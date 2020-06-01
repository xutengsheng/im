package com.xts.im.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.base.Constants;
import com.xts.im.bean.HotBean;
import com.xts.im.presenter.HotPresenter;
import com.xts.im.ui.activity.NewsDetailActivity;
import com.xts.im.view.HotView;

import java.util.ArrayList;

import butterknife.BindView;

public class HotFragment extends BaseFragment<HotPresenter> implements HotView {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private BaseQuickAdapter mAdapter;

    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();

        return fragment;
    }

    @Override
    protected HotPresenter initPresenter() {
        return new HotPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initView(View inflate) {

        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        final ArrayList<HotBean.RecentBean> list = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<HotBean.RecentBean, BaseViewHolder>(R.layout.item_zhihu_news,list) {
            @Override
            protected void convert(BaseViewHolder helper, HotBean.RecentBean item) {
                helper.setText(R.id.tv,item.getTitle());
                Glide.with(mContext).load(item.getThumbnail())
                        .into((ImageView) helper.getView(R.id.iv));
            }
        };
        mAdapter.bindToRecyclerView(mRlv);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                go2Detail(list.get(position));
            }
        });
    }

    private void go2Detail(HotBean.RecentBean bean) {
        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
        intent.putExtra(Constants.TITLE,bean.getTitle());
        intent.putExtra(Constants.PIC,bean.getThumbnail());
        intent.putExtra(Constants.ID,bean.getNews_id());
        startActivity(intent);
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }


    @Override
    public void setData(HotBean hotBean) {
        mAdapter.addData(hotBean.getRecent());
    }
}
