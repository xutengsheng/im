package com.xts.im.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hyphenate.chat.EMClient;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.base.Constants;
import com.xts.im.net.LoginService;
import com.xts.im.presenter.LoginPresenter;
import com.xts.im.view.LoginView;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {



    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_psd)
    EditText mEtPsd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    @BindView(R.id.iv_qq)
    ImageView mIvQq;
    @BindView(R.id.iv_wechat)
    ImageView mIvWechat;
    @BindView(R.id.iv_sina)
    ImageView mIvSina;

    @Override
    protected void initListener() {

    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //判断是否自动登陆了
        if (EMClient.getInstance().isLoggedInBefore()) {
            go2MainActivity();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void go2Register(int type, String uid, String typeid) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(Constants.TYPE,type);
        intent.putExtra(Constants.USERID,uid);
        intent.putExtra(Constants.TYPE_ID,typeid);
        startActivity(intent);
    }

    private void login() {
        final String name = mEtName.getText().toString().trim();
        final String psd = mEtPsd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)) {
            showToast("用户名或者密码不能为空");
            return;
        }

        mPresenter.login(name, psd);
    }

    @Override
    public void go2MainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.iv_qq, R.id.iv_wechat, R.id.iv_sina})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                go2Register(RegisterActivity.TYPE_NORMAL_REGISTER,"","");
                break;
            case R.id.iv_qq:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.iv_wechat:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_sina:
                login(SHARE_MEDIA.SINA);
                break;
        }
    }

    private void login(SHARE_MEDIA type) {
        UMShareAPI umShareAPI = UMShareAPI.get(this);
        umShareAPI.getPlatformInfo(this, type, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            log(data);
            String typeId = "";
            if (platform == SHARE_MEDIA.QQ){
                typeId = LoginService.TYPE_QQ;
            }else if (platform == SHARE_MEDIA.SINA){
                typeId = LoginService.TYPE_SINA;
            } else if (platform == SHARE_MEDIA.WEIXIN) {
                typeId = LoginService.TYPE_WECHAT;
            }
            if (data != null) {
                //mPresenter.loginAccess(data.get("uid")+(System.currentTimeMillis()%1000),typeId);
                mPresenter.loginAccess(data.get("uid"),typeId);
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("失败");
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("取消");
        }
    };

    private void log(Map<String, String> data) {
        if (data != null) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Log.d("tag", key + ":" + value);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).deleteOauth(this,SHARE_MEDIA.QQ,authListener);
    }
}
