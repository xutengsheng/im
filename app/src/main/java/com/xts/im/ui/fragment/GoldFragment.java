package com.xts.im.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.base.Constants;
import com.xts.im.bean.TabBean;
import com.xts.im.presenter.GoldPresenter;
import com.xts.im.ui.activity.SpecialShowActivity;
import com.xts.im.ui.adapter.VpGoldAdapter;
import com.xts.im.view.GoldView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GoldFragment extends BaseFragment<GoldPresenter> implements GoldView {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.vp)
    ViewPager mVp;
    private ArrayList<TabBean> mTitles;
    private ArrayList<BaseFragment> mFragments;

    public static GoldFragment newInstance() {
        GoldFragment fragment = new GoldFragment();

        return fragment;
    }

    @Override
    protected GoldPresenter initPresenter() {
        return new GoldPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gold;
    }

    @Override
    protected void initView(View inflate) {
        mFragments = new ArrayList<>();
        initTitles();
        initFragments();

        setVp();
    }

    private void setVp() {
        VpGoldAdapter adapter = new VpGoldAdapter(getChildFragmentManager(), mTitles, mFragments);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
    }

    private void initFragments() {
        //创建几个fragment有mtitles里面的选中字段决定
        mFragments.clear();
        for (int i = 0; i < mTitles.size(); i++) {
            TabBean tabBean = mTitles.get(i);
            //如果是选中状态才需要创建对应的Fragment
            if (tabBean.isSelect){
                mFragments.add(AboutFragment.newInstance(tabBean.title));
            }
        }
    }

    private void initTitles() {
        //title 不能仅仅是一个string 因为,对应的fragment是否展示需要根据特别展示页面的选中状态来决定
        //所以,标题里面需要额外加一个是否选中的字段
        mTitles = new ArrayList<>();
        mTitles.add(new TabBean("设计",true));
        mTitles.add(new TabBean("阅读",true));
        mTitles.add(new TabBean("工具资源",true));
        mTitles.add(new TabBean("Android",true));
        mTitles.add(new TabBean("产品",true));
        mTitles.add(new TabBean("iOS",true));
        mTitles.add(new TabBean("前端",true));
        mTitles.add(new TabBean("后端",true));
    }

    @OnClick(R.id.iv)
    public void click(View v){
        go2SpecialShow();
    }

    private void go2SpecialShow() {
        Intent intent = new Intent(getContext(), SpecialShowActivity.class);
        intent.putExtra(Constants.DATA,mTitles);
        startActivityForResult(intent,100);
        //如果使用了下面这个方法,fragment中的onActivityResult()不会响应,activity中的会响应
        //getActivity().startActivityForResult();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null){
            mTitles = (ArrayList<TabBean>) data.getSerializableExtra(Constants.DATA);

            initFragments();

            setVp();
        }
    }
}
