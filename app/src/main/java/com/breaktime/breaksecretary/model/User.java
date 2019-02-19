package com.breaktime.breaksecretary.model;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;


@SuppressWarnings("serial")
@IgnoreExtraProperties
public class User {
    public static final String TAG = "USER CLASS";

    /* status constant */
    public static final int STATUS_NOTHING = 0;
    public static final int STATUS_SITTING = 1;
    public static final int STATUS_RESERVING = 2;
    public static final int STATUS_BEING_AWAY = 3;
    public static final int STATUS_PENALTY = 4;
    public static final int STATUS_BLOCKED = 5;

    @Exclude
    private FirebaseUser currentUser;
    @Exclude
    private DatabaseReference mUserRef;

    // User Information
    private String emailAddress = null;
    private Integer status = STATUS_NOTHING;
    private Integer mysectionNum = null;
    private Integer myseatNum = null;

    //private Boolean isExtended = false;
    //private Map<String, Boolean> favoriteSeats = new HashMap<>();

    // TimeStamp
    private Long notifyTS = null;
    private Long loginTS = null;
    private Long startTS = null;
    private Long reserveTS = null;
    private Long idleTS = null;
    private Long penaltyTS = null;
    private Long blockTS = null;


    public User(FirebaseUser currentUser, DatabaseReference rootRef) {
        this.currentUser = currentUser;
        emailAddress = currentUser.getEmail();

        mUserRef = rootRef.child("Users").child(currentUser.getUid());
    }

    // Default Constructor
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    // [START]DON'T USE THESE FUNCTIONS. THEY'RE FOR FIREBASE REALTIME DATABASE
    // getters

    public String getEmailAddress() { return emailAddress; }

    public Integer getStatus() { return status; }

    public Integer getMysectionNum() { return mysectionNum; }

    public Integer getMyseatNum() { return myseatNum; }

    //public Boolean getIsExtended() { return isExtended; }

    public Long getNotifyTS() { return notifyTS; }

    public Long getLoginTS() { return loginTS; }

    public Long getStartTS() { return startTS; }

    public Long getReserveTS() { return reserveTS; }

    public Long getIdleTS() { return idleTS; }

    public Long getPenaltyTS() { return penaltyTS; }

    public Long getBlockTS() { return blockTS; }

    // setters

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public void setStatus(Integer status) { this.status = status; }

    public void setMysectionNum(Integer mysectionNum) { this.mysectionNum = mysectionNum; }

    public void setMyseatNum(Integer myseatNum) { this.myseatNum = myseatNum; }

    //public void setIsExtended(Boolean isExtended) { this.isExtended = isExtended; }

    public void setNotifyTS(Long time) {
        this.notifyTS = time;
    }

    public void setLoginTS(Long time){
        this.startTS = time;
    }

    public void setStartTS(Long time) {
        this.startTS = time;
    }

    public void setReserveTS(Long time) {
        this.reserveTS = time;
    }

    public void setIdleTS(Long time) {
        this.idleTS = time;
    }

    public void setPenaltyTS(Long penaltyTS) { this.penaltyTS = penaltyTS; }

    public void setBlockTS(Long time) {
        this.blockTS = time;
    }



    /*
    public void setFavoriteSeats(Map<String, Boolean> mymap) {
        this.favoriteSeats = mymap;
    }
    */

    // [END]DON'T USE THESE FUNCTIONS. THEY'RE FOR FIREBASE REALTIME DATABASE





    // USER FUNCTIONS


    @Exclude
    public String getEmailForSingleEvent() {
        return (emailAddress = currentUser.getEmail());
    }


    @Exclude
    public void getStatusForSingleEvent(final MyCallback<Integer> myCallback) {
        mUserRef.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getSectionNumForSingleEvent(final MyCallback<Integer> myCallback) {
        mUserRef.child("mysectionNum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getSeatNumForSingleEvent(final MyCallback<Integer> myCallback) {
        mUserRef.child("myseatNum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }


    @Exclude
    public void getLoginTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("loginTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getStartTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("startTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getIdleTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("idleTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getReserveTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("reserveTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getPenaltyTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("penaltyTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getBlockTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("blockTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    @Exclude
    public void getNotifyTSForSingleEvent(final MyCallback<Long> myCallback) {
        mUserRef.child("notifyTS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                myCallback.onCallback(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }



    @Exclude
    public void setEmailForSingleEvent(String email) {
        currentUser.updateEmail(email);
        mUserRef.child("emailAddress").setValue(email);
    }

    @Exclude
    public void setSectionNumForSingleEvent(Integer sectionNum) {
        mUserRef.child("mysectionNum").setValue(sectionNum);
    }

    @Exclude
    public void setSeatNumForSingleEvent(Integer seatNum) {
        mUserRef.child("myseatNum").setValue(seatNum);
    }

    @Exclude
    public void setStatusForSingleEvent(Integer status) {
        mUserRef.child("status").setValue(status);
    }

    @Exclude
    public void userLogin() {
        mUserRef.child("loginTS").setValue(System.currentTimeMillis());
    }

    @Exclude
    public void userStart() {
        mUserRef.child("reserveTS").setValue(null);
        mUserRef.child("startTS").setValue(System.currentTimeMillis());
        setStatusForSingleEvent(STATUS_SITTING);
    }

    @Exclude
    public void userStop() {
        getStatusForSingleEvent(new MyCallback<Integer>() {
            @Override
            public void onCallback(Integer value) {
                status = value;
            }
        });

        switch (status) {
            case STATUS_NOTHING :

                break;

            case STATUS_SITTING :
                mUserRef.child("startTS").setValue(null);
                setSectionNumForSingleEvent(null);
                setSeatNumForSingleEvent(null);
                break;

            case STATUS_RESERVING :
                mUserRef.child("reserveTS").setValue(null);
                setSectionNumForSingleEvent(null);
                setSeatNumForSingleEvent(null);
                break;

            case STATUS_BEING_AWAY :
                mUserRef.child("startTS").setValue(null);
                mUserRef.child("idleTS").setValue(null);
                setSectionNumForSingleEvent(null);
                setSeatNumForSingleEvent(null);
                break;

            case STATUS_PENALTY :
                break;
            case STATUS_BLOCKED :
                break;

            default:
                break;
        }

        setStatusForSingleEvent(STATUS_NOTHING);
    }

    @Exclude
    public void userIdle() {
        mUserRef.child("idleTS").setValue(System.currentTimeMillis());
        setStatusForSingleEvent(STATUS_BEING_AWAY);
    }

    @Exclude
    public void userReturnToSeat() {
        mUserRef.child("idleTS").setValue(null);
        setStatusForSingleEvent(STATUS_SITTING);
    }

    @Exclude
    public void userReserve(Integer sectionNum, Integer seatNum) {
        mUserRef.child("reserveTS").setValue(System.currentTimeMillis());
        setSectionNumForSingleEvent(sectionNum);
        setSeatNumForSingleEvent(seatNum);

        setStatusForSingleEvent(STATUS_RESERVING);
    }

    @Exclude
    public void userPenalty() {

        mUserRef.child("penaltyTS").setValue(System.currentTimeMillis());
        setStatusForSingleEvent(STATUS_PENALTY);

    }


    @Exclude
    public void userBlock() {
        mUserRef.child("blockTS").setValue(System.currentTimeMillis());
        setStatusForSingleEvent(STATUS_BLOCKED);
    }


    @Exclude
    public void userNotify(Boolean decision) {
        if ( decision )
            mUserRef.child("notifyTS").setValue(System.currentTimeMillis());
        else
            mUserRef.child("notifyTS").setValue(null);
    }



    @Exclude
    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    @Exclude
    public DatabaseReference getUserRef() {
        return mUserRef;
    }

    @Exclude
    public Boolean isVerified() {
        return currentUser.isEmailVerified();
    }

    @Exclude
    public void setVerified(Activity mContext) {
        currentUser.sendEmailVerification().addOnCompleteListener(mContext, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "sendEmailVerification : succeed to " + emailAddress);

                } else {
                    Log.e(TAG, "sendEmailVerification : failed to " + emailAddress, task.getException());
                }
            }
        });
    }


/*
    @Exclude
    public Map<String, Boolean> getFavoriteSeatsForSingleEvent() {
        mUserRef.child("favoriteSeats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoriteSeats = dataSnapshot.getValue(Map.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return favoriteSeats;
    }

    @Exclude
    public ArrayList<String> getFavoritesSeats() {
        return new ArrayList<String>(getFavoriteSeatsForSingleEvent().keySet());
    }


    @Exclude
    public void setFavoritesSeat(String seatNum, Boolean decision) {
//        favoriteSeats.put(myseatNum, decision);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(seatNum, decision);
        mUserRef.child("favoriteSeats").updateChildren(childUpdates);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("emailAddress", emailAddress);
        result.put("myseatNum", myseatNum);
        result.put("status", status);

        result.put("isExtended", isExtended);
//        result.put("isVerified", isVerified);
        result.put("favoriteSeats", favoriteSeats);
        result.put("loginTS", loginTS);
        result.put("startTS", startTS);
        result.put("idleTS", idleTS);
        result.put("reserveTS", reserveTS);
        result.put("blockTS", blockTS);
        result.put("notifyTS", notifyTS);

        return result;
    }
*/





}