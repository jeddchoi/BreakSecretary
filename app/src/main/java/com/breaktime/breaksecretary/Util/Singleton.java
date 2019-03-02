package com.breaktime.breaksecretary.Util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
문제
1) 멀티쓰레딩 환경에서의 다중생성

 */
public class Singleton {
    // 유일한 인스턴스를 담고있는 static 변수
    private static Singleton singleton;

    private ArrayList<Integer> LimitsArray;

    // 생성자를 private으로 선언함
    private Singleton(){

    }

    // Singleton 인스턴스를 얻기 위한 static 메소드
    public synchronized static Singleton getInstance() {
        // 아직 인스턴스가 생성된적이 없음 = 존재하지 않음
        if(singleton == null) {

            // 인스턴스 생성. 생성자가 private이니까 클래스 내부에서는 가능.
            singleton = new Singleton();
        }
        return singleton;
    }

    public void Init(FirebaseUtil firebaseUtil){
        LimitsArray = new ArrayList<>();

        firebaseUtil.getSettingRef().child("Limits").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("HEE", ds.getKey()+" :"+String.valueOf(ds.getValue(Integer.class)));
                    LimitsArray.add(ds.getValue(Integer.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public int getLimitsReserving(){
        return LimitsArray.get(4);
    }
    public int getLimitsStepOut(){
        return LimitsArray.get(5);
    }

}
