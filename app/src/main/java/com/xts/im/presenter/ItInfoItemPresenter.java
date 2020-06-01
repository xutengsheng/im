package com.xts.im.presenter;

import com.xts.im.base.BasePresenter;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.model.ItInfoItemModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.ItInfoItemView;

public class ItInfoItemPresenter extends BasePresenter<ItInfoItemView> {

    private ItInfoItemModel mItInfoItemModel;

    @Override
    protected void initModel() {
        mItInfoItemModel = new ItInfoItemModel();
        addModel(mItInfoItemModel);
    }

    public void getChapterList(int id, int page) {
        mItInfoItemModel.getChapterList(id,page, new ResultCallBack<ItInfoItemBean>() {
            @Override
            public void onSuccess(ItInfoItemBean itInfoItemBean) {
                mView.setChapterList(itInfoItemBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
