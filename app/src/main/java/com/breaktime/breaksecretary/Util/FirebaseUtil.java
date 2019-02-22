package com.breaktime.breaksecretary.Util;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.breaktime.breaksecretary.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUtil {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRootRef;
    private DatabaseReference mUsersRef;
    private DatabaseReference mSettingRef;
    private DatabaseReference mSeatsRef;
    private DatabaseReference mCounterRef;
    private DatabaseReference mBlackListRef;

    private FirebaseAuth mAuth;

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



}