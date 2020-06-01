package com.xts.im.presenter;

import com.xts.im.base.BasePresenter;
import com.xts.im.bean.TencentBean;
import com.xts.im.model.TencentModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.AboutView;
import com.xts.im.view.TencentView;

import java.util.ArrayList;

public class TencentPresenter extends BasePresenter<TencentView> {

    private TencentModel mTencentModel;

    @Override
    protected void initModel() {
        mTencentModel = new TencentModel();
        addModel(mTencentModel);
    }

    public void getData() {
        mTencentModel.getData(new ResultCallBack<ArrayList<TencentBean>>() {
            @Override
            public void onSuccess(ArrayList<TencentBean> tencentBeans) {
                mView.setData(tencentBeans);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
