package com.breaktime.breaksecretary.Util;

import com.breaktime.breaksecretary.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRootRef;

    private FirebaseAuth mAuth;


    public FirebaseUtil() {
        mDatabase = FirebaseDatabase.getInstance();
        mRootRef = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
    }

    // Called by MainActivity's onCreated
    public void init(){
        mDatabase = FirebaseDatabase.getInstance();
        mRootRef = mDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuth.useAppLanguage();
    }



    public FirebaseDatabase getDatabase() {
        return mDatabase;
    }

    public DatabaseReference getRootRef() {
        return mRootRef;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }



}