package com.breaktime.breaksecretary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

// 아이콘 추가시 확장 : implements PagerSlidingTabStrip.IconTabProvider
public class MainFragmentPagerAdapter extends FragmentPagerAdapter  {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Tab11", "Tab2", "Tab3","Tab4","Tab5"};
    //private int tabIcons[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round};
    ArrayList<Fragment> items = new ArrayList<>();


    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    public void addItem(Fragment item){
        items.add(item);
    }

    @Override
    public Fragment getItem(int position) {
        //return PageFragment.newInstance(position + 1);
        // case 1.
        return items.get(position);
        // case 2.
//        if(position == 0){
//            return PageFragment.newInstance()
//        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

//    @Override
//    public int getPageIconResId(int position) {
//        return tabIcons[position];
//    }

}
