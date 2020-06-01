package com.xts.im.model;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.HotBean;
import com.xts.im.bean.NaviBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class GuideModel extends BaseModel {
    public void getData(final ResultCallBack<NaviBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getWanApi()
                        .getNavi()
                        .compose(RxUtils.<NaviBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<NaviBean>() {
                            @Override
                            protected void onSuccess(NaviBean newsDetailBean) {
                                callBack.onSuccess(newsDetailBean);
                            }
                        })
        );
    }
}
