package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.breaktime.breaksecretary.Observer;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Service.MyService;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.User;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;


// In this case, the fragment displays simple text based on the page
public class QuickReserveFragment extends Fragment implements Observer {
    private static final String TAG = QuickReserveFragment.class.getName();
    private View view;
    Button btn_re;
    private FirebaseUtil mFirebaseUtil;
    private User mUser;
    private CircularImageView imgView;
    private TextView sta;
    private boolean isFlag = false;
    private int i = 0;
    CircleProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;

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
        view = inflater.inflate(R.layout.fragment_quickreserve, container, false);

        ViewInit();
        return view;
    }

    public void ViewInit(){
        Button quick_btn = view.findViewById(R.id.btn_quick_res);
        quick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService();
            }
        });

        Button btn_stop = view.findViewById(R.id.btn_stop);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService();
                mCountDownTimer.cancel();
            }
        });
        btn_re = view.findViewById(R.id.btn_rere);
        btn_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 시간초과 후 초기화 과정...
                mUser.get_user_ref().child("status").setValue(User.Status_user.ONLINE);

            }
        });

        imgView = view.findViewById(R.id.circularImageView);
        sta = view.findViewById(R.id.txt_sta);


        mProgressBar=view.findViewById(R.id.progressbar);
        mProgressBar.setMax(10);
        mProgressBar.setProgressTextSize(80);
        mProgressBar.setProgressFormatter(new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return max-progress+"s";
            }
        });

        mCountDownTimer=new CountDownTimer(10000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                mProgressBar.setProgress(i);

            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                mProgressBar.setProgress(100);
            }
        };

        mProgressBar.setVisibility(View.GONE);
        btn_re.setVisibility(View.GONE);

    }

    @Override
    public void update(User.Status_user status){
        try{
            switch (status){
                case ONLINE:
                    mProgressBar.setVisibility(View.GONE);
                    imgView.setImageResource(R.drawable.jal);
                    btn_re.setVisibility(View.GONE);
                    sta.setText("None");
                    break;
                case RESERVING:
                    isFlag = true;
                    imgView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    mCountDownTimer.start();
                    sta.setText("예약중");
                    break;
                case RESERVING_OVER:
                    imgView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    btn_re.setVisibility(View.VISIBLE);
                    imgView.setImageResource(R.drawable.ad);
                    sta.setText("에약 시간초과");
                    break;
                case OCCUPYING:
                    isFlag = false;
                    imgView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    imgView.setImageResource(R.drawable.empty_state);
                    sta.setText("사용중");
                    break;
                case STEPPING_OUT:
                    imgView.setImageResource(R.drawable.ad);
                    sta.setText("자리비움 중");
                    break;
                case STEPPING_OUT_OVER:
                    sta.setText("자리비움 초과");
                    btn_re.setVisibility(View.VISIBLE);
                    break;
                case SUBSCRIBING:
                    break;
            }
        }catch (Exception e){}

    }

    public void startService(){
        mUser.get_user_ref().child("status").setValue(User.Status_user.RESERVING);
        Intent intent = new Intent(getActivity(), MyService.class);
        intent.putExtra("major", 1002);
        intent.putExtra("minor", 20);
        ContextCompat.startForegroundService(getActivity(), intent);
    }

    public void stopService(){
        mUser.get_user_ref().child("status").setValue(User.Status_user.ONLINE);
        Intent intent = new Intent(getActivity(), MyService.class);
        getActivity().stopService(intent);
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

}
