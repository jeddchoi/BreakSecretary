package com.breaktime.breaksecretary.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;


@SuppressWarnings("serial")
@IgnoreExtraProperties
public class User implements Serializable {
    private static final String TAG = User.class.getName();

    /* status constant */
    public enum Status_user {
        ONLINE, SUBSCRIBING, RESERVING, RESERVING_OVER, OCCUPYING, OCCUPYING_OVER, STEPPING_OUT, STEPPING_OUT_OVER, PAYING_PENALTY, BEING_BLOCKED
    }

    @Exclude
    private FirebaseUtil mFirebaseUtil;
    @Exclude
    private DatabaseReference mUserRef;

    // User Information
    private Status_user status;
    private Integer num_section = null;
    private Integer num_seat = null;

    // TimeStamp
    private Long ts_login = null;
    private Long ts_subscribe = null;
    private Long ts_occupy = null;
    private Long ts_reserve = null;
    private Long ts_step_out = null;
    private Long ts_get_penalty = null;
    private Long ts_get_block = null;


    public User(FirebaseUtil mFirebaseUtil) {
        this.mFirebaseUtil = mFirebaseUtil;


        if (mFirebaseUtil.getCurrentUser() == null) {
            Log.d(TAG, "getCurrentUser() is null");
        }

        mUserRef = mFirebaseUtil.getUsersRef().child(mFirebaseUtil.getCurrentUser().getUid());
        mUserRef.child("email_address").setValue(mFirebaseUtil.getCurrentUser().getEmail());
    }

    // Default Constructor
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    // [START]DON'T USE THESE FUNCTIONS. THEY'RE FOR FIREBASE REALTIME DATABASE
    // getters



    public String getStatus() {
        if (status == null) {
            return null;
        } else {
            return status.name();
        }
    }

    public Integer getNum_section() { return num_section; }

    public Integer getNum_seat() { return num_seat; }

    public Long getTs_subscribe() { return ts_subscribe; }

    public Long getTs_login() { return ts_login; }

    public Long getTs_occupy() { return ts_occupy; }

    public Long getTs_reserve() { return ts_reserve; }

    public Long getTs_step_out() { return ts_step_out; }

    public Long getTs_get_penalty() { return ts_get_penalty; }

    public Long getTs_get_block() { return ts_get_block; }

    // setters

    public void setStatus(String statusString) {
        if (statusString == null){
            status = null;
        } else {
            this.status = Status_user.valueOf(statusString);
        }
    }

    public void setNum_section(Integer num_section) { this.num_section = num_section; }

    public void setNum_seat(Integer num_seat) { this.num_seat = num_seat; }


    public void setTs_subscribe(Long time) {
        this.ts_subscribe = time;
    }

    public void setTs_login(Long time){
        this.ts_occupy = time;
    }

    public void setTs_occupy(Long time) {
        this.ts_occupy = time;
    }

    public void setTs_reserve(Long time) {
        this.ts_reserve = time;
    }

    public void setTs_step_out(Long time) {
        this.ts_step_out = time;
    }

    public void setTs_get_penalty(Long ts_get_penalty) { this.ts_get_penalty = ts_get_penalty; }

    public void setTs_get_block(Long time) {
        this.ts_get_block = time;
    }



    // [END]DON'T USE THESE FUNCTIONS. THEY'RE FOR FIREBASE REALTIME DATABASE





    // USER FUNCTIONS
    @Exclude
    public String getEmailForSingleEvent() {
        if (mFirebaseUtil.getCurrentUser() == null) {
            Log.e(TAG, "getCurrentUser() is null");
            return "NO EMAIL";
        }
        return mFirebaseUtil.getCurrentUser().getEmail();
    }



    @Exclude
    public void getStatusForSingleEvent(final MyCallback<Status_user> myCallback) {
        mUserRef.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status_user value = Status_user.valueOf(dataSnapshot.getValue(String.class));
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getNum_sectionForSingleEvent(final MyCallback<Integer> myCallback) {
        mUserRef.child("num_section").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getNum_seatForSingleEvent(final MyCallback<Integer> myCallback) {
        mUserRef.child("num_seat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    @Exclude
    public void getTSForSingleEvent(String title, final MyCallback<Long> myCallback) {
        mUserRef.child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    @Exclude
    public void getTs_loginForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("ts_login").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getTs_subscribeTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("ts_subscribe").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getTs_reserveForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("ts_reserve").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getTs_occupyForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("ts_occupy").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getTs_step_outForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("ts_step_out").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    @Exclude
    public void getTs_get_penaltyForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("ts_get_penalty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getTs_get_blockForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("ts_get_block").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }




    // TODO : verify 하는 것 구현하기

    @Exclude
    public void setEmail_addressForSingleEvent(String email) {
        mFirebaseUtil.getCurrentUser().updateEmail(email);
        mUserRef.child("email_address").setValue(email);
    }

    @Exclude
    public void setStatusForSingleEvent(Status_user status) {
        mUserRef.child("status").setValue(status.name());
    }

    @Exclude
    public void setNum_sectionForSingleEvent(Integer sectionNum) {
        mUserRef.child("num_section").setValue(sectionNum);
    }

    @Exclude
    public void setNum_seatForSingleEvent(Integer seatNum) {
        mUserRef.child("num_seat").setValue(seatNum);
    }




    @Exclude
    public void user_login() {
        Log.d(TAG, "user login");
        setStatusForSingleEvent(Status_user.ONLINE);
        mUserRef.child("ts_login").setValue(System.currentTimeMillis());


    }


    // logout means that He/She will not use this service for a while.
    @Exclude
    public void user_logout() {
        Log.d(TAG, "user logout -> delete UserReference1");
        mUserRef.removeValue();

    }

    @Exclude
    public void user_subscribe() {
        Log.d(TAG, "user subscribe");
        setStatusForSingleEvent(Status_user.SUBSCRIBING);
        mUserRef.child("ts_subscribe").setValue(System.currentTimeMillis());


    }

    @Exclude
    public void user_unsubscribe() {
        Log.d(TAG, "user unsubscribe");
        setStatusForSingleEvent(Status_user.ONLINE);
        mUserRef.child("ts_subscribe").removeValue();

    }


    @Exclude
    public void user_reserve(Integer num_section, Integer num_seat) {
        mFirebaseUtil.getSeatsRef().child(num_section.toString()).child("_" + num_seat.toString()).setValue(mFirebaseUtil.getAuth().getUid());
        Log.d(TAG, "user reserve");
        setStatusForSingleEvent(Status_user.RESERVING);
        setNum_sectionForSingleEvent(num_section);
        setNum_seatForSingleEvent(num_seat);
        mUserRef.child("ts_reserve").setValue(System.currentTimeMillis());
        mUserRef.child("ts_subscribe").removeValue();

    }



    @Exclude
    public void user_cancel_reservation() {
        getNum_sectionForSingleEvent(new MyCallback<Integer>() {
            @Override
            public void onCallback(final Integer section) {
                getNum_seatForSingleEvent(new MyCallback<Integer>() {
                    @Override
                    public void onCallback(Integer seatnum) {
                        mFirebaseUtil.getSeatsRef().child(section.toString()).child("_" + seatnum.toString()).setValue("None");
                        Log.d(TAG, "user cancel reservation");
                        setStatusForSingleEvent(Status_user.ONLINE);
                        setNum_sectionForSingleEvent(null);
                        setNum_seatForSingleEvent(null);
                        mUserRef.child("ts_reserve").removeValue();
                    }
                });
            }
        });
    }

    @Exclude
    public void user_reservation_over() {
        getNum_sectionForSingleEvent(new MyCallback<Integer>() {
            @Override
            public void onCallback(final Integer section) {
                getNum_seatForSingleEvent(new MyCallback<Integer>() {
                    @Override
                    public void onCallback(Integer seatnum) {
                        mFirebaseUtil.getSeatsRef().child(section.toString()).child("_" + seatnum.toString()).setValue("None");
                        Log.d(TAG, "user reservation over");
                        setStatusForSingleEvent(Status_user.RESERVING_OVER);
                        // TODO :
                        setNum_sectionForSingleEvent(null);
                        setNum_seatForSingleEvent(null);
                    }
                });
            }
        });
    }

    @Exclude
    public void user_reservation_over_confirm() {
        Log.d(TAG, "user reservation over confirm");
        setStatusForSingleEvent(Status_user.ONLINE);
        mUserRef.child("ts_reserve").removeValue();
    }



    @Exclude
    public void user_occupy() {
        Log.d(TAG, "user occupy");
        setStatusForSingleEvent(Status_user.OCCUPYING);
        mUserRef.child("ts_occupy").setValue(System.currentTimeMillis());
        mUserRef.child("ts_reserve").removeValue();

    }

    // stop means that He/She wants to stop using that seat.
    @Exclude
    public void user_stop() {
        getNum_sectionForSingleEvent(new MyCallback<Integer>() {
            @Override
            public void onCallback(final Integer section) {
                getNum_seatForSingleEvent(new MyCallback<Integer>() {
                    @Override
                    public void onCallback(Integer seatnum) {
                        mFirebaseUtil.getSeatsRef().child(section.toString()).child("_" + seatnum.toString()).setValue("None");
                        Log.d(TAG, "user stop");
                        setStatusForSingleEvent(Status_user.ONLINE);
                        mUserRef.child("ts_occupy").removeValue();
                        mUserRef.child("ts_step_out").removeValue();
                        mUserRef.child("num_section").removeValue();
                        mUserRef.child("num_seat").removeValue();
                    }
                });
            }
        });
    }

    @Exclude
    public void user_occupy_over() {

        getNum_sectionForSingleEvent(new MyCallback<Integer>() {
            @Override
            public void onCallback(final Integer section) {
                getNum_seatForSingleEvent(new MyCallback<Integer>() {
                    @Override
                    public void onCallback(Integer seatnum) {
                        mFirebaseUtil.getSeatsRef().child(section.toString()).child("_" + seatnum.toString()).setValue("None");
                        Log.d(TAG, "user occupy over");
                        setStatusForSingleEvent(Status_user.OCCUPYING_OVER);
                        mUserRef.child("ts_step_out").removeValue();
                        mUserRef.child("num_section").removeValue();
                        mUserRef.child("num_seat").removeValue();
                    }
                });
            }
        });
    }

    @Exclude
    public void user_occupy_over_confirm() {
        Log.d(TAG, "user occupy over confirm");
        setStatusForSingleEvent(Status_user.ONLINE);
        mUserRef.child("ts_occupy").removeValue();
    }



    @Exclude
    public void user_step_out() {
        Log.d(TAG, "user step out");
        setStatusForSingleEvent(Status_user.STEPPING_OUT);
        mUserRef.child("ts_step_out").setValue(System.currentTimeMillis());

    }

    @Exclude
    public void user_return_to_seat() {
        Log.d(TAG, "user return to seat");
        setStatusForSingleEvent(Status_user.OCCUPYING);
        mUserRef.child("ts_step_out").removeValue();

    }

    @Exclude
    public void user_step_out_over() {

        mFirebaseUtil.getSeatsRef().child(num_section.toString()).child("_" + num_seat.toString()).setValue("None");
        Log.d(TAG, "user step out over");
        setStatusForSingleEvent(Status_user.STEPPING_OUT_OVER);
        mUserRef.child("ts_step_out").removeValue();

        getNum_sectionForSingleEvent(new MyCallback<Integer>() {
            @Override
            public void onCallback(final Integer section) {
                getNum_seatForSingleEvent(new MyCallback<Integer>() {
                    @Override
                    public void onCallback(Integer seatnum) {
                        mFirebaseUtil.getSeatsRef().child(section.toString()).child("_" + seatnum.toString()).setValue("None");
                        Log.d(TAG, "user step out over");
                        setStatusForSingleEvent(Status_user.STEPPING_OUT_OVER);
                        mUserRef.child("ts_step_out").removeValue();
                    }
                });
            }
        });

    }

    @Exclude
    public void user_step_out_over_confirm() {
        Log.d(TAG, "user return to seat");
        setStatusForSingleEvent(Status_user.ONLINE);
        mUserRef.child("ts_step_out").removeValue();

    }



    @Exclude
    public void user_get_penalty() {
        Log.d(TAG, "user get penalty");
        setStatusForSingleEvent(Status_user.PAYING_PENALTY);
        mUserRef.child("ts_reserve").removeValue();
        mUserRef.child("ts_get_penalty").setValue(System.currentTimeMillis());

    }

    @Exclude
    public void user_cancel_penalty() {
        Log.d(TAG, "user cancel penalty");
        setStatusForSingleEvent(Status_user.ONLINE);
        mUserRef.child("ts_reserve").removeValue();
        mUserRef.child("ts_get_penalty").removeValue();

    }

    @Exclude
    public void user_get_block() {
        Log.d(TAG, "user get block");
        setStatusForSingleEvent(Status_user.BEING_BLOCKED);
        mUserRef.child("ts_get_block").setValue(System.currentTimeMillis());
        mUserRef.child("num_section").removeValue();
        mUserRef.child("num_seat").removeValue();
        mUserRef.child("ts_subscribe").removeValue();
        mUserRef.child("ts_reserve").removeValue();
        mUserRef.child("ts_occupy").removeValue();
        mUserRef.child("ts_step_out").removeValue();
        mUserRef.child("ts_get_penalty").removeValue();




    }

    @Exclude
    public void user_cancel_block() {
        Log.d(TAG, "user cancel block");
        setStatusForSingleEvent(Status_user.ONLINE);
        mUserRef.child("ts_get_block").removeValue();

    }

    @Exclude
    public DatabaseReference get_user_ref() {
        return mUserRef;
    }

}