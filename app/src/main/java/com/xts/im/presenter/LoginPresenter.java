package com.xts.im.presenter;

import com.xts.im.base.BasePresenter;
import com.xts.im.base.Constants;
import com.xts.im.bean.LoginBean;
import com.xts.im.model.LoginModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.ui.activity.RegisterActivity;
import com.xts.im.util.SpUtil;
import com.xts.im.view.LoginView;

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginModel = new LoginModel();
        addModel(mLoginModel);
    }

    public void login(String name, String psd) {

        mLoginModel.login(name,psd, new ResultCallBack<LoginBean>() {
            @Override
            public void onSuccess(LoginBean bean) {
                mView.showToast("登录成功");
                saveUserData(bean);
                mView.go2MainActivity();

            }

            @Override
            public void onFail(String msg) {
                mView.showToast(msg);
            }
        });

    }

    //保存用户信息
    private void saveUserData(LoginBean bean) {
        SpUtil.setParam(Constants.TOKEN,bean.getToken());
        SpUtil.setParam(Constants.USERNAME,bean.getUser().getName());
        SpUtil.setParam(Constants.PHOTO,bean.getUser().getHeaderpic());
        SpUtil.setParam(Constants.USERID,bean.getUser().getUserid());
    }

    public void loginAccess(final String uid, final String typeId) {
        mLoginModel.loginAccess(uid,typeId,new ResultCallBack<LoginBean>(){
            @Override
            public void onSuccess(LoginBean bean) {
                mView.showToast("登录成功");
                saveUserData(bean);
                mView.go2MainActivity();
            }

            @Override
            public void onFail(String msg) {
                if (msg.equals("0")){
                    mView.showToast("授权成功,请设置用户名密码");
                    mView.go2Register(RegisterActivity.TYPE_ACCESS_REGISTER, uid, typeId);
                }else {
                    mView.showToast(msg);
                }
            }
        });
    }
}
