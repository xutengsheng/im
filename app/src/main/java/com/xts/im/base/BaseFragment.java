package com.xts.im.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.xts.im.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private Unbinder mUnbinder;
    public P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, inflate);
        //如果写到这里,在这个方法里面消耗的时间会久一点
        /*initView(inflate);
        initData();*/
        return inflate;
    }

    /**
     * fragment的布局创建完成了
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view就是onCreateView()方法的返回值inflate
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initView(view);
        initData();
    }

    protected abstract P initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在fragment中需要解绑
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        ////取消v和p的关联
        //打断网络请求+订阅关系
        if (mPresenter != null){
            mPresenter.detachView();
        }

    }

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    @Override
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
