package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private static final String TAG = "SettingFragment";
    private View view;
    private FirebaseUtil myFireBase;
    private User mUser;

    EditText section_et, seat_et;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof  MainActivity) {
            myFireBase = ((MainActivity)getActivity()).myFireBase;
            mUser = ((MainActivity)getActivity()).mUser;
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        view.findViewById(R.id.logout_btn).setOnClickListener(this);
        view.findViewById(R.id.verify_btn).setOnClickListener(this);
        view.findViewById(R.id.delete_btn).setOnClickListener(this);
        view.findViewById(R.id.donate_btn).setOnClickListener(this);
        view.findViewById(R.id.report_btn).setOnClickListener(this);

        view.findViewById(R.id.reserve_btn).setOnClickListener(this);
        view.findViewById(R.id.fast_reserve_btn).setOnClickListener(this);
        view.findViewById(R.id.start_btn).setOnClickListener(this);
        view.findViewById(R.id.stop_btn).setOnClickListener(this);
        view.findViewById(R.id.idle_btn).setOnClickListener(this);
        view.findViewById(R.id.return_btn).setOnClickListener(this);
        view.findViewById(R.id.penalty_btn).setOnClickListener(this);
        view.findViewById(R.id.block_btn).setOnClickListener(this);

        section_et = view.findViewById(R.id.section_et);
        seat_et = view.findViewById(R.id.seat_et);
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

            case R.id.logout_btn :
                signOut();
                Intent intent = new Intent(getContext(), FirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                getActivity().finish();
                break;

            case R.id.verify_btn :
                Toast.makeText(getActivity(), "Verify Email", Toast.LENGTH_SHORT).show();

                break;
            case R.id.delete_btn :
                Toast.makeText(getActivity(), "Delete Account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.donate_btn :
                Toast.makeText(getActivity(), "Donate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.report_btn :
                Toast.makeText(getActivity(), "Report", Toast.LENGTH_SHORT).show();
                break;

            case R.id.reserve_btn :
                Toast.makeText(getActivity(), "Reserve", Toast.LENGTH_SHORT).show();
                mUser.user_reserve(Integer.parseInt(section_et.getText().toString()), Integer.parseInt(seat_et.getText().toString()));
                break;

            case R.id.fast_reserve_btn :
                Toast.makeText(getActivity(), "Fast Reservation", Toast.LENGTH_SHORT).show();
                // get available seat and user_reserve

                break;

            case R.id.start_btn :
                Toast.makeText(getActivity(), "Start", Toast.LENGTH_SHORT).show();
                mUser.user_occupy();
                break;

            case R.id.stop_btn :
                Toast.makeText(getActivity(), "Stop", Toast.LENGTH_SHORT).show();
                mUser.user_stop();
                break;

            case R.id.idle_btn :
                Toast.makeText(getActivity(), "Being Away", Toast.LENGTH_SHORT).show();
                mUser.user_step_out();
                break;

            case R.id.return_btn :
                Toast.makeText(getActivity(), "Return to Seat", Toast.LENGTH_SHORT).show();
                mUser.user_return_to_seat();
                break;

            case R.id.penalty_btn :
                Toast.makeText(getActivity(), "Get Penalty", Toast.LENGTH_SHORT).show();
                mUser.user_get_penalty();
                break;

            case R.id.block_btn :
                Toast.makeText(getActivity(), "Get Blocked", Toast.LENGTH_SHORT).show();
                mUser.user_get_block();
                break;

            default:
                break;
        }
    }
}
