package com.example.kelvin.instagramclone.Home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelvin on 2/23/18.
 */

/**
 * class that stores fragment for tabs
 */

public class SectionPagerAdpter extends FragmentPagerAdapter {
    private static final String TAG = "SectionPagerAdpter";
    private final List<Fragment> mFlagmentlist = new ArrayList<>();

    public SectionPagerAdpter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFlagmentlist.get(position);
    }

    @Override
    public int getCount() {
        return mFlagmentlist.size();
    }
    //method to add fragment to other
    public void addFragment(Fragment fragment){
        mFlagmentlist.add(fragment);
    }
}
