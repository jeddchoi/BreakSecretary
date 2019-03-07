package com.breaktime.breaksecretary.Util;


import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.breaktime.breaksecretary.model.User;

import java.util.Locale;

public abstract class TimeCounter {
    private static final String TAG = TimeCounter.class.getName();
    protected User mUser;

    protected TextView mTextViewCountDown;
    protected ProgressBar mProgressBarCountDown;
    protected CountDownTimer mCountDownTimer;

    protected boolean mTimerRunning;

    protected Long mStartTimeInMillis; // setting에서 가져온 시간
    protected Long mTimeLeftInMillis; // 앞으로 남은 시간
    protected Long mEndTime; // 끝날 시간

    public TimeCounter(User mUser, TextView tv, ProgressBar pb, Long minsInput) {
        this.mUser = mUser;
        mTextViewCountDown = tv;
        mProgressBarCountDown = pb;
        setTime(minsInput * 1000L * 60L); // minutes = 1000 * 60 milliseconds
        mProgressBarCountDown.setMax((int) (minsInput * 60));
    }

    private void setTime(Long milliseconds) {
        mStartTimeInMillis = milliseconds;
        Log.d(TAG, mStartTimeInMillis.toString());
        resetTimer();
    }

    public void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownUI();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownUI();
        updateWatchInterface();
    }

    private void updateCountDownUI() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }


        Log.d(TAG, timeLeftFormatted);
        mTextViewCountDown.setText(timeLeftFormatted);
        mProgressBarCountDown.setProgress((int) (mTimeLeftInMillis / 1000));
    }

    public abstract void updateWatchInterface();
//        if (mTimerRunning) {
////            mButtonSet.setVisibility(View.INVISIBLE);
////            mButtonReset.setVisibility(View.INVISIBLE);
////            mButtonStartPause.setText("Pause");
//        } else {
////            mButtonSet.setVisibility(View.VISIBLE);
////            mButtonStartPause.setText("Start");
//
//            if (mTimeLeftInMillis < 1000) {
////                mButtonStartPause.setVisibility(View.INVISIBLE);
//            } else {
////                mButtonStartPause.setVisibility(View.VISIBLE);
//            }
//
//            if (mTimeLeftInMillis < mStartTimeInMillis) {
////                mButtonReset.setVisibility(View.VISIBLE);
//            } else {
////                mButtonReset.setVisibility(View.INVISIBLE);
//            }
//        }


    public void onStop(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    public void onStart(SharedPreferences prefs) {

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownUI();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis < 0L) {
                mTimeLeftInMillis = 0L;
                mTimerRunning = false;
                updateCountDownUI();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }
}