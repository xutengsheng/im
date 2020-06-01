package com.xts.im.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.bean.TencentBean;
import com.xts.im.presenter.TencentPresenter;
import com.xts.im.view.TencentView;

import java.util.ArrayList;

import butterknife.BindView;

public class TencentActivity extends BaseActivity<TencentPresenter> implements TencentView {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private BaseQuickAdapter<TencentBean, BaseViewHolder> mAdapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected TencentPresenter initPresenter() {
        return new TencentPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<TencentBean> list = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<TencentBean, BaseViewHolder>(R.layout.item_tencent,list) {
            @Override
            protected void convert(BaseViewHolder helper, TencentBean item) {
                helper.setText(R.id.tv_title,item.getTitle());
             }
        };

        mAdapter.bindToRecyclerView(mRlv);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebActivity.startAct(TencentActivity.this,mAdapter.getData().get(position).url);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tencent;
    }

    @Override
    public void setData(final ArrayList<TencentBean> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addData(list);
            }
        });

    }
}
