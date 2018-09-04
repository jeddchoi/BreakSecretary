package com.breaktime.breaksecretary.model;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.UserHandle.readFromParcel;

@SuppressWarnings("serial")
@IgnoreExtraProperties
public class User {
    /* status constant */
    public static final int STATUS_NOTHING = 0;
    public static final int STATUS_SITTING = 1;
    public static final int STATUS_BEING_AWAY = 2;
    public static final int STATUS_RESERVING = 3;
    public static final int STATUS_PENALTY = 4;
    public static final int STATUS_BLOCKED = 5;
    public static final String TAG = "USER CLASS";

    @Exclude
    private FirebaseUser currentUser;
    @Exclude
    private DatabaseReference mLoginUserRef;
    @Exclude
    private DatabaseReference mRegisteredUserRef;

    // User Information
    private String email = null;
    private String seatNum = null;
    private Integer status = STATUS_NOTHING;
    private Boolean isExtended = false;
    private Map<String, Boolean> favoriteSeats = new HashMap<>();

    // TimeStamp
    private Long lastLoginTimeStamp = null;
    private Long lastStartSittingTimeStamp = null;
    private Long lastBeingAwayTimeStamp = null;
    private Long lastReservingTimeStamp = null;
    private Long lastBlockedTimeStamp = null;
    private Long lastSetNotifiedTimeStamp = null;


    public User(FirebaseUser currentUser, DatabaseReference rootRef) {
        this.currentUser = currentUser;
        email = currentUser.getEmail();

        mRegisteredUserRef = rootRef.child("registeredUsers").child(currentUser.getUid());
        mLoginUserRef = rootRef.child("loginUsers").child(currentUser.getUid());
        lastLoginTimeStamp = System.currentTimeMillis();
    }

    // Default Constructor
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }


    // [START]DON'T USE THESE FUNCTIONS. THEY'RE FOR FIREBASE REALTIME DATABASE
    // getters

    public String getEmail() { return email; }

    public String getSeatNum() { return seatNum; }

    public Integer getStatus() { return status; }

    public Boolean getIsExtended() { return isExtended; }

    public Long getLastLoginTimeStamp() { return lastLoginTimeStamp; }

    public Long getLastStartSittingTimeStamp() { return lastStartSittingTimeStamp; }

    public Long getLastBeingAwayTimeStamp() { return lastBeingAwayTimeStamp; }

    public Long getLastReservingTimeStamp() { return lastReservingTimeStamp; }

    public Long getLastBlockedTimeStamp() { return lastBlockedTimeStamp; }

    public Long getLastSetNotifiedTimeStamp() { return lastSetNotifiedTimeStamp; }

    // setters

    public void setEmail(String email) { this.email = email; }

    public void setSeatNum(String seatNum) { this.seatNum = seatNum; }

    public void setStatus(Integer status) { this.status = status; }

    public void setIsExtended(Boolean isExtended) { this.isExtended = isExtended; }

    public void setLastLoginTimeStamp(Long time){
        this.lastStartSittingTimeStamp = time;
    }

    public void setLastStartSittingTimeStamp(Long time) {
        this.lastStartSittingTimeStamp = time;
    }

    public void setLastBeingAwayTimeStamp(Long time) {
        this.lastBeingAwayTimeStamp = time;
    }

    public void setLastReservingTimeStamp(Long time) {
        this.lastReservingTimeStamp = time;
    }

    public void setLastBlockedTimeStamp(Long time) {
        this.lastBlockedTimeStamp = time;
    }

    public void setLastSetNotifiedTimeStamp(Long time) {
        this.lastSetNotifiedTimeStamp = time;
    }

    public void setFavoriteSeats(Map<String, Boolean> mymap) {
        this.favoriteSeats = mymap;
    }
    // [END]DON'T USE THESE FUNCTIONS. THEY'RE FOR FIREBASE REALTIME DATABASE







    // USER FUNCTIONS

    @Exclude
    public void addToRegRef() {
        mRegisteredUserRef.setValue(this);
    }

    @Exclude
    public void addToLogRef() {
        mLoginUserRef.setValue(this);
    }


    @Exclude
    public String getEmailForSingleEvent() {
        email = currentUser.getEmail();

        if ( email == null )
            return "No email";
        return email;
    }

    @Exclude
    public String getSeatNumForSingleEvent() {
        mLoginUserRef.child("seatNum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                seatNum = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( seatNum == null )
            return "None";
        return seatNum;
    }

    @Exclude
    public Integer getStatusForSingleEvent() {
        mLoginUserRef.child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( status == null )
            return STATUS_NOTHING;
        return status;
    }

    @Exclude
    public Boolean getIsExtendedForSingleEvent() {
        mLoginUserRef.child("isExtended").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isExtended = dataSnapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (isExtended == null)
            return false;
        return isExtended;
    }

    @Exclude
    public Long getLastLoginTimeStampForSingleEvent() {
        mLoginUserRef.child("lastLoginTimeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastLoginTimeStamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( lastLoginTimeStamp == null )
            return 0L;
        return lastLoginTimeStamp;
    }

    @Exclude
    public Long getLastStartSittingTimeStampForSingleEvent() {
        mLoginUserRef.child("lastStartSittingTimeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastStartSittingTimeStamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( lastStartSittingTimeStamp == null )
            return 0L;
        return lastStartSittingTimeStamp;
    }

    @Exclude
    public Long getLastBeingAwayTimeStampForSingleEvent() {
        mLoginUserRef.child("lastBeingAwayTimeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastBeingAwayTimeStamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( lastBeingAwayTimeStamp == null )
            return 0L;
        return lastBeingAwayTimeStamp;
    }

    @Exclude
    public Long getLastReservingTimeStampForSingleEvent() {
        mLoginUserRef.child("lastReservingTimeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastReservingTimeStamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( lastReservingTimeStamp == null )
            return 0L;
        return lastReservingTimeStamp;
    }

    @Exclude
    public Long getLastBlockedTimeStampForSingleEvent() {
        mLoginUserRef.child("lastBlockedTimeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastBlockedTimeStamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( lastBlockedTimeStamp == null )
            return 0L;
        return lastBlockedTimeStamp;
    }

    @Exclude
    public Long getLastSetNotifiedTimeStampForSingleEvent() {
        mLoginUserRef.child("lastSetNotifiedTimeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastSetNotifiedTimeStamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if ( lastSetNotifiedTimeStamp == null )
            return 0L;
        return lastSetNotifiedTimeStamp;
    }

    @Exclude
    public Boolean isNotified() {
        mLoginUserRef.child("lastSetNotifiedTimeStamp").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastSetNotifiedTimeStamp = dataSnapshot.getValue(Long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // 1시간 이내 일 경우
        if ( lastSetNotifiedTimeStamp != null && (System.currentTimeMillis() - lastSetNotifiedTimeStamp)/60000 <= 60 )
            return true;
        else
            return false;
    }



    @Exclude
    public void setEmailForSingleEvent(String email) {
        currentUser.updateEmail(email);
        this.email = email;
        mLoginUserRef.child("email").setValue(email);
    }

    @Exclude
    public void setSeatNumForSingleEvent(String seatNum) {
        mLoginUserRef.child("seatNum").setValue(seatNum);
    }

    @Exclude
    public void setStatusForSingleEvent(Integer status) {
        mLoginUserRef.child("status").setValue(status);
    }

    @Exclude
    public void setIsExtendedForSingleEvent(Boolean isExtended) {
        mLoginUserRef.child("isExtended").setValue(isExtended);
    }

    @Exclude
    public void login() {
        mLoginUserRef.child("lastLoginTimeStamp").setValue(System.currentTimeMillis());
    }

    @Exclude
    public void startSitting(Boolean decision) {
        if (decision) {
            mLoginUserRef.child("lastStartSittingTimeStamp").setValue(System.currentTimeMillis());
            setStatusForSingleEvent(STATUS_SITTING);
        } else
            mLoginUserRef.child("lastStartSittingTimeStamp").setValue(null);
    }

    @Exclude
    public void beAway(Boolean decision) {
        if (decision) {
            mLoginUserRef.child("lastBeingAwayTimeStamp").setValue(System.currentTimeMillis());
            setStatusForSingleEvent(STATUS_BEING_AWAY);
        } else
            mLoginUserRef.child("lastBeingAwayTimeStamp").setValue(null);
    }

    @Exclude
    public void reserve(Boolean decision) {
        if (decision) {
            mLoginUserRef.child("lastReservingTimeStamp").setValue(System.currentTimeMillis());
            setStatusForSingleEvent(STATUS_RESERVING);
        } else
            mLoginUserRef.child("lastReservingTimeStamp").setValue(null);
    }

    @Exclude
    public void blocked(Boolean decision) {
        if (decision) {
            mLoginUserRef.child("lastBlockedTimeStamp").setValue(System.currentTimeMillis());
            setStatusForSingleEvent(STATUS_BLOCKED);
        } else
            mLoginUserRef.child("lastBlockedTimeStamp").setValue(null);
    }


    @Exclude
    public void setNotified(Boolean decision) {
        if ( decision )
            mLoginUserRef.child("lastSetNotifiedTimeStamp").setValue(System.currentTimeMillis());
        else
            mLoginUserRef.child("lastSetNotifiedTimeStamp").setValue(null);
    }



    @Exclude
    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    @Exclude
    public DatabaseReference getLoginRef() {
        return mLoginUserRef;
    }

    @Exclude
    public DatabaseReference getRegistedRef() {
        return mRegisteredUserRef;
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
                    Log.d(TAG, "sendEmailVerification : succeed to " + email);
                } else {
                    Log.e(TAG, "sendEmailVerification : failed to " + email, task.getException());
                }
            }
        });
    }



    @Exclude
    public Map<String, Boolean> getFavoriteSeatsForSingleEvent() {
        mLoginUserRef.child("favoriteSeats").addListenerForSingleValueEvent(new ValueEventListener() {
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
//        favoriteSeats.put(seatNum, decision);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(seatNum, decision);
        mLoginUserRef.child("favoriteSeats").updateChildren(childUpdates);
    }




    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("seatNum", seatNum);
        result.put("status", status);

        result.put("isExtended", isExtended);
//        result.put("isVerified", isVerified);
        result.put("favoriteSeats", favoriteSeats);
        result.put("lastLoginTimeStamp", lastLoginTimeStamp);
        result.put("lastStartSittingTimeStamp", lastStartSittingTimeStamp);
        result.put("lastBeingAwayTimeStamp", lastBeingAwayTimeStamp);
        result.put("lastReservingTimeStamp", lastReservingTimeStamp);
        result.put("lastBlockedTimeStamp", lastBlockedTimeStamp);
        result.put("lastSetNotifiedTimeStamp", lastSetNotifiedTimeStamp);

        return result;
    }






}