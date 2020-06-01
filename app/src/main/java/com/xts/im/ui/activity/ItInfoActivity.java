package com.xts.im.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.base.BaseApp;
import com.xts.im.base.BaseFragment;
import com.xts.im.base.Constants;
import com.xts.im.bean.ChapterTabBean;
import com.xts.im.bean.DataBean;
import com.xts.im.db.DataBeanDao;
import com.xts.im.presenter.ItInfoPresenter;
import com.xts.im.ui.adapter.VpItInfoAdapter;
import com.xts.im.ui.fragment.ItInfoItemFragment;
import com.xts.im.util.LogUtil;
import com.xts.im.view.ItInfoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ItInfoActivity extends BaseActivity<ItInfoPresenter> implements ItInfoView {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    private ArrayList<DataBean> mTabList;
    private DataBeanDao mDataBeanDao;

    @Override
    protected void initListener() {

    }

    @Override
    protected ItInfoPresenter initPresenter() {
        return new ItInfoPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getChapterTab();
    }

    @Override
    protected void initView() {
        mDataBeanDao = BaseApp.getInstance().getDaoSession().getDataBeanDao();
        mToolBar.setTitle(R.string.it_info);
        setSupportActionBar(mToolBar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_it_info;
    }

    @Override
    public void setTab(ChapterTabBean chapterTabBean) {
        ArrayList netList = chapterTabBean.getData();
        if (netList != null && netList.size() > 0) {
            dealList(netList);
            dealUi();
        }
    }

    private void dealList(ArrayList<DataBean> tabList) {
        ArrayList<DataBean> list = (ArrayList<DataBean>) mDataBeanDao.queryBuilder().list();
        LogUtil.print(list.toString());
        Collections.sort(list, new Comparator<DataBean>() {
            @Override
            public int compare(DataBean o1, DataBean o2) {
                return o1.getIndex()-o2.getIndex();
            }
        });
        if (list != null && list.size() > 0) {
            //比对本地和网络数据,选中的显示+新增的tab也显示
            for (int i = 0; i < list.size(); i++) {
                int id = list.get(i).getId();
                boolean isHave = false;
                for (int j = 0; j < tabList.size(); j++) {
                    if (id == tabList.get(j).getId()) {
                        isHave = true;
                        tabList.remove(j);
                        break;
                    }
                }
            }
            if (tabList.size() > 0) {
                list.addAll(tabList);
            }
            mTabList = list;
        } else {
            mTabList = tabList;
            for (int i = 0; i < mTabList.size(); i++) {
                mTabList.get(i).setIndex(i);
            }
            mDataBeanDao.insertOrReplaceInTx(mTabList);
        }

    }

    private void dealUi() {

        ArrayList<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTabList.size(); i++) {
            DataBean dataBean = mTabList.get(i);
            if (dataBean.isSelect()) {
                fragments.add(ItInfoItemFragment.newInstance(mTabList.get(i).getId()));
            }
        }

        VpItInfoAdapter adapter = new VpItInfoAdapter(getSupportFragmentManager(),
                fragments, mTabList);
        mVp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mVp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(ItInfoActivity.this, SearchActivity.class));
                break;
            case R.id.guide:
                startActivity(new Intent(ItInfoActivity.this, GuideActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.iv_arrow})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow:
                go2InterestSelect();
                break;
        }
    }

    private void go2InterestSelect() {
        if (mTabList != null && mTabList.size() > 0) {
            Intent intent = new Intent(this, SpecialShowActivity.class);
            intent.putExtra(Constants.DATA, mTabList);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            mTabList = (ArrayList<DataBean>) data.getSerializableExtra(Constants.DATA);
            dealUi();
        }
    }
}
