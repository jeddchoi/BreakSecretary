//package com.breaktime.breaksecretary.activity;
//
//import android.annotation.TargetApi;
//import android.app.LoaderManager.LoaderCallbacks;
//import android.content.Context;
//import android.content.CursorLoader;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.Loader;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.drawable.AnimationDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
//import android.support.design.widget.TextInputEditText;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.breaktime.breaksecretary.R;
//import com.breaktime.breaksecretary.Util.FirebaseUtil;
//import com.breaktime.breaksecretary.model.User;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseAuthInvalidUserException;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static android.Manifest.permission.READ_CONTACTS;
//
///**
// * A userLogin screen that offers userLogin via email/password.
// */
//public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, View.OnClickListener {
//    private static final String TAG = "LoginActivity";
//
//    /**
//     * Id to identity READ_CONTACTS permission request.
//     */
//    private static final int REQUEST_READ_CONTACTS = 0;
//
//    /**
//     * Keep track of the userLogin task to ensure we can cancel it if requested.
//     */
//    private FirebaseUtil myFireBase = new FirebaseUtil();
//
//
//    // UI references.
//    private AutoCompleteTextView mEmailView;
//    private TextInputEditText mPasswordView;
//    private AnimationDrawable anim;
//    private InputMethodManager mIMM;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_login);
//
//
//        // Set up the userLogin form.
//        mEmailView = findViewById(R.id.email);
//        populateAutoComplete();
//        mPasswordView = findViewById(R.id.password);
//        mIMM = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    mIMM.hideSoftInputFromWindow(mPasswordView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
//        findViewById(R.id.back_button).setOnClickListener(this);
//        findViewById(R.id.having_trouble_loggin_in).setOnClickListener(this);
//
//        // Animate Background like Instagram App
//        LinearLayout container = findViewById(R.id.container);
//        anim = (AnimationDrawable) container.getBackground();
//        anim.setEnterFadeDuration(2000);
//        anim.setExitFadeDuration(4000);
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch( v.getId() ) {
//            case R.id.email_sign_in_button :
//                attemptLogin();
//                break;
//
//            case R.id.back_button:
//                Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//                finish();
//                break;
//
//            case R.id.having_trouble_loggin_in:
//                sendResetPasswordEmail();
//                break;
//
//            default:
//                Log.d(TAG, "invalid click");
//                break;
//        }
//    }
//
//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }
//
//    private boolean mayRequestContacts() {
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }
//
//
//    /**
//     * Attempts to sign in or register the account specified by the userLogin form.
//     * If there are form errors (invalid email, missing fields, etc.), the
//     * errors are presented and no actual userLogin attempt is made.
//     */
//    private void attemptLogin() {
//        if (myFireBase.getCurrenUser() != null) {
//            Log.d(TAG, "Already userLogin !");
//            return;
//        }
//
//        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);
//
//        // Store values at the time of the userLogin attempt.
//        String email = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt userLogin and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            signIn(email, password);
//        }
//    }
//
//    private boolean isEmailValid(String email) {
//        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
//
//    private boolean isPasswordValid(String password) {
//        String regex = "^[a-zA-Z0-9!@.#$%^&*?_~]{8,16}$"; // 8자리 ~ 16자리까지 가능
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(password);
//
//        return m.matches();
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE +
//                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE},
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }
//
//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onPostResume() {
//        super.onPostResume();
//        Log.d(TAG, "LoginActivity onPostResume");
//        if (anim != null && !anim.isRunning())
//            anim.start();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "LoginActivity onPause");
//        if (anim != null && anim.isRunning())
//            anim.stop();
//    }
//
//    private interface ProfileQuery {
//        String[] PROJECTION = {
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
//        };
//
//        int ADDRESS = 0;
//
//    }
//
//    private void signIn(final String mEmail, final String mPassword) {
//        Log.d(TAG, "signIn try with :" + mEmail);
//
//        // [START sign_in_with_email]
//        myFireBase.getAuth().signInWithEmailAndPassword(mEmail, mPassword)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                            startActivity(intent);
//
//                            finish();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                        }}
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    notifyUser("Invalid password", mEmail);
//                } else if (e instanceof FirebaseAuthInvalidUserException) {
//
//                    String errorCode = ((FirebaseAuthInvalidUserException) e).getErrorCode();
//                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
//                        notifyUser("No matching account found", mEmail);
//                    } else if (errorCode.equals("ERROR_USER_DISABLED")) {
//                        notifyUser("User account has been disabled", mEmail);
//                    } else {
//                        notifyUser(e.getLocalizedMessage(), mEmail);
//                    }
//                }
//            }
//        });
//        // [END sign_in_with_email]
//    }
//
//    public void notifyUser(String message, String mEmail) {
//        if ( mEmail != null ) {
//            message = message + "with " + mEmail;
//        }
//        Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG).show();
//    }
//
//    private void sendResetPasswordEmail() {
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        // ...Irrelevant code for customizing the buttons and title
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_reset_password, null);
//
//        dialogBuilder.setView(dialogView);
//        dialogBuilder.setTitle(getString(R.string.reset_password));
//
//        final AutoCompleteTextView mResetEmail = dialogView.findViewById(R.id.email_reset);
//
//        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), null);
//
//        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String emailAddress = mResetEmail.getText().toString();
//                notifyUser("Cancel to reset password", null);
//            }
//        });
//
//        final AlertDialog alertDialog = dialogBuilder.create();
//
//        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//
//                Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
//                button.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//                        mResetEmail.setError(null);
//                        String emailAddress = mResetEmail.getText().toString();
//                        boolean cancel = false;
//
//                        // Check for a valid email address.
//                        if (TextUtils.isEmpty(emailAddress)) {
//                            mResetEmail.setError(getString(R.string.error_field_required));
//                            cancel = true;
//                        } else if (!isEmailValid(emailAddress)) {
//                            mResetEmail.setError(getString(R.string.error_invalid_email));
//                            cancel = true;
//                        }
//
//                        if (cancel) {
//                            // There was an error; don't attempt userLogin and focus the first
//                            // form field with an error.
//                            mResetEmail.requestFocus();
//                            Log.d(TAG, "requestFocus");
//                        } else {
//                            Log.d(TAG, "no error");
//
//                            myFireBase.getAuth().sendPasswordResetEmail(emailAddress);
//                            notifyUser("Sent reset password mail", emailAddress);
//                            alertDialog.dismiss();
//                        }
//
//                    }
//                });
//            }
//        });
//
//        alertDialog.show();
//
//
//        // Initially disable the button
//        ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//        // OR you can use here setOnShowListener to disable button at first
//        // time.
//
//        // Now set the textchange listener for edittext
//        mResetEmail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Check if edittext is empty
//                if (TextUtils.isEmpty(s)) {
//                    // Disable ok button
//                    ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                } else {
//                    // Something into edit text. Enable the button.
//                    ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                }
//
//            }
//        });
//
//
//    }
//
//
//}
//
