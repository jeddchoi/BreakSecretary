package com.breaktime.breaksecretary.Util;

import android.os.CountDownTimer;
import android.util.Log;

import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;

public class TimeCounter extends CountDownTimer {
    private static final String TAG = TimeCounter.class.getName();
    private User mUser;
    private String type;
    private Long ts_begin = null;

    public TimeCounter(Integer min_limit, User user, final String type) {
        super(min_limit * 1000 * 60L, 1000);
        mUser = user;
        this.type = type;

    }



    @Override
    public void onTick(long l) {
        Integer sec = (int)(System.currentTimeMillis() - ts_begin)/1000;
        Log.d(TAG, type + " timer : " + sec.toString() + " seconds");
    }


    @Override
    public void onFinish() {
        Log.d(TAG, "finish timer : " + type);
    }

}