package com.xts.im.presenter;

import com.xts.im.base.BasePresenter;
import com.xts.im.bean.NewsDetailBean;
import com.xts.im.model.NewsDetailModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.NewsDetailView;

public class NewsDetailPresenter extends BasePresenter<NewsDetailView> {

    private NewsDetailModel mNewsDetailModel;

    @Override
    protected void initModel() {
        mNewsDetailModel = new NewsDetailModel();
        addModel(mNewsDetailModel);
    }

    public void getDetail(int id) {
        mNewsDetailModel.getDetail(id, new ResultCallBack<NewsDetailBean>() {
            @Override
            public void onSuccess(NewsDetailBean bean) {
                if (bean != null && mView !=null){
                    mView.setData(bean);
                }
            }

            @Override
            public void onFail(String msg) {
                if (mView != null){
                    mView.showToast(msg);
                }
            }
        });
    }
}
