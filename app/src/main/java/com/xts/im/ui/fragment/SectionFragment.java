package com.xts.im.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.bean.SectionBean;
import com.xts.im.presenter.SectionPresenter;
import com.xts.im.view.SectionView;

import java.util.ArrayList;

import butterknife.BindView;

public class SectionFragment extends BaseFragment<SectionPresenter> implements SectionView {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private BaseQuickAdapter<SectionBean.DataBean, BaseViewHolder> mAdapter;

    public static SectionFragment newInstance(){
        SectionFragment fragment = new SectionFragment();

        return fragment;
    }

    @Override
    protected SectionPresenter initPresenter() {
        return new SectionPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_section;
    }

    @Override
    protected void initView(View inflate) {
        ArrayList<SectionBean.DataBean> list = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<SectionBean.DataBean, BaseViewHolder>(R.layout.item_section,list) {
            @Override
            protected void convert(BaseViewHolder helper, SectionBean.DataBean item) {
                ImageView iv = helper.getView(R.id.iv);
                Glide.with(mContext).load(item.getThumbnail()).into(iv);
                helper.setText(R.id.tv,item.getName());
            }
        };
        mRlv.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter.bindToRecyclerView(mRlv);

    }

    @Override
    public void setData(SectionBean sectionBean) {
        if (sectionBean.getData() != null && sectionBean.getData().size()>0){
            mAdapter.addData(sectionBean.getData());
        }
    }
}
