package com.xts.im.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.base.BaseApp;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.bean.NaviBean;
import com.xts.im.presenter.GuidePresenter;
import com.xts.im.view.GuideView;
import com.xts.im.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qdx.stickyheaderdecoration.NormalDecoration;

public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideView {

    int mRed = BaseApp.getRes().getColor(R.color.red);
    int mGreen = BaseApp.getRes().getColor(R.color.purple);

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    private BaseQuickAdapter<NaviBean.DataBean, BaseViewHolder> mAdapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected GuidePresenter initPresenter() {
        return new GuidePresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        final ArrayList<NaviBean.DataBean> list = new ArrayList<>();
        NormalDecoration decoration = new NormalDecoration() {
            @Override
            public String getHeaderName(int i) {
                return list.get(i).getName();
            }
        };
        mRlv.addItemDecoration(decoration);
        mRlv.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BaseQuickAdapter<NaviBean.DataBean, BaseViewHolder>(R.layout.item_guide,list) {
            @Override
            protected void convert(BaseViewHolder helper, NaviBean.DataBean item) {
                FlowLayout parent = helper.getView(R.id.fl);
                List<NaviBean.DataBean.ArticlesBean> articles = item.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    //获取视图,视图可以自定义,可以添加自己想要的效果
                    TextView label = (TextView) View.inflate(mContext, R.layout.item_label, null);
                    //获取数据
                    NaviBean.DataBean.ArticlesBean data = articles.get(i);
                    //绑定数据
                    label.setText(data.getTitle());
                    if (i % 2== 0){
                        label.setTextColor(mRed);
                    }else {
                        label.setTextColor(mGreen);
                    }
                    //加到容器中,parent是FlowLayout
                    parent.addView(label);
                }
            }
        };

        mAdapter.bindToRecyclerView(mRlv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void setData(NaviBean naviBean) {
        List<NaviBean.DataBean> data = naviBean.getData();
        mAdapter.addData(data);
    }
}
