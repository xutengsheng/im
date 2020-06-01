package com.xts.im.presenter;


import com.xts.im.base.BasePresenter;
import com.xts.im.bean.HotBean;
import com.xts.im.model.HotModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.HotView;

public class HotPresenter extends BasePresenter<HotView> {

    private HotModel mHotModel;

    @Override
    protected void initModel() {
        mHotModel = new HotModel();
        addModel(mHotModel);
    }

    public void getData() {
        mHotModel.getData(new ResultCallBack<HotBean>() {
            @Override
            public void onSuccess(HotBean hotBean) {
                mView.setData(hotBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
