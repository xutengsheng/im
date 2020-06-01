package com.xts.im.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;


import com.xts.im.util.ToastUtil;

import butterknife.ButterKnife;

//模板方法模式:父类定义代码的执行流程,把一些无法决定的东西放到子类完成
//相同的代码抽取到父类里面
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView{
    public P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //避免进入页面EdiText自动弹出软键盘
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //避免进入页面EdiText自动弹出软键盘 且 避免底部控件被顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //butterknife,findviewbyid,添加监听
        ButterKnife.bind(this);

        mPresenter = initPresenter();
        if (mPresenter !=null){
            mPresenter.attachView(this);
        }
        initView();
        initData();
        initListener();
    }

    protected abstract void initListener();

    protected abstract  P initPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消v和p的关联
        //打断网络请求+订阅关系
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    @Override
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToastShort(msg);
            }
        });

    }
}
