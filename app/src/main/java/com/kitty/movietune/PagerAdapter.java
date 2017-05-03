package com.kitty.movietune;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohita on 4/29/2017.
 */
class PagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PagerAdapter(FragmentManager manager) {
        super(manager);
    }


    @Override
    public Fragment getItem(int position){
        Fragment fragment=null;

        Bundle bundle = new Bundle();

        switch (position){
            case 0:
                fragment=new MovieFragment();
                bundle.putString("tab", "New Release");
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment=new MovieFragment();
                bundle.putString("tab", "Top Rated");
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment=new MovieFragment();
                bundle.putString("tab", "Upcoming");
                fragment.setArguments(bundle);
                break;
            default:
                fragment=null;
                break;
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
