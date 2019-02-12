package com.breaktime.breaksecretary.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.Util.FirebaseUtil;
import com.breaktime.breaksecretary.adapter.FragmentAdapter;
import com.breaktime.breaksecretary.fragment.MyStatusFragment;
import com.breaktime.breaksecretary.fragment.QuickReserveFragment;
import com.breaktime.breaksecretary.fragment.ReserveAndCheckFragment;
import com.breaktime.breaksecretary.fragment.SettingFragment;
import com.breaktime.breaksecretary.fragment.TimeLineFragment;
import com.breaktime.breaksecretary.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public FirebaseUtil myFireBase = new FirebaseUtil();
    public User mUser;

    private TabLayout mTabLayout;

    private int[] tabIcons = {
            R.drawable.ic_flash_on_white_24dp,
            R.drawable.ic_timer_white_24dp,
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_timeline_white_24dp,
            R.drawable.ic_settings_white_24dp
    };

    private static boolean isShowPageStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String TAG = "MainActivity";
        Log.d(TAG, "onCreate MainActivity");


        mUser = new User(myFireBase.getAuth().getCurrentUser(), myFireBase.getRootRef());
        mUser.userLogin();

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, null));
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);

        if (isShowPageStart) {
            if (sharedPreferences.getBoolean("isFirst", true)) {
                succeedToLogin();
            }
            isShowPageStart = false;
        }

        initViewPager();
    }

    public void succeedToLogin() {
        Snackbar.make(findViewById(R.id.container), "Sign in with " + mUser.getEmailForSingleEvent(), Snackbar.LENGTH_SHORT).show();
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
        Objects.requireNonNull(mTabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(mTabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(mTabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(mTabLayout.getTabAt(3)).setIcon(tabIcons[3]);
        Objects.requireNonNull(mTabLayout.getTabAt(4)).setIcon(tabIcons[4]);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
