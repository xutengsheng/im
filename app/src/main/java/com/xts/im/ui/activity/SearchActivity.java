package com.xts.im.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.presenter.SearchPresenter;
import com.xts.im.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchView {

    @BindView(R.id.msv)
    MaterialSearchView mMsv;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    private BaseQuickAdapter<ItInfoItemBean.DataBean.DatasBean, BaseViewHolder> mAdapter;
    private int mPage = 0;

    @Override
    protected void initListener() {
        mMsv.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)){
                    showToast("搜索内容为空");
                    return false;
                }
                mPresenter.search(query,mPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected SearchPresenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMsv.showSearch(false);
        mToolbar.setTitle("搜索");
        setSupportActionBar(mToolbar);


        mRlv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ItInfoItemBean.DataBean.DatasBean> list = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<ItInfoItemBean.DataBean.DatasBean, BaseViewHolder>(R.layout.item_it_info,list) {
            @Override
            protected void convert(BaseViewHolder helper, ItInfoItemBean.DataBean.DatasBean item) {
                helper.setText(R.id.tv_author,item.getAuthor());
                helper.setText(R.id.tv_time,item.getNiceDate());
                helper.setText(R.id.tv_title,item.getTitle());
                helper.setText(R.id.tv_wechat,item.getSuperChapterName()+"."+item.getChapterName());
            }
        };

        mAdapter.bindToRecyclerView(mRlv);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebActivity.startAct(SearchActivity.this,mAdapter.getData().get(position).getLink());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void setData(ItInfoItemBean itInfoItemBean) {
        List<ItInfoItemBean.DataBean.DatasBean> datas = itInfoItemBean.getData().getDatas();
        if (datas != null && datas.size()>0){
            mAdapter.addData(datas);
        }else {
            showToast("未搜索到内容");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mMsv.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (mMsv.isSearchOpen()) {
            mMsv.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
