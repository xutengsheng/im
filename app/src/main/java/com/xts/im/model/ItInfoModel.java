package com.xts.im.model;

import com.xts.im.base.BaseModel;
import com.xts.im.bean.ChapterTabBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class ItInfoModel extends BaseModel {
    public void getChapterTab(final ResultCallBack<ChapterTabBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getWanApi()
                        .getChapterTab()
                        .compose(RxUtils.<ChapterTabBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<ChapterTabBean>() {
                            @Override
                            protected void onSuccess(ChapterTabBean newsDetailBean) {
                                callBack.onSuccess(newsDetailBean);
                            }
                        })
        );
    }
}
