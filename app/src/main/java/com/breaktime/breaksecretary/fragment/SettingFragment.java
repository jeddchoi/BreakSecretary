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
import com.breaktime.breaksecretary.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Map;


// In this case, the fragment displays simple text based on the page
public class SettingFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = SettingFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    TextView tv_limit_login, tv_limit_subscribe, tv_limit_reserve, tv_limit_occupy, tv_limit_step_out, tv_limit_get_penalty, tv_limit_get_block;
    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach()");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        view.findViewById(R.id.btn_logout).setOnClickListener(this);

        tv_limit_login = view.findViewById(R.id.tv_limit_login);
        tv_limit_subscribe = view.findViewById(R.id.tv_limit_subscribe);
        tv_limit_reserve = view.findViewById(R.id.tv_limit_reserve);
        tv_limit_occupy = view.findViewById(R.id.tv_limit_occupy);
        tv_limit_step_out = view.findViewById(R.id.tv_limit_step_out);
        tv_limit_get_penalty = view.findViewById(R.id.tv_limit_get_penalty);
        tv_limit_get_block = view.findViewById(R.id.tv_limit_get_block);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated()");
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            // Restore last state for checked position.

        }
        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;

        Map<String, Long> limits = mFirebaseUtil.getmLimits();

        tv_limit_login.setText(limits.get("login").toString());
        tv_limit_subscribe.setText(limits.get("subscribe").toString());
        tv_limit_reserve.setText(limits.get("reserve").toString());
        tv_limit_occupy.setText(limits.get("occupy").toString());
        tv_limit_step_out.setText(limits.get("step_out").toString());
        tv_limit_get_penalty.setText(limits.get("get_penalty").toString());
        tv_limit_get_block.setText(limits.get("get_block").toString());


    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                sign_out();
                startActivity(new Intent(getActivity(), FirstActivity.class));
                getActivity().finish();
                break;
        }
    }

    private void sign_out() {
        mFirebaseUtil.getAuth().signOut();
        mUser.user_logout();
        // Google sign out
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        mGoogleSignInClient.signOut();

    }

}
