package com.xts.im.model;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xts.im.base.BaseModel;
import com.xts.im.bean.LoginBean;
import com.xts.im.bean.RegisterBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.LoginService;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class RegisterModel extends BaseModel {
    public void login(final String name, final String psd, final ResultCallBack<LoginBean> callBack) {
        //1.登录环信
        //2.拿环信的账号去登录我们自己的服务器,成功,进主页面,失败提示用户
        EMClient.getInstance().login(name, psd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                addDisposable(
                        HttpUtil.getInstance()
                                .getLoginSer()
                                .login(name,psd)
                                .compose(RxUtils.<LoginBean>rxSchedulerHelper())
                                .subscribeWith(new ResultSubScriber<LoginBean>() {
                                    @Override
                                    protected void onSuccess(LoginBean bean) {
                                        if (bean.getCode().equals(LoginService.LOGIN_SUCCESS_CODE)) {
                                            callBack.onSuccess(bean);
                                        }else {
                                            callBack.onFail(bean.getMessage());
                                        }
                                    }
                                })
                );

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                callBack.onFail("登录失败");
            }
        });
    }

    public void loginAccess(String uid, String typeId, final ResultCallBack<LoginBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getLoginSer()
                        .loginAccess(uid,typeId)
                        .compose(RxUtils.<LoginBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<LoginBean>() {
                            @Override
                            protected void onSuccess(LoginBean bean) {
                                if (bean.getCode().equals(LoginService.LOGIN_SUCCESS_CODE)) {
                                    callBack.onSuccess(bean);
                                }else {
                                    callBack.onFail("注册失败");
                                }
                            }
                        })
        );
    }

    //账号密码注册,只注册,不登录
    public void register(String name, String psd, final ResultCallBack<RegisterBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getLoginSer()
                        .register(name,psd,"","")
                        .compose(RxUtils.<RegisterBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<RegisterBean>() {
                            @Override
                            protected void onSuccess(RegisterBean bean) {

                                callBack.onSuccess(bean);
                            }
                        })
        );
    }
    //三方注册,登录
    public void register(String name, String psd,final String uid,final String typeId ,final ResultCallBack<LoginBean> callBack) {

        addDisposable(
                HttpUtil.getInstance()
                        .getLoginSer()
                        .register(name,psd,uid,typeId)
                        .compose(RxUtils.<RegisterBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<RegisterBean>() {
                            @Override
                            protected void onSuccess(RegisterBean bean) {
                                String code = bean.code;
                                if ("注册成功".equals(code)) {
                                    //注册成功,去登录
                                    loginAccess(uid,typeId,callBack);
                                }else {
                                    callBack.onFail(code.toString());
                                }
                            }
                        })
        );
    }
}
