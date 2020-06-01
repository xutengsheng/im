package com.xts.im.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xts.im.base.BaseFragment;

import java.util.ArrayList;

public class VpZhihuAdapter extends FragmentPagerAdapter {

    private final ArrayList<String> mTitles;
    private final ArrayList<BaseFragment> mFragments;

    public VpZhihuAdapter(@NonNull FragmentManager fm, ArrayList<String> titles,
                          ArrayList<BaseFragment> fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
