package com.breaktime.breaksecretary;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public static Context mContext;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Button logoutButton;
    private TextView loginAccountTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();



        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(mContext, FirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        loginAccountTextView = findViewById(R.id.loginAccount);
        loginAccountTextView.setText( currentUser.getEmail() + " logined..." );

    }
    private void signOut() {
        mAuth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Snackbar.make(findViewById(R.id.container), "Sign in with " + currentUser.getEmail(), Snackbar.LENGTH_SHORT).show();
    }
}
