package com.xts.im.net;



import com.xts.im.bean.DailyNewsBean;
import com.xts.im.bean.HotBean;
import com.xts.im.bean.NewsDetailBean;
import com.xts.im.bean.SectionBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ZhihuService {

    String sZhiHuUrl = "http://news-at.zhihu.com/api/4/";

    @GET("news/latest")
    Flowable<DailyNewsBean> getLastNews();

    @GET("news/before/{date}")
    Flowable<DailyNewsBean> getOldNews(@Path("date") String date);

    @GET("news/{news_id}")
    Flowable<NewsDetailBean> getNewsDetail(@Path("news_id") String news_id);

    @GET("sections")
    Flowable<SectionBean> getSection();

    @GET("news/hot")
    Flowable<HotBean> getHot();


}
