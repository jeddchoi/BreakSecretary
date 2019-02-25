package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.FirstActivity;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


// In this case, the fragment displays simple text based on the page
public class SettingFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = SettingFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;
        return view;
    }


    private void signOut() {
        mFirebaseUtil.getAuth().signOut();

        // Google sign out
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        mGoogleSignInClient.signOut();

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
    }
}
