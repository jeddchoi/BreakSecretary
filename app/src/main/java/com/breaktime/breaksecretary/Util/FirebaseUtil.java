package com.breaktime.breaksecretary.Util;

import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    private static FirebaseDatabase mRootRef;

    // Called by MainActivity's onCreated
    public static void init(){
        mRootRef= FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getLoginUserRootRef(){
        return mRootRef.getReference("LoginUsers");
    }


    public static DatabaseReference getRegisteredUserRootRef(){
        return mRootRef.getReference("RegisteredUsers");
    }


    public static void RegisterNewOne(String Uid, User user){
        getRegisteredUserRootRef().child(Uid).setValue(user);
    }


}
