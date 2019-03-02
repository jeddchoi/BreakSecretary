package com.breaktime.breaksecretary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.breaktime.breaksecretary.Observer;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Subject;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.Util.Singleton;
import com.breaktime.breaksecretary.adapter.FragmentAdapter;
import com.breaktime.breaksecretary.fragment.MyStatusFragment;
import com.breaktime.breaksecretary.fragment.QuickReserveFragment;
import com.breaktime.breaksecretary.fragment.ReserveAndCheckFragment;
import com.breaktime.breaksecretary.fragment.SettingFragment;
import com.breaktime.breaksecretary.fragment.TimeLineFragment;
import com.breaktime.breaksecretary.model.MyCallback;
import com.breaktime.breaksecretary.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends BaseActivity implements View.OnClickListener, Subject {
    private static final String TAG = MainActivity.class.getName();

    public FirebaseUtil mFirebaseUtil;
    public User mUser;

    private TabLayout mTabLayout;

    private int[] tabIcons = {
            R.drawable.ic_flash_on_white_24dp,
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_timeline_white_24dp,
            R.drawable.ic_settings_white_24dp
    };

    public ArrayList<Observer> observers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        observers = new ArrayList<>();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, null));


        initViewPager();


        mFirebaseUtil = new FirebaseUtil();
        mUser = new User(mFirebaseUtil);
        mUser.get_user_ref().child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value != null){
                    notifyTheStatus(User.Status_user.valueOf(value));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        InitSetting();

    }

    public void InitSetting(){
        Singleton.getInstance().Init(mFirebaseUtil);
        /*
        // Setting updates
        mFirebaseUtil.getSettingRef().child("Limits").child("reserve").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int a = dataSnapshot.getValue(Integer.class);
                Singleton.setLimitsA(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mFirebaseUtil.getSettingRef().child("Limits").child("step_out").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
    }

    @Override
    public void registerObserver(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void deleteObserver(Observer ob) {
        int index = observers.indexOf(ob);
        observers.remove(index);
    }

    @Override
    public void notifyTheStatus(User.Status_user status) {
        Log.d("TEST", "call notify in subject");
        // To observers
        for(Observer ob : observers){
            ob.update(status);
        }

        // To service
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");

        super.onDestroy();



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState()");
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart()");
        super.onStart();



        if (mFirebaseUtil.getCurrentUser() == null || !mFirebaseUtil.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(MainActivity.this, FirstActivity.class));
            finish();
        } else {
            mUser.user_login();
            show_snackbar_msg("Successfully signed in : " + mUser.getEmailForSingleEvent(), true);
        }

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");
        super.onStop();

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        super.onBackPressed();
    }


    private void initViewPager() {
        mTabLayout = findViewById(R.id.tab_layout_main);
        ViewPager mViewPager = findViewById(R.id.view_pager_main);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.tab_title_main_1));
        titles.add(getString(R.string.tab_title_main_2));
        titles.add(getString(R.string.tab_title_main_3));
        titles.add(getString(R.string.tab_title_main_4));
        titles.add(getString(R.string.tab_title_main_5));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(4)));

        List<Fragment> fragments = new ArrayList<>();
        QuickReserveFragment quickReserveFragment = new QuickReserveFragment();
        fragments.add(quickReserveFragment);
        registerObserver(quickReserveFragment);

        fragments.add(new ReserveAndCheckFragment());

        MyStatusFragment myStatusFragment = new MyStatusFragment();
        fragments.add(myStatusFragment);
        registerObserver(myStatusFragment);

        fragments.add(new TimeLineFragment());

        fragments.add(new SettingFragment());


        mViewPager.setOffscreenPageLimit(4);
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();



    }

    private void setupTabIcons() {
        Objects.requireNonNull(mTabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(mTabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(mTabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(mTabLayout.getTabAt(3)).setIcon(tabIcons[3]);
        Objects.requireNonNull(mTabLayout.getTabAt(4)).setIcon(tabIcons[4]);
    }


    @Override
    public void onClick(View view) {

    }
}
