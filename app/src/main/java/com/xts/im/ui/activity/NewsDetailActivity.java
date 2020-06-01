package com.xts.im.ui.activity;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.base.Constants;
import com.xts.im.bean.NewsDetailBean;
import com.xts.im.presenter.NewsDetailPresenter;
import com.xts.im.view.NewsDetailView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter> implements NewsDetailView {

    private static final String TAG = "NewsDetail";
    @BindView(R.id.html_text)
    HtmlTextView mTv;
    @BindView(R.id.appBar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.ctl)
    CollapsingToolbarLayout mCdl;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    private String mTitle;
    private String mPic;
    private int mId;

    @Override
    protected NewsDetailPresenter initPresenter() {
        return new NewsDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mTitle = getIntent().getStringExtra(Constants.TITLE);
        mPic = getIntent().getStringExtra(Constants.PIC);
        mId = getIntent().getIntExtra(Constants.ID,0);


        Glide.with(this).load(mPic).into(mIv);

        //设置标题
        mCdl.setTitle(mTitle);
        //扩张时候的title颜色
        mCdl.setExpandedTitleColor(getResources().getColor(R.color.colorAccent));
        //收缩后显示title的颜色
        mCdl.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initData() {
        mPresenter.getDetail(mId);
    }

    @Override
    public void setData(NewsDetailBean bean) {
// loads html from string and displays cat_pic.png from the app's drawable folder
        mTv.setHtml(bean.getBody(),
                new HtmlHttpImageGetter(mTv));
    }

    @Override
    protected void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset 垂直方向的偏移量 1dp = 3px 200dp = 600px
                //0 到 -600px
                Log.d(TAG, "onOffsetChanged: "+verticalOffset);
            }
        });
    }
}
