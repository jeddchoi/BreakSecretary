package com.breaktime.breaksecretary.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import com.breaktime.breaksecretary.activity.MainActivity;

public class App extends Application {
    public static final String CHANNEL_ID = "ServiceChannel";
    public static final int EMPTY_RANGE = 3;
    public MainActivity ma;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App", "onCreate");
        createNotificationChannel();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            // oreo 와 같거나 높다면 채널을 생성한다.
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

        }
    }

    public void ref(MainActivity ma){
        this.ma = ma;
    }
}