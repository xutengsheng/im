package com.xts.im.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.base.Constants;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.presenter.ItInfoItemPresenter;
import com.xts.im.ui.activity.WebActivity;
import com.xts.im.view.ItInfoItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ItInfoItemFragment extends BaseFragment<ItInfoItemPresenter>
        implements ItInfoItemView {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private int mId;
    private int mPage;
    private BaseQuickAdapter<ItInfoItemBean.DataBean.DatasBean, BaseViewHolder> mAdapter;

    public static ItInfoItemFragment newInstance(int id) {
        ItInfoItemFragment fragment = new ItInfoItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected ItInfoItemPresenter initPresenter() {
        return new ItInfoItemPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getChapterList(mId, mPage);
    }

    @Override
    protected void initView(View view) {
        Bundle arguments = getArguments();
        mId = arguments.getInt(Constants.ID);

        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
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
                WebActivity.startAct(getContext(),mAdapter.getData().get(position).getLink());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_it_info_item;
    }

    @Override
    public void setChapterList(ItInfoItemBean itInfoItemBean) {
        if (itInfoItemBean.getData() != null && itInfoItemBean.getData().getDatas() != null
                && itInfoItemBean.getData().getDatas().size() > 0) {
            List<ItInfoItemBean.DataBean.DatasBean> datas = itInfoItemBean.getData().getDatas();
            mAdapter.addData(datas);
        }
    }
}
