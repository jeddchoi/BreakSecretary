package com.breaktime.breaksecretary.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.adapter.FragmentAdapter;
import com.breaktime.breaksecretary.fragment.MyStatusFragment;
import com.breaktime.breaksecretary.fragment.QuickReserveFragment;
import com.breaktime.breaksecretary.fragment.ReserveAndCheckFragment;
import com.breaktime.breaksecretary.fragment.SettingFragment;
import com.breaktime.breaksecretary.fragment.TimeLineFragment;
import com.breaktime.breaksecretary.model.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public FirebaseUtil myFireBase = new FirebaseUtil();
    public User mUser;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RelativeLayout relative_main;
    private ImageView img_page_start;

    private int[] tabIcons = {
            R.drawable.ic_flash_on_white_24dp,
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_timeline_white_24dp,
            R.drawable.ic_settings_white_24dp
    };

    private static boolean isShowPageStart = true;
    private final int MESSAGE_SHOW_START_PAGE = 0x002;
    private final String TAG = "MainActivity";

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SHOW_START_PAGE:
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(300);
                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            relative_main.setVisibility(View.GONE);
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate MainActivity");

        mUser = new User(myFireBase.getCurrenUser(), myFireBase.getRootRef());
        mUser.addToLogRef();
        succeedToLogin();

        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        setContentView(R.layout.activity_main);

        initView();
        initViewPager();

        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);

        if (isShowPageStart) {
            relative_main.setVisibility(View.VISIBLE);
            Glide.with(MainActivity.this).load(R.drawable.google_logo).into(img_page_start);
            if (sharedPreferences.getBoolean("isFirst", true)) {
                mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 2000);
                Log.d(TAG, "isFirst == true, MESSAGE_SHOW_START_PAGE");
            } else {
                mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_START_PAGE, 1000);
                Log.d(TAG, "isFirst == false, MESSAGE_SHOW_START_PAGE");
            }
            isShowPageStart = false;
        }
    }

    public void succeedToLogin() {
        Snackbar.make(findViewById(R.id.container), "Sign in with " + mUser.getEmail(), Snackbar.LENGTH_SHORT).show();
    }

    private void initView() {
        relative_main = findViewById(R.id.relative_main);
        img_page_start = findViewById(R.id.img_page_start);
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
        fragments.add(new QuickReserveFragment());
        fragments.add(new ReserveAndCheckFragment());
        fragments.add(new MyStatusFragment());
        fragments.add(new TimeLineFragment());
        fragments.add(new SettingFragment());

        mViewPager.setOffscreenPageLimit(4);


        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
        mTabLayout.getTabAt(3).setIcon(tabIcons[3]);
        mTabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
