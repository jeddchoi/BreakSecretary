package com.breaktime.breaksecretary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.breaktime.breaksecretary.Util.TimerManager;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.view.View.VISIBLE;


// In this case, the fragment displays simple text based on the page
public class MyStatusFragment extends Fragment {
    private static final String TAG = "MyStatusFragment";
    private View view;
    private FirebaseUtil myFireBase;
    private User mUser;
    private TimerManager mTM;


    private TextView tv_notifyTS, tv_loginTS, tv_startTS, tv_reserveTS, tv_idleTS, tv_penaltyTS, tv_blockTS;
    public TextView tv_status, tv_section, tv_seat;

    SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초",  Locale.KOREA);




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            myFireBase = ((MainActivity)getActivity()).myFireBase;
            mUser = ((MainActivity)getActivity()).mUser;
        }
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mystatus, container, false);

        tv_notifyTS = view.findViewById(R.id.notify_ts);
        tv_loginTS = view.findViewById(R.id.login_ts);
        tv_startTS = view.findViewById(R.id.start_ts);
        tv_reserveTS = view.findViewById(R.id.reserve_ts);
        tv_idleTS = view.findViewById(R.id.idle_ts);
        tv_penaltyTS = view.findViewById(R.id.penalty_ts);
        tv_blockTS = view.findViewById(R.id.block_ts);

        tv_status = view.findViewById(R.id.status);
        tv_section = view.findViewById(R.id.section_num);
        tv_seat = view.findViewById(R.id.seat_num);



//        mUser.getNotifyTSForSingleEvent(new MyCallback<Long>() {
//            @Override
//            public void onCallback(Long ts) {
//                if (ts == null)
//                    ts = 0L;
//                String td = dayTime.format(ts);
//                tv_notifyTS.setText(td);
//            }
//        });


        return view;
    }

    public String formatTime(Long lTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lTime);
        return (c.get(Calendar.HOUR_OF_DAY) + "시 " + c.get(Calendar.MINUTE) + "분 " + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND) + "초");
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
