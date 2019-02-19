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
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


// In this case, the fragment displays simple text based on the page
public class MyStatusFragment extends Fragment {
    private static final String TAG = "MyStatusFragment";
    private View view;
    private FirebaseUtil myFireBase;
    private User mUser;

    TextView tv_notifyTS, tv_loginTS, tv_startTS, tv_reserveTS, tv_idleTS, tv_penaltyTS, tv_blockTS;
    TextView tv_status, tv_section, tv_seat, tv_time;

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
        tv_time = view.findViewById(R.id.left_time);


//        mUser.getNotifyTSForSingleEvent(new MyCallback<Long>() {
//            @Override
//            public void onCallback(Long ts) {
//                if (ts == null)
//                    ts = 0L;
//                String td = dayTime.format(ts);
//                tv_notifyTS.setText(td);
//            }
//        });



        mUser.getUserRef().child("notifyTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value == null)
                    tv_notifyTS.setText("No Notification");
                else
                    tv_notifyTS.setText(dayTime.format(value));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUser.getUserRef().child("loginTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value == null)
                    tv_loginTS.setText("No Login");
                else
                    tv_loginTS.setText(dayTime.format(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUser.getUserRef().child("startTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value == null)
                    tv_startTS.setText("No Start");
                else
                    tv_startTS.setText(dayTime.format(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUser.getUserRef().child("reserveTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value == null)
                    tv_reserveTS.setText("No Reservation");
                else
                    tv_reserveTS.setText(dayTime.format(value));
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUser.getUserRef().child("idleTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value == null)
                    tv_idleTS.setText("No Idle");
                else
                    tv_idleTS.setText(dayTime.format(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUser.getUserRef().child("penaltyTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value == null)
                    tv_penaltyTS.setText("No Penalty");
                else
                    tv_penaltyTS.setText(dayTime.format(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mUser.getUserRef().child("blockTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                if (value == null)
                    tv_blockTS.setText("No Block");
                else
                    tv_blockTS.setText(dayTime.format(value));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUser.getUserRef().child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                if (value == null)
                    tv_status.setText("status : None");
                else {
                    switch( value ) {
                        case User.STATUS_NOTHING:
                            tv_status.setText("STATUS_NOTHING");
                            break;
                        case User.STATUS_SITTING:
                            tv_status.setText("STATUS_SITTING");
                            mUser.getStartTSForSingleEvent(new MyCallback<Long>() {
                                @Override
                                public void onCallback(Long ts) {
                                    if (ts == null)
                                        return;

                                    TimeCounter  timer = new TimeCounter(2 * 60L, ts, tv_time);
                                    timer.start();

                                }
                            });
                            break;
                        case User.STATUS_RESERVING:
                            tv_status.setText("STATUS_RESERVING");

                            mUser.getReserveTSForSingleEvent(new MyCallback<Long>() {
                                @Override
                                public void onCallback(Long ts) {
                                    if (ts == null)
                                        return;

                                    TimeCounter  timer = new TimeCounter(2 * 60L, ts, tv_time);
                                    timer.start();

                                }
                            });
                            break;
                        case User.STATUS_BEING_AWAY:
                            tv_status.setText("STATUS_BEING_AWAY");
                            mUser.getIdleTSForSingleEvent(new MyCallback<Long>() {
                                @Override
                                public void onCallback(Long ts) {
                                    if (ts == null)
                                        return;

                                    TimeCounter  timer = new TimeCounter(2 * 60L, ts, tv_time);
                                    timer.start();

                                }
                            });
                            break;
                        case User.STATUS_PENALTY:
                            tv_status.setText("STATUS_PENALTY");
                            mUser.getPenaltyTSForSingleEvent(new MyCallback<Long>() {
                                @Override
                                public void onCallback(Long ts) {
                                    if (ts == null)
                                        return;

                                    TimeCounter  timer = new TimeCounter(2 * 60L, ts, tv_time);
                                    timer.start();

                                }
                            });
                            break;
                        case User.STATUS_BLOCKED:
                            tv_status.setText("STATUS_BLOCKED");
                            mUser.getBlockTSForSingleEvent(new MyCallback<Long>() {
                                @Override
                                public void onCallback(Long ts) {
                                    if (ts == null)
                                        return;

                                    TimeCounter  timer = new TimeCounter(2 * 60L, ts, tv_time);
                                    timer.start();

                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUser.getUserRef().child("mysectionNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                if (value == null)
                    tv_section.setText("Section : None");
                else
                    tv_section.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUser.getUserRef().child("myseatNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                if (value == null)
                    tv_seat.setText("Seat : None");
                else
                    tv_seat.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    public String formatTime(Long lTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lTime);
        return (c.get(Calendar.HOUR_OF_DAY) + "시 " + c.get(Calendar.MINUTE) + "분 " + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND) + "초");
    }


    public class TimeCounter extends CountDownTimer {
        Long beginTS;
        Integer countUpTimer;
        TextView view;

        public TimeCounter(Long minLimit, Long beginTS, TextView view) {
            super(minLimit * 1000, 1000);

            this.beginTS = beginTS;
            countUpTimer = (int)(System.currentTimeMillis() - beginTS) / 1000;
            this.view = view;
        }

        public void updateBeginTS(Long beginTS) {
            this.beginTS = beginTS;
            countUpTimer = (int)(System.currentTimeMillis() - beginTS) / 1000;
        }

        @Override
        public void onTick(long l) {
            view.setText("Seconds: " + countUpTimer);
            countUpTimer = countUpTimer + 1;
        }

        @Override
        public void onFinish() {
            countUpTimer = (int)(System.currentTimeMillis() - beginTS) / 1000;
        }
    }


}
