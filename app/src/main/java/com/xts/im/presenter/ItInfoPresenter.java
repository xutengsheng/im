package com.xts.im.presenter;

import com.xts.im.base.BasePresenter;
import com.xts.im.bean.ChapterTabBean;
import com.xts.im.model.ItInfoModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.ItInfoView;

public class ItInfoPresenter extends BasePresenter<ItInfoView> {

    private ItInfoModel mItInfoModel;

    @Override
    protected void initModel() {
        mItInfoModel = new ItInfoModel();
        addModel(mItInfoModel);
    }

    public void getChapterTab() {
        mItInfoModel.getChapterTab(new ResultCallBack<ChapterTabBean>() {
            @Override
            public void onSuccess(ChapterTabBean chapterTabBean) {
                mView.setTab(chapterTabBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
