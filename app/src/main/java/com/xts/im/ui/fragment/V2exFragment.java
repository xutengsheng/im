package com.xts.im.ui.fragment;


import android.view.View;

import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.presenter.V2exPresenter;
import com.xts.im.view.V2exView;

public class V2exFragment extends BaseFragment<V2exPresenter> implements V2exView {

    private String mUrl = "https://www.v2ex.com/";

    public static V2exFragment newInstance() {
        V2exFragment fragment = new V2exFragment();

        return fragment;
    }

    @Override
    protected V2exPresenter initPresenter() {
        return new V2exPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_v2ex;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
