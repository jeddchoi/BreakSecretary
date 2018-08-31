package com.breaktime.breaksecretary.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.breaktime.breaksecretary.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.yqritc.scalablevideoview.ScalableType;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;


public class FirstActivity extends AppCompatActivity {
    public static Context mContext;
    private String TAG = "FirstActivity";
    private static final int RC_SIGN_IN = 9001;

    private ScalableVideoView mVideoView;
    private Button signupButton;
    private Button loginButton;
    private SignInButton googleSigninButton;
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;
//    private Button kakaoLoginButton;
//    private String mCustomToken;
//    private TokenBroadcastReceiver mTokenReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mContext = this;

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(mContext, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }

        Log.d(TAG, "FirstActivity onCreate");
        mVideoView = (ScalableVideoView) findViewById(R.id.video_view);
        try {
            mVideoView.setRawData(R.raw.bg_video);
            mVideoView.setVolume(0, 0);
            mVideoView.setScalableType(ScalableType.CENTER_BOTTOM_CROP);
            mVideoView.setLooping(true);
            mVideoView.prepareAsync(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mVideoView.start();
                    Log.d(TAG, "mVideoView playing");
                }
            });

        } catch (IOException ioe) {
            //handle error
            Log.e(TAG, "mVideoView Error");
        }

        signupButton = findViewById(R.id.signupButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "sign up button clicked");
                Intent intent = new Intent(mContext, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSigninButton = findViewById(R.id.googleSigninButton);
        googleSigninButton.setSize(SignInButton.SIZE_WIDE);
        googleSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "log in button clicked");
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });





        /* Kakao Login */

//        kakaoLoginButton = findViewById(R.id.kakaoLoginButton);
//        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "kakao login button clicked");
//                Intent intent = new Intent(mContext, KakaoLoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//            }
//        });
//
//        // Create token receiver (for demo purposes only)
//        mTokenReceiver = new TokenBroadcastReceiver() {
//            @Override
//            public void onNewToken(String token) {
//                Log.d(TAG, "onNewToken:" + token);
//                setCustomToken(token);
//            }
//        };
    }



    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "firebaseAuthWithGoogle: onActivityResult");

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle: onActivityResult" + account.getId());
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.container), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]








/* Kakao Login */

//    @Override
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(mTokenReceiver, TokenBroadcastReceiver.getFilter());
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(mTokenReceiver);
//    }
//
//    private void startSignIn() {
//        // Initiate sign in with custom token
//        // [START sign_in_custom]
//        mAuth.signInWithCustomToken(mCustomToken)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCustomToken:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCustomToken:failure", task.getException());
//                            Toast.makeText(FirstActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//        // [END sign_in_custom]
//    }

//    private void setCustomToken(String token) {
//        mCustomToken = token;
//
//        String status;
//        if (mCustomToken != null) {
//            status = "Token:" + mCustomToken;
//        } else {
//            status = "Token: null";
//        }
//
//        // Enable/disable sign-in button and show the token
//        findViewById(R.id.button_sign_in).setEnabled((mCustomToken != null));
//        ((TextView) findViewById(R.id.text_token_status)).setText(status);
//    }

}
