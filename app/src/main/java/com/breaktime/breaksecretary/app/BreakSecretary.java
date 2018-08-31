package com.breaktime.breaksecretary.app;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

public class BreakSecretary extends Application {

    // user_email 은 예시임...
    public static String user_email;

    private static String TAG = "BeanBeanTag";
    private static BreakSecretary instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FirebaseApp.initializeApp(this);
//        KakaoSDK.init(new KakaoAdapter() {
//            @Override
//            public IApplicationConfig getApplicationConfig() {
//                return new IApplicationConfig() {
//                    @Override
//                    public Context getApplicationContext() {
//                        return instance;
//                    }
//                };
//            }
//        });
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
