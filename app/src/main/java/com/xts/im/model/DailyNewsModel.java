package com.xts.im.model;

import android.text.TextUtils;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.DailyNewsBean;
import com.xts.im.net.ZhihuService;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

import io.reactivex.Flowable;

public class DailyNewsModel extends BaseModel {
    /**
     * 如果日期是"",那么代表是要最新新闻,如果不为空,请求给定日期的新闻
     *
     * @param date 日期,格式:20200202
     * @param callBack
     */
    public void getData(String date, final ResultCallBack<DailyNewsBean> callBack) {
        ZhihuService apiserver = HttpUtil.getInstance().getZHihuApi();
        Flowable<DailyNewsBean> news;

        if (TextUtils.isEmpty(date)) {
            //如果日期是"",那么代表是要最新新闻
            news= apiserver.getLastNews();
        }else {
            //如果不为空,请求给定日期的新闻
            news =  apiserver.getOldNews(date);
        }

        addDisposable(
                news.compose(RxUtils.<DailyNewsBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<DailyNewsBean>() {
                            @Override
                            protected void onSuccess(DailyNewsBean dailyNewsBean) {
                                callBack.onSuccess(dailyNewsBean);
                            }
                        })
        );

    }
}
