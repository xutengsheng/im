package com.xts.im.model;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.HotBean;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class SearchModel extends BaseModel {
    public void getData(String query, int page, final ResultCallBack<ItInfoItemBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getWanApi()
                        .search(page,query)
                        .compose(RxUtils.<ItInfoItemBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<ItInfoItemBean>() {
                            @Override
                            protected void onSuccess(ItInfoItemBean newsDetailBean) {
                                callBack.onSuccess(newsDetailBean);
                            }
                        })
        );
    }
}
