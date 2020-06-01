package com.xts.im.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.presenter.ZhihuPresenter;
import com.xts.im.ui.adapter.VpZhihuAdapter;
import com.xts.im.view.ZhihuView;

import java.util.ArrayList;

import butterknife.BindView;

public class ZhihuFragment extends BaseFragment<ZhihuPresenter> implements ZhihuView {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.vp)
    ViewPager mVp;
    private ArrayList<String> mTitles;
    private ArrayList<BaseFragment> mFragments;

    public static ZhihuFragment newInstance() {
        ZhihuFragment fragment = new ZhihuFragment();

        return fragment;
    }

    @Override
    protected ZhihuPresenter initPresenter() {
        return new ZhihuPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    protected void initView(View inflate) {
        initFragments();
        initTitles();

        //fragment嵌套fragment,
        VpZhihuAdapter adapter = new VpZhihuAdapter(getChildFragmentManager(), mTitles, mFragments);
        mVp.setAdapter(adapter);

        //关联viewpager和tabLayout
        mTabLayout.setupWithViewPager(mVp);
    }

    private void initTitles() {
        mTitles = new ArrayList<>();
        mTitles.add(getResources().getString(R.string.daily_news));
        mTitles.add(getResources().getString(R.string.section));
        mTitles.add(getResources().getString(R.string.hot));
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(DailyNewsFragment.newInstance());
        mFragments.add(SectionFragment.newInstance());
        mFragments.add(HotFragment.newInstance());

    }
}
