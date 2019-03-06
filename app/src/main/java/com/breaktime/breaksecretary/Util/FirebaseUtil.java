package com.breaktime.breaksecretary.Util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.breaktime.breaksecretary.model.MyCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseUtil {
    private static final String TAG = FirebaseUtil.class.getName();

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRootRef;
    private DatabaseReference mUsersRef;
    private DatabaseReference mSettingRef;
    private DatabaseReference mSeatsRef;
    private DatabaseReference mCounterRef;
    private DatabaseReference mBlackListRef;

    private FirebaseAuth mAuth;
    private Map<String, Long> mLimits;

    public FirebaseUtil() {
        mDatabase = FirebaseDatabase.getInstance();
        mRootRef = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();

        mUsersRef = mRootRef.child("Users");
        mSettingRef = mRootRef.child("Setting");
        mSeatsRef = mRootRef.child("Seats");
        mCounterRef = mRootRef.child("Counter");
        mBlackListRef = mRootRef.child("BlockedUsers");

        downloadLimits();

    }

    public DatabaseReference getRootRef() {
        return mRootRef;
    }

    public DatabaseReference getUsersRef() {
        return mUsersRef;
    }

    public DatabaseReference getSettingRef() {
        return mSettingRef;
    }

    public DatabaseReference getSeatsRef() {
        return mSeatsRef;
    }

    public DatabaseReference getCounterRef() {
        return mCounterRef;
    }

    public DatabaseReference getBlackListRef() {
        return mBlackListRef;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }


    public Map<String, Long> getmLimits() {
        return mLimits;
    }

    private void downloadLimits() {
        getLimitsForSingleEvent(new MyCallback<Map<String, Long>>() {
            @Override
            public void onCallback(Map<String, Long> value) {
                mLimits = value;
                for ( String limit : mLimits.keySet() ) {
                    Log.d(TAG, limit + " = " + mLimits.get(limit).toString());
                }

            }
        });
    }

    private void getLimitsForSingleEvent(final MyCallback<Map<String, Long>> myCallback) {
        mSettingRef.child("Limits").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Long> limits = (Map) dataSnapshot.getValue();
                myCallback.onCallback(limits);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

}