package com.xts.im.ui.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.xts.im.R;
import com.xts.im.adapter.BaseRlvAdapter;
import com.xts.im.base.BaseFragment;
import com.xts.im.base.Constants;
import com.xts.im.bean.DailyNewsBean;
import com.xts.im.presenter.DailyNewsPresenter;
import com.xts.im.ui.activity.CalendarActivity;
import com.xts.im.ui.activity.NewsDetailActivity;
import com.xts.im.ui.adapter.RlvDailyNewsAdapter;
import com.xts.im.util.LogUtil;
import com.xts.im.util.TimeUtil;
import com.xts.im.view.DailyNewsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

//Android 控件学ios,material design 质感设计,新的控件
//FloatingActionButton
public class DailyNewsFragment extends BaseFragment<DailyNewsPresenter> implements DailyNewsView {
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private RlvDailyNewsAdapter mAdapter;


    public static DailyNewsFragment newInstance(){
        DailyNewsFragment fragment = new DailyNewsFragment();

        return fragment;
    }

    @Override
    protected DailyNewsPresenter initPresenter() {
        return new DailyNewsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily_news;
    }

    @Override
    protected void initView(View inflate) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<DailyNewsBean.StoriesBean> newsList = new ArrayList<>();
        ArrayList<DailyNewsBean.TopStoriesBean> bannerList = new ArrayList<>();
        mAdapter = new RlvDailyNewsAdapter(getContext(),newsList,bannerList);
        mRlv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListenr(new BaseRlvAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                go2Detail(mAdapter.mNewsList.get(position));
            }
        });

    }

    private void go2Detail(DailyNewsBean.StoriesBean bean) {
        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
        intent.putExtra(Constants.TITLE,bean.getTitle());
        intent.putExtra(Constants.PIC,bean.getImages().get(0));
        intent.putExtra(Constants.ID,bean.getId());
        startActivity(intent);
    }

    @Override
    protected void initData() {
        mPresenter.getData("");
    }

    @Override
    public void setData(DailyNewsBean bean) {
        mAdapter.setData(bean);
    }


    @OnClick({R.id.fab})
    public void click(View view){
        switch (view.getId()) {
            case R.id.fab:
                go2Calendar();
                break;
        }
    }

    private void go2Calendar() {
        //startActivityForResult();
        //EnventBus
        startActivity(new Intent(getContext(), CalendarActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe()
    public void receiveDate(CalendarDay day){
        LogUtil.print("年:"+day.getYear()+",月:"+day.getMonth()
                +",日:"+day.getDay());
        //m层中如果是需要最新新闻,传"",旧的新闻,传日期
        String date = "";
        /*if (day是今日的日期){
            date = "";
        }else {
            date = "day对应的字符串日期";
        }*/

        //获取当前日期
        Calendar current = Calendar.getInstance();
        Calendar select = day.getCalendar();

        String strCurrent = TimeUtil.parseTime(current);
        String strSelect = TimeUtil.parseTime(select);
        if (strCurrent.equals(strSelect)){
            date = "";
        }else {
            date = strSelect;
        }
        LogUtil.print("date:"+date);
        //旧新闻给定0302,返回的是0301的新闻,后端接口有问题,如果是旧日期我们需要手动给添加一天
        if (!TextUtils.isEmpty(date)){
            select.add(Calendar.DAY_OF_MONTH,1);
            //添加一天后重新复制
            date = TimeUtil.parseTime(select);
        }
        mPresenter.getData(date);
    }
}
