package com.xts.im.model;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xts.im.base.BaseModel;
import com.xts.im.bean.HotBean;
import com.xts.im.bean.LoginBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.LoginService;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class LoginModel extends BaseModel {
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
                            protected void onSuccess(final LoginBean bean) {
                                //todo 自己服务器登录成功,还需要登录环信,接收消息
                                if (bean.getCode().equals(LoginService.LOGIN_SUCCESS_CODE)) {
                                    EMClient.getInstance().login(bean.getUser().getUserid(),
                                            bean.getUser().getPassword(), new EMCallBack() {//回调
                                        @Override
                                        public void onSuccess() {
                                            EMClient.getInstance().groupManager().loadAllGroups();
                                            EMClient.getInstance().chatManager().loadAllConversations();
                                            callBack.onSuccess(bean);
                                        }

                                        @Override
                                        public void onProgress(int progress, String status) {

                                        }

                                        @Override
                                        public void onError(int code, String message) {
                                            callBack.onFail("登录失败");
                                        }
                                    });

                                    callBack.onSuccess(bean);
                                }else if (bean.getCode().equals("0")){
                                    //三方的uid没有注册过,需要注册,但是不能叫注册,应该叫完善个人信息
                                    callBack.onFail(bean.getCode());
                                }else {
                                    callBack.onFail("登录失败");
                                }
                            }
                        })
        );
    }
}
