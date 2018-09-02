package com.breaktime.breaksecretary.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    public static Context mContext;
    private static final String TAG = "SignUpActivity";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private FirebaseUser currentUser;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // UI references.
    private AutoCompleteTextView mEmailView;
    private TextInputEditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private AnimationDrawable anim;
    private Button mEmailSignUpButton;
    private TextView termOfUseTextView;
    private ImageView mBackButton;
    private InputMethodManager mIMM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mContext = this;

        termOfUseTextView = findViewById(R.id.term_of_use_textview);
        termOfUseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(SignUpActivity.this)
                        .setTitle(getString(R.string.main_dialog_simple_title))
                        .setMessage(Html.fromHtml(getString(R.string.terms_of_use)))
                        .setPositiveButton(getString(R.string.dialog_ok), null)
                        .show();
            }
        });

        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mIMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    mIMM.hideSoftInputFromWindow(mPasswordView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        mEmailSignUpButton = findViewById(R.id.email_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        mBackButton = findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        // Animate Background like Instagram App
        LinearLayout container = findViewById(R.id.container);
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(4000);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        currentUser = null;

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            createAccount(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        String regex = "^[a-zA-Z0-9!@.#$%^&*?_~]{8,16}$"; // 8자리 ~ 16자리까지 가능
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (anim != null && anim.isRunning())
            anim.stop();
    }


    private void createAccount(final String mEmail, final String mPassword) {

        Log.d(TAG, "createAccount:" + mEmail);

        showProgress(true);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            sendEmailVerification(mEmail);
                            signIn(mEmail, mPassword);


                            FirebaseUtil.RegisterNewOne(mAuth.getUid(), new User(mEmail));





                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            notifyUser("Failed to sign up", mEmail);
                        }

                        // [START_EXCLUDE]
                        showProgress(false);
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void sendEmailVerification(final String mEmail) {

        // Send verification email
        // [START send_email_verification]
        currentUser = mAuth.getCurrentUser();
        currentUser.sendEmailVerification()
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // [START_EXCLUDE]

                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());

//                    Snackbar.make(findViewById(R.id.container), "Failed to send verification email to " + mEmail, Snackbar.LENGTH_INDEFINITE)
//                            .setAction("RESEND", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    sendEmailVerification(mEmail);
//                                }
//                            }).show();
                }
                    // [END_EXCLUDE]
                }
            });
        // [END send_email_verification]

    }

    private void signIn(final String mEmail, final String mPassword) {

        Log.d(TAG, "signIn try with :" + mEmail);
        showProgress(true);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }}
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    notifyUser("Invalid password", mEmail);
                } else if (e instanceof FirebaseAuthInvalidUserException) {

                    String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();
                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        notifyUser("No matching account found", mEmail);
                    } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                        notifyUser("User account has been disabled", mEmail);
                    } else {
                        notifyUser(e.getLocalizedMessage(), mEmail);
                    }
                }
                showProgress(false);
            }
        });
        // [END sign_in_with_email]
    }


    public void notifyUser(String message, String mEmail) {
        Snackbar.make(findViewById(R.id.container), message + " with " + mEmail, Snackbar.LENGTH_LONG).show();
    }
}
