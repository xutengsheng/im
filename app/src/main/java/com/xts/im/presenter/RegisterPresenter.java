package com.xts.im.presenter;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.xts.im.base.BasePresenter;
import com.xts.im.base.Constants;
import com.xts.im.bean.LoginBean;
import com.xts.im.bean.RegisterBean;
import com.xts.im.model.RegisterModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ThreadManager;
import com.xts.im.util.SpUtil;
import com.xts.im.view.AboutView;
import com.xts.im.view.RegisterView;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    private RegisterModel mRegisterModel;

    @Override
    protected void initModel() {
        mRegisterModel = new RegisterModel();
        addModel(mRegisterModel);
    }

    //账号密码注册,无需直接登录
    public void register(final String name, final String psd) {
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().createAccount(name, psd);//同步方法
                            //环信注册成功,需要去自己的服务器注册
                            mRegisterModel.register(name, psd, new ResultCallBack<RegisterBean>() {
                                @Override
                                public void onSuccess(RegisterBean registerBean) {
                                    String code = registerBean.code;
                                    if ("注册成功".equals(code)) {
                                        mView.toFinish();
                                    }
                                    mView.showToast(code);
                                }

                                @Override
                                public void onFail(String msg) {
                                    mView.showToast(msg);
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            mView.showToast("注册失败");
                        }

                    }
                });
    }

    //三方注册,需要直接登录
    public void register(final String name, final String psd, final String uid, final String typeId) {
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().createAccount(name, psd);//同步方法
                            //环信注册成功,需要去自己的服务器注册
                            //注册之后还需要直接登录
                            mRegisterModel.register(name, psd, uid, typeId, new ResultCallBack<LoginBean>() {
                                @Override
                                public void onSuccess(LoginBean registerBean) {
                                    //登录成功
                                    mView.showToast("登录成功");
                                    saveUserData(registerBean);
                                    mView.go2MainActivity();
                                }

                                @Override
                                public void onFail(String msg) {
                                    mView.showToast(msg);
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            mView.showToast(e.toString());
                        }

                    }
                });
    }

    //保存用户信息
    private void saveUserData(LoginBean bean) {
        SpUtil.setParam(Constants.TOKEN, bean.getToken());
        SpUtil.setParam(Constants.USERNAME, bean.getUser().getName());
        SpUtil.setParam(Constants.PHOTO, bean.getUser().getHeaderpic());
        SpUtil.setParam(Constants.USERID, bean.getUser().getUserid());
    }

}
