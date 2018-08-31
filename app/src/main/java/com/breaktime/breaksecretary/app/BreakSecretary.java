package com.breaktime.breaksecretary.app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

public class BreakSecretary extends Application {

    // user_email 은 예시임...
    public static String user_email;

    private static String TAG = "BeanBeanTag";
    private static BreakSecretary instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static BreakSecretary getInstance(){
        return instance;
    }

    // 이것도 예제
    public static void TestToast(String message){
        Toast.makeText(instance, message, Toast.LENGTH_SHORT).show();
    }

    // For debugging 
    public static void Log(String message){
        Log.d(TAG, message);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }



}
