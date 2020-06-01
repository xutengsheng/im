package com.xts.im.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xts.im.base.BaseFragment;
import com.xts.im.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class VpItInfoAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<BaseFragment> mFragments;
    private final List<DataBean> mTabList;
    private final ArrayList<String> mTitles;

    public VpItInfoAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments, List<DataBean> tabList) {
        super(fm);
        mFragments = fragments;
        mTabList = tabList;

        mTitles = new ArrayList<>();
        for (int i = 0; i < mTabList.size(); i++) {
            DataBean dataBean = mTabList.get(i);
            if (dataBean.isSelect()){
                mTitles.add(dataBean.getName());
            }
        }
    }

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
