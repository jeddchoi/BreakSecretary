package com.breaktime.breaksecretary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.breaktime.breaksecretary.Application.App;
import com.breaktime.breaksecretary.Observer;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Service.MyService;
import com.breaktime.breaksecretary.Subject;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.Util.Singleton;
import com.breaktime.breaksecretary.adapter.FragmentAdapter;
import com.breaktime.breaksecretary.fragment.MyStatusFragment;
import com.breaktime.breaksecretary.fragment.QuickReserveFragment;
import com.breaktime.breaksecretary.fragment.ReserveAndCheckFragment;
import com.breaktime.breaksecretary.fragment.SettingFragment;
import com.breaktime.breaksecretary.fragment.TimeLineFragment;
import com.breaktime.breaksecretary.model.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends BaseActivity implements Subject {
    private static final String TAG = MainActivity.class.getName();

    public FirebaseUtil mFirebaseUtil;
    public User mUser;
    private TabLayout mTabLayout;
    public ViewPager mViewPager;
    private RelativeLayout relative_main;

    private int[] tabIcons = {
            R.drawable.ic_flash_on_white_24dp,
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_timeline_white_24dp,
            R.drawable.ic_settings_white_24dp
    };

    public ArrayList<Observer> observers;
    private final int MESSAGE_SHOW_START_PAGE = 0x002;

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SHOW_START_PAGE:
                    initViewPager();
                    Window window = getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, null));


                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(200);
                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            relative_main.setVisibility(View.GONE);
                            mUser.user_login();
                            show_snackbar_msg("Successfully signed in : " + mUser.getEmailForSingleEvent(), true);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    relative_main.startAnimation(alphaAnimation);
                    break;

                default:
                    break;

            }
            return true;
        }
    });


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d("HHH", "called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);

        // This should be executed first of all. Otherwise, in fragments, there will be some errors.
        // Because constructing FirebaseUtil demands some time.
        mFirebaseUtil = new FirebaseUtil();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, FirstActivity.class));
            finish();
        }
        mUser = new User(mFirebaseUtil);


        setContentView(R.layout.activity_main);
        observers = new ArrayList<>();

        InitSetting();
        ((App)getApplicationContext()).ref(this);

        relative_main = findViewById(R.id.relative_main);
        ImageView img_page_start = findViewById(R.id.img_page_start);
        relative_main.setVisibility(View.VISIBLE);
        Glide.with(MainActivity.this).load(R.drawable.google_logo).into(img_page_start);


        // This should be called last if you want to earn some time before creating fragments.
        mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 2000);
    }

    public void InitSetting(){
        Singleton.getInstance().Init(mFirebaseUtil);
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

        switch (status) {
            case ONLINE:
            case SUBSCRIBING:
                mViewPager.setCurrentItem(0);
                break;

            case RESERVING:
            case RESERVING_OVER:
            case OCCUPYING:
            case OCCUPYING_OVER:
            case STEPPING_OUT:
            case STEPPING_OUT_OVER:
            case PAYING_PENALTY:
            case BEING_BLOCKED:
                mViewPager.setCurrentItem(2);
                break;

            default:
                Log.e(TAG, "Undefined User Status");

        }


        for(Observer ob : observers){
            ob.update(status);
        }


        // To service
    }


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        mHandler.removeCallbacksAndMessages(null);

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
        mViewPager = findViewById(R.id.view_pager_main);
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


    public void startService(int major , int minor){
        //mUser.get_user_ref().child("status").setValue(User.Status_user.RESERVING);
        mUser.user_reserve(1,1);
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra(ReservingActivity.R_MAJOR, major);
        intent.putExtra(ReservingActivity.R_MINOR, minor);
        ContextCompat.startForegroundService(this, intent);
    }

    public void stopService(){
        mUser.get_user_ref().child("status").setValue(User.Status_user.ONLINE);
        Intent intent = new Intent(this, MyService.class);
        this.stopService(intent);
    }


}
