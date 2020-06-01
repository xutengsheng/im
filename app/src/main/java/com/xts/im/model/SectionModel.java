package com.xts.im.model;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.SectionBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class SectionModel extends BaseModel {
    public void getData(final ResultCallBack<SectionBean> callBack) {
        addDisposable(
                HttpUtil.getInstance().getZHihuApi()
                    .getSection()
                    .compose(RxUtils.<SectionBean>rxSchedulerHelper())
                    .subscribeWith(new ResultSubScriber<SectionBean>() {
                        @Override
                        protected void onSuccess(SectionBean sectionBean) {
                            callBack.onSuccess(sectionBean);
                        }
                    })
        );
    }
}
