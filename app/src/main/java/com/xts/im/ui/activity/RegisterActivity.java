package com.xts.im.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.base.Constants;
import com.xts.im.presenter.RegisterPresenter;
import com.xts.im.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView {
    public static int TYPE_NORMAL_REGISTER = 0;
    public static int TYPE_ACCESS_REGISTER = 1;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_psd)
    EditText mEtPsd;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    private int mType;
    private String mUid;
    private String mTypeId;

    @Override
    protected void initListener() {

    }

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mType = getIntent().getIntExtra(Constants.TYPE, TYPE_NORMAL_REGISTER);
        mUid = getIntent().getStringExtra(Constants.USERID);
        mTypeId = getIntent().getStringExtra(Constants.TYPE_ID);
        if (mType == TYPE_NORMAL_REGISTER){
            mBtnRegister.setText("注册");
        }else {
            mBtnRegister.setText("设置");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    private void register() {
        final String name = mEtName.getText().toString().trim();
        final String psd = mEtPsd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(psd)) {
            showToast("用户名或者密码不能为空");
            return;
        }
        if (mType == TYPE_NORMAL_REGISTER) {
            mPresenter.register(name, psd);
        }else {
            mPresenter.register(name,psd,mUid,mTypeId);
        }
    }

    @OnClick(R.id.btn_register)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    @Override
    public void toFinish() {
        finish();
    }

    @Override
    public void go2MainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
