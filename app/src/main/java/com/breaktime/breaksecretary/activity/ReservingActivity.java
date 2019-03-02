package com.breaktime.breaksecretary.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.breaktime.breaksecretary.Application.App;
import com.breaktime.breaksecretary.Observer;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.model.User;
import com.dinuscxj.progressbar.CircleProgressBar;

public class ReservingActivity extends AppCompatActivity implements Observer {

    @Override
    public void update(User.Status_user status) {
        Log.d("TTT", "Call update in RA :"+status);
        switch (status){
            case OCCUPYING:
            case RESERVING_OVER:
                finish();
            break;
        }
    }

    CircleProgressBar mProgressBar;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((App)getApplicationContext()).ma.deleteObserver(this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserving);

        mProgressBar=findViewById(R.id.progressbar);
        mProgressBar.setMax(10);
        mProgressBar.setProgressTextSize(80);
        mProgressBar.setProgressFormatter(new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return max-progress+"s";
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(
                messageReceiver, new IntentFilter("from_beacon"));

        ((App)getApplicationContext()).ma.registerObserver(this);

    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int dis = intent.getIntExtra("msg", 0);
            mProgressBar.setProgress(dis);
        }
    };

}
