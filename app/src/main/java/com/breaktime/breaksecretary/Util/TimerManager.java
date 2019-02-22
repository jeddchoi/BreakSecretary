package com.breaktime.breaksecretary.Util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class TimerManager {
    private static final String TAG = TimerManager.class.getName();
    private TimeCounter timer_login, timer_subscribe, timer_reserve, timer_occupy, timer_step_out, timer_get_penalty, timer_get_block;
    private Integer peak_start, peak_end;

    public TimerManager(DatabaseReference settingRef, final User mUser) {

        settingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Peak")) {
                    DataSnapshot ds = dataSnapshot.child("Peak");
                    peak_start = ds.child("startTime").getValue(Integer.class);
                    peak_end = ds.child("endTime").getValue(Integer.class);
                } else
                    Log.e(TAG, "No Peak Setting");

                if (dataSnapshot.hasChild("Limits")) {
                    DataSnapshot ds = dataSnapshot.child("Limits");

//                    timer_login = new TimeCounter(ds.child("login").getValue(Integer.class), mUser, "ts_reserve");
//                    timer_subscribe = new TimeCounter(ds.child("subscribe").getValue(Integer.class), mUser, "ts_reserve");
//                    timer_reserve = new TimeCounter(ds.child("reserve").getValue(Integer.class), mUser, "ts_reserve");
//                    timer_occupy = new TimeCounter(ds.child("occupy").getValue(Integer.class), mUser, "ts_reserve");
//                    timer_step_out = new TimeCounter(ds.child("step_out").getValue(Integer.class), mUser, "ts_reserve");
//                    timer_get_penalty = new TimeCounter(ds.child("get_penalty").getValue(Integer.class), mUser, "ts_reserve");
//                    timer_get_block = new TimeCounter(ds.child("get_block").getValue(Integer.class), mUser, "ts_reserve");

                } else
                    Log.e(TAG, "No Limits Setting");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}