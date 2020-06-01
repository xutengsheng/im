package com.xts.im.model;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.HotBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class HotModel extends BaseModel {
    public void getData(final ResultCallBack<HotBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getZHihuApi()
                        .getHot()
                        .compose(RxUtils.<HotBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<HotBean>() {
                            @Override
                            protected void onSuccess(HotBean newsDetailBean) {
                                callBack.onSuccess(newsDetailBean);
                            }
                        })
        );
    }
}
