package com.xts.im.presenter;

import com.xts.im.base.BasePresenter;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.model.SearchModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.AboutView;
import com.xts.im.view.SearchView;

public class SearchPresenter extends BasePresenter<SearchView> {

    private SearchModel mSearchModel;

    @Override
    protected void initModel() {
        mSearchModel = new SearchModel();
        addModel(mSearchModel);
    }

    public void search(String query, int page) {
        mSearchModel.getData(query,page, new ResultCallBack<ItInfoItemBean>() {
            @Override
            public void onSuccess(ItInfoItemBean itInfoItemBean) {
                mView.setData(itInfoItemBean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
