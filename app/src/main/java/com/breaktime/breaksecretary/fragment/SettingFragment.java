package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.FirstActivity;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.SimpleDateFormat;


// In this case, the fragment displays simple text based on the page
public class SettingFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "SettingFragment";
    private View view;
    private FirebaseUtil myFireBase;
    private User mUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof  MainActivity) {
            myFireBase = ((MainActivity)getActivity()).myFireBase;
            mUser = ((MainActivity)getActivity()).mUser;
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        view.findViewById(R.id.logoutButton).setOnClickListener(this);

        view.findViewById(R.id.btn_test).setOnClickListener(this);
        final TextView tv = view.findViewById(R.id.status);

        Log.d(TAG, "onCreateView");
        mUser.getLoginTSForSingleEvent(new MyCallback() {
            @Override
            public void onCallback(Long timeStamp) {
                SimpleDateFormat dayTime = new SimpleDateFormat("MM dd,yyyy HH:mm");
                String todayDate = dayTime.format(timeStamp);
                tv.setText(todayDate);
            }
        });

        mUser.getUserRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Long timeStamp = dataSnapshot.getValue(Long.class);
                SimpleDateFormat dayTime = new SimpleDateFormat("MM dd,yyyy HH:mm", java.util.Locale.getDefault());
                String todayDate = dayTime.format(timeStamp);
                tv.setText(todayDate);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        return view;

    }


    private void signOut() {
        myFireBase.getAuth().signOut();

        // Google sign out
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        mGoogleSignInClient.signOut();

    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ) {
            case R.id.logoutButton :
                signOut();
                Intent intent = new Intent(getContext(), FirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btn_test :
                mUser.userLogin();
                break;
            default:
                break;
        }
    }
}
