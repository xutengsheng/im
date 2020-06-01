package com.xts.im.presenter;


import com.xts.im.base.BasePresenter;
import com.xts.im.bean.SectionBean;
import com.xts.im.model.SectionModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.SectionView;

public class SectionPresenter extends BasePresenter<SectionView> {

    private SectionModel mSectionModel;

    @Override
    protected void initModel() {
        mSectionModel = new SectionModel();
        addModel(mSectionModel);
    }

    public void getData() {
        //mSectionModel.getData()
        mSectionModel.getData(new ResultCallBack<SectionBean>() {
            @Override
            public void onSuccess(SectionBean sectionBean) {
                mView.setData(sectionBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
