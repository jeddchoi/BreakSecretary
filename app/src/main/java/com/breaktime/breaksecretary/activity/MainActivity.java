package com.breaktime.breaksecretary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.adapter.MainFragmentPagerAdapter;
import com.breaktime.breaksecretary.app.BreakSecretary;
import com.breaktime.breaksecretary.fragment.MyStatusFragment;
import com.breaktime.breaksecretary.fragment.QuickReserveFragment;
import com.breaktime.breaksecretary.fragment.ReserveAndCheckFragment;
import com.breaktime.breaksecretary.fragment.SettingFragment;
import com.breaktime.breaksecretary.fragment.TimeLineFragment;
import com.breaktime.breaksecretary.model.TestUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    MainFragmentPagerAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BreakSecretary.Log("MainActivity onCreate Called");

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        LoadFragment(adapter);
        viewPager.setAdapter(adapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        // for test
        TestUser.TestUserInit();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Snackbar.make(findViewById(R.id.container), "Sign in with " + currentUser.getEmail(), Snackbar.LENGTH_SHORT).show();
    }



    public void LoadFragment(MainFragmentPagerAdapter adapter){
        adapter.addItem(QuickReserveFragment.newInstance(1));
        adapter.addItem(ReserveAndCheckFragment.newInstance(2));
        adapter.addItem(MyStatusFragment.newInstance(3));
        adapter.addItem(SettingFragment.newInstance(4));
        adapter.addItem(TimeLineFragment.newInstance(5));
        //adapter.addItem(MyStatusFragment_Reserving.newInstance(6));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BreakSecretary.Log("onActivityResult Called");
        BreakSecretary.Log(String.valueOf(requestCode));
        Log.d("TESSS","heellllo");


        if(resultCode == RESULT_OK) {
                BreakSecretary.Log("ok called");
                BreakSecretary.Log(String.valueOf(TestUser.getStatus()));


                //TODO : 뷰페이저 프래그먼트 갱신방법
                onMyStatusFragmentChange();
        }
    }

    //type 0: 비어있음
    //type 1: 이용중
    //type 2: 예약중
    //type 3: 자리비움
    public void onMyStatusFragmentChange(){


        //TODO : 해당 프래그먼트만 업데이트 ... how?
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2,true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        BreakSecretary.Log("MA onStart Called");

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
