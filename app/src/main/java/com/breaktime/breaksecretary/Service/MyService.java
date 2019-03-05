package com.breaktime.breaksecretary.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.breaktime.breaksecretary.Application.App;
import com.breaktime.breaksecretary.Observer;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.Util.Singleton;
import com.breaktime.breaksecretary.activity.MainActivity;
import com.breaktime.breaksecretary.model.User;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.HashMap;

import static com.breaktime.breaksecretary.Application.App.CHANNEL_ID;

/*
 거리의 오차로 인하여
 Immediate, near, far 라는 세가지 범주로 거리를 나눈다.
Immediate : 범위 안 / 신호 잡힘
 near     : 범위 밖 / 신호 잡힘
 far       : 범위 밖 / 신호 안잡힘


 */
public class MyService extends Service implements BeaconConsumer, Observer {
    private enum DISTANCE{
        IMMEDIATE, NEAR, FAR
    }

    private static final String TAG = "TEST";
    private int major, minor;
    private User.Status_user status;
    private DISTANCE distance;
    private BeaconManager beaconManager;
    private boolean isOutofRange;
    private int threadhold;
    private int usingtime;
    private User mUser;
    private FirebaseUtil mFirebaseUtil;
    HashMap<User.Status_user, Notification> Notify;

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null) {
            major = intent.getIntExtra("major", 0);
            minor = intent.getIntExtra("minor", 0);
        }
        setStatus(User.Status_user.RESERVING);
        // do heavy work on a background THREAD
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        logg("call onDestroy");
        super.onDestroy();
        beaconManager.unbind(this);

    }


    // ================================================================================================
    @Override
    public void update(User.Status_user status){

    }


    private void setStatus(User.Status_user to){
        stopForeground(true);
        switch (to){
            case RESERVING:
                startForeground(1,Notify.get(User.Status_user.RESERVING));
                break;
            case OCCUPYING:
                startForeground(1,Notify.get(User.Status_user.OCCUPYING));
                distance = DISTANCE.IMMEDIATE;
                status = User.Status_user.OCCUPYING;
                mUser.get_user_ref().child("status").setValue(User.Status_user.OCCUPYING);
                break;
            case STEPPING_OUT:
                startForeground(1,Notify.get(User.Status_user.ONLINE));
                distance = DISTANCE.NEAR;
                status = User.Status_user.STEPPING_OUT;
                mUser.get_user_ref().child("status").setValue(User.Status_user.STEPPING_OUT);
                break;
        }
    }


    private void sendMessage(int threadhold){
        Intent intent = new Intent("from_beacon");
        intent.putExtra("msg", threadhold);
        Log.d("HEEL", "send msg from service");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.removeAllRangeNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                // The callback for these APIs is didRangeBeaconsInRegion(Region region,
                // Collection<Beacon>)which gives you a list of every beacon matched in the last scan interval.
                // unbind 전까지 1초에 한번씩 호출된다.
                threadhold++;
                usingtime++;
                logg("현재 상태 : "+String.valueOf(status));
                logg("threadhold : "+String.valueOf(threadhold));
                logg("usingtime : "+String.valueOf(usingtime));

                if(status == User.Status_user.RESERVING) {
                    sendMessage(threadhold);
                    if (threadhold >= Singleton.getInstance().getLimitsReserving()) {
                        // 예약 타임 아웃
                        //mUser.get_user_ref().child("status").setValue(User.Status_user.RESERVING_OVER);
                        mUser.user_reservation_over();
                        stopForeground(true);
                        stopSelf();
                    }
                }

                if (beacons.size() > 0) {
                    for (Beacon beacon : beacons) {
                        if(major == beacon.getId2().toInt() && minor == beacon.getId3().toInt()){
                            if(status == User.Status_user.RESERVING){// 예약 -> 시작직전의 상황
                                if(beacon.getDistance() < App.EMPTY_RANGE) {
                                    setStatus(User.Status_user.OCCUPYING);
                                    usingtime = 0;
                                }
                            }else{ // 시작 후의 상황
                                switch (status){
                                    case OCCUPYING:
                                        if(beacon.getDistance() > App.EMPTY_RANGE && threadhold > 4){ //
                                            setStatus(User.Status_user.STEPPING_OUT);
                                            threadhold = 0;
                                        }
                                        if(beacon.getDistance() < App.EMPTY_RANGE){
                                            threadhold = 0;
                                        }
                                        break;
                                    case STEPPING_OUT:
                                        if(beacon.getDistance() < App.EMPTY_RANGE){
                                            threadhold = 0;
                                            setStatus(User.Status_user.OCCUPYING);
                                        }
                                        // 비움 초과 시
                                        if(threadhold >= Singleton.getInstance().getLimitsStepOut()){
                                            //mUser.user_step_out_over();
                                            mUser.get_user_ref().child("status").setValue(User.Status_user.STEPPING_OUT_OVER);
                                            mUser.user_step_out_over();
                                            stopForeground(true);
                                            stopSelf();
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        });

        beaconManager.addMonitorNotifier(new MonitorNotifier() {

            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing beacons: " + state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));

        } catch (RemoteException e) {
        }


        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

    private void Init(){
        // 1) init beaconManager
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.bind(this);

        // 2) init Notifications
        Notify = new HashMap<>();
        Intent notificationIntent = new Intent(this, MainActivity.class); // Notif 눌렀을 때 돌아갈 activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notify.put(User.Status_user.RESERVING, new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("예약중")
                .setContentText("에약중입니다.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build());
        Notify.put(User.Status_user.OCCUPYING, new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("사용중")
                .setContentText("사용중입니다.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build());
        Notify.put(User.Status_user.ONLINE, new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("자리비움")
                .setContentText("자리비움중입니다.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build());

        // 3) init fields
        distance = DISTANCE.FAR;
        threadhold = 0;
        usingtime = 0;
        status = User.Status_user.RESERVING;
        mFirebaseUtil = new FirebaseUtil();
        mUser = new User(mFirebaseUtil);

    }



    // =============================================================================================
    // 디버깅 용
    private void logg(String msg){
        Log.d(TAG, msg);
    }

    private void logg(Beacon beacon){
        Log.i(TAG, "The beacon I see is about " + beacon.getDistance() + " meters away.");
        //sendMessage(Double.toString(beacon.getDistance()));
        Log.i(TAG, "Reading…" + "\n" + "proximityUuid:" + " " + beacon.getId1() + "\n" +
                "major:" + " " + beacon.getId2() + "\n" +
                "minor:" + " " + beacon.getId3() +"\n"+
                "Rssi:" + " "+beacon.getRssi());
    }


}