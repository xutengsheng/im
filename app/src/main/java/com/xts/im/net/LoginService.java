package com.xts.im.net;

import com.xts.im.bean.LoginBean;
import com.xts.im.bean.RegisterBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    String sUrl = "http://47.110.151.50/p6/";
    String TYPE_QQ = "1";
    String TYPE_WECHAT = "2";
    String TYPE_SINA = "3";
    String LOGIN_SUCCESS_CODE = "200";//登录成功code


    /**
     * 账号密码登录
     * @param userid
     * @param psd
     * @return
     */
    @POST("login.do")
    @FormUrlEncoded
    Flowable<LoginBean> login(@Field("userid") String userid,
                              @Field("password") String psd);

    /**
     * 三方登录
     * @param accesstoken
     * @param typeid
     * @return
     */
    @POST("loginAccessToken.do")
    @FormUrlEncoded
    Flowable<LoginBean> loginAccess(@Field("accesstoken") String accesstoken,
                              @Field("typeid") String typeid);

    /**
     * 注册,
     * @param userid
     * @param psd
     * @param accessToken 三方平台唯一标识,可选参数,三方注册使用
     * @param typeid ,可选参数,三方注册使用,三方平台类型,1是qq,2是微信,3微博
     * @return
     */
    @POST("register.do")
    @FormUrlEncoded
    Flowable<RegisterBean> register(@Field("userid") String userid,
                                    @Field("password") String psd,
                                    @Field("accessToken") String accessToken,
                                    @Field("typeid") String typeid);
}
