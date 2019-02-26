package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.User;


// In this case, the fragment displays simple text based on the page
public class TimeLineFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = TimeLineFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    private EditText et_num_section, et_num_seat;

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
        view = inflater.inflate(R.layout.fragment_timeline, container, false);
        mFirebaseUtil = ((MainActivity)getActivity()).mFirebaseUtil;
        mUser = ((MainActivity)getActivity()).mUser;

        et_num_section = view.findViewById(R.id.et_num_section);
        et_num_seat = view.findViewById(R.id.et_num_seat);
        view.findViewById(R.id.btn_reserve).setOnClickListener(this);
        view.findViewById(R.id.btn_fast_reserve).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_reservation).setOnClickListener(this);
        view.findViewById(R.id.btn_occupy).setOnClickListener(this);
        view.findViewById(R.id.btn_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_step_out).setOnClickListener(this);
        view.findViewById(R.id.btn_return_to_seat).setOnClickListener(this);
        view.findViewById(R.id.btn_get_penalty).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_penalty).setOnClickListener(this);
        view.findViewById(R.id.btn_get_block).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel_block).setOnClickListener(this);

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
            case R.id.btn_reserve:
                if (et_num_seat.getText().toString().length() == 0 || et_num_section.getText().toString().length() == 0) {
                    ((MainActivity)getActivity()).show_toast_msg("You should fill out number first!", true);
                    return;
                }
                mUser.user_reserve(Integer.parseInt(et_num_section.getText().toString()), Integer.parseInt(et_num_seat.getText().toString()));

                ((MainActivity)getActivity()).show_snackbar_msg("You reserved " + et_num_seat.getText().toString() + " seat of " + et_num_section.getText().toString() + " section.", true);
                break;

            case R.id.btn_fast_reserve:
                break;

            case R.id.btn_cancel_reservation:
                break;

            case R.id.btn_occupy:
                break;

            case R.id.btn_stop:
                break;

            case R.id.btn_step_out:
                break;

            case R.id.btn_return_to_seat:
                break;

            case R.id.btn_get_penalty:
                break;

            case R.id.btn_cancel_penalty:
                break;

            case R.id.btn_get_block:
                break;

            case R.id.btn_cancel_block:
                break;

            default:
                break;
        }
    }
}
