package com.breaktime.breaksecretary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.astuetz.PagerSlidingTabStrip;
import com.breaktime.breaksecretary.R;
import com.breaktime.breaksecretary.adapter.MainFragmentPagerAdapter;
import com.breaktime.breaksecretary.app.BreakScretApp;
import com.breaktime.breaksecretary.fragment.MyStatusFragment;
import com.breaktime.breaksecretary.fragment.QuickReserveFragment;
import com.breaktime.breaksecretary.fragment.ReserveAndCheckFragment;
import com.breaktime.breaksecretary.fragment.SettingFragment;
import com.breaktime.breaksecretary.fragment.TimeLineFragment;


public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        LoadFragment(adapter);
        viewPager.setAdapter(adapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }



    public void LoadFragment(MainFragmentPagerAdapter adapter){
        adapter.addItem(QuickReserveFragment.newInstance(1));
        adapter.addItem(ReserveAndCheckFragment.newInstance(2));
        adapter.addItem(MyStatusFragment.newInstance(3));
        adapter.addItem(SettingFragment.newInstance(4));
        adapter.addItem(TimeLineFragment.newInstance(5));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BreakScretApp.Log("onActivityResult Called");
        BreakScretApp.Log(String.valueOf(requestCode));

        if(resultCode == RESULT_OK) {
                BreakScretApp.Log("ok called");
                viewPager.setCurrentItem(2,true);
        }

    }
}
