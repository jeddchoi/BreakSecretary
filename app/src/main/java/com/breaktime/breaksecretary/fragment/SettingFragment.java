package com.breaktime.breaksecretary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.FirstActivity;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.app.BreakSecretary;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


// In this case, the fragment displays simple text based on the page
public class SettingFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public static final String ARG_PAGE = "ARG_PAGE";
    private Button logoutButton;
    View view;


    private int mPage;

    public static SettingFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        // Test Field
        Button bnt = (Button)view.findViewById(R.id.btn_test);
        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BreakSecretary.getInstance().TestToast("Hello"); // 임시객체 생성 후 행 전환시 소멸
                BreakSecretary.getInstance().Log("버튼 클릭됨");

//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("message");

//                myRef.setValue("Hello, World!");

                FirebaseUtil.getLoginUserRootRef().child("aa").setValue(1241124);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = new Intent(getContext(), FirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }
    private void signOut() {
        mAuth.signOut();

        // Google sign out
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        mGoogleSignInClient.signOut();
    }




}
