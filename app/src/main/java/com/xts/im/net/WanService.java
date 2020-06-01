package com.xts.im.net;

import com.xts.im.bean.ChapterTabBean;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.bean.NaviBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WanService {
    String sWanUrl = "https://wanandroid.com/";

    //公众号tab
    @GET("wxarticle/chapters/json")
    Flowable<ChapterTabBean> getChapterTab();

    //公众号列表
    //id,公众号tab中的公众号id
    //page,页码,从1开始
    @GET("wxarticle/list/{id}/{page}/json")
    Flowable<ItInfoItemBean> getItInfoList(@Path("id") int id,
                                           @Path("page") int page);

    //公众号列表
    //page,页码,从1开始
    //k,关键字
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Flowable<ItInfoItemBean> search(@Path("page") int page,
                                           @Field("k") String keyword);
    //导航
    @GET("navi/json")
    Flowable<NaviBean> getNavi();
}
