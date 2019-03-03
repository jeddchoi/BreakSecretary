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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.breaktime.breaksecretary.Observer;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


// In this case, the fragment displays simple text based on the page
public class MyStatusFragment extends Fragment implements Observer {
    private static final String TAG = MyStatusFragment.class.getName();
    private View view;

    private FirebaseUtil mFirebaseUtil;
    private User mUser;

    private TextView tv_login, tv_subscribe, tv_occupy, tv_reserve, tv_step_out, tv_get_penalty, tv_get_block;
    private TextView tv_status;
    private FrameLayout container_status;
    private Button button_stop;
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
        view = inflater.inflate(R.layout.fragment_mystatus, container, false);

        tv_login = view.findViewById(R.id.tv_login);
        tv_subscribe = view.findViewById(R.id.tv_subscribe);
        tv_reserve = view.findViewById(R.id.tv_reserve);
        tv_occupy = view.findViewById(R.id.tv_occupy);
        tv_step_out = view.findViewById(R.id.tv_step_out);
        tv_get_penalty = view.findViewById(R.id.tv_get_penalty);
        tv_get_block = view.findViewById(R.id.tv_get_block);

        tv_status = view.findViewById(R.id.tv_status);
        container_status = view.findViewById(R.id.container_status);

        button_stop = view.findViewById(R.id.button2);
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).show_toast_msg("사용종료", true);
                ((MainActivity)getActivity()).stopService();
            }
        });
        button_stop.setVisibility(View.GONE);
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

        mUser.get_user_ref().child("ts_login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);

                if (value != null)
                    tv_login.setText("ts_login : " + value.toString());
                else
                    tv_login.setText("ts_login : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUser.get_user_ref().child("ts_subscribe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);

                if (value != null)
                    tv_subscribe.setText("ts_subscribe : " + value.toString());
                else
                    tv_subscribe.setText("ts_subscribe : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUser.get_user_ref().child("ts_reserve").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);

                if (value != null)
                    tv_reserve.setText("ts_reserve : " + value.toString());
                else
                    tv_reserve.setText("ts_reserve : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mUser.get_user_ref().child("ts_occupy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);

                if (value != null)
                    tv_occupy.setText("ts_occupy : " + value.toString());
                else
                    tv_occupy.setText("ts_occupy : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        mUser.get_user_ref().child("ts_step_out").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);

                if (value != null)
                    tv_step_out.setText("ts_step_out : " + value.toString());
                else
                    tv_step_out.setText("ts_step_out : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUser.get_user_ref().child("ts_get_penalty").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);

                if (value != null)
                    tv_get_penalty.setText("ts_get_penalty : " + value.toString());
                else
                    tv_get_penalty.setText("ts_get_penalty : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUser.get_user_ref().child("ts_get_block").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);

                if (value != null)
                    tv_get_block.setText("ts_get_block : " + value.toString());
                else
                    tv_get_block.setText("ts_get_block : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*
        mUser.get_user_ref().child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                if (value != null)
                    tv_status.setText("status : " + value);
                else
                    tv_status.setText("status : NULL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

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
    public void update(User.Status_user status) {
        try{
            switch (status){
                case ONLINE:
                    //btn_re.setVisibility(View.GONE);
                    button_stop.setVisibility(View.GONE);
                    tv_status.setText("None");
                    break;
                case RESERVING:

                   // mCountDownTimer.start();
                    tv_status.setText("예약중");
                    break;
                case RESERVING_OVER:
                    //btn_re.setVisibility(View.VISIBLE);
                    tv_status.setText("에약 시간초과");
                    break;
                case OCCUPYING:
                    //isFlag = false;
                    button_stop.setVisibility(View.VISIBLE);
                    tv_status.setText("사용중");
                    break;
                case STEPPING_OUT:
                    tv_status.setText("자리비움 중");
                    break;
                case STEPPING_OUT_OVER:
                    tv_status.setText("자리비움 초과");
                    //btn_re.setVisibility(View.VISIBLE);
                    break;
                case SUBSCRIBING:
                    break;
            }
        }catch (Exception e){}
    }

}
