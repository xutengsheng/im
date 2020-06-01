package com.xts.im.presenter;

import com.xts.im.base.BasePresenter;
import com.xts.im.bean.NaviBean;
import com.xts.im.model.GuideModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.AboutView;
import com.xts.im.view.GuideView;

public class GuidePresenter extends BasePresenter<GuideView> {

    private GuideModel mGuideModel;

    @Override
    protected void initModel() {
        mGuideModel = new GuideModel();
        addModel(mGuideModel);
    }

    public void getData() {
        mGuideModel.getData(new ResultCallBack<NaviBean>() {
            @Override
            public void onSuccess(NaviBean naviBean) {
                mView.setData(naviBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
