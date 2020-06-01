package com.xts.im.model;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.ItInfoItemBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class ItInfoItemModel extends BaseModel {
    public void getChapterList(int id, int page, final ResultCallBack<ItInfoItemBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getWanApi()
                        .getItInfoList(id,page)
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
