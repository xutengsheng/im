package com.xts.im.model;


import com.xts.im.base.BaseModel;
import com.xts.im.bean.NewsDetailBean;
import com.xts.im.net.HttpUtil;
import com.xts.im.net.ResultCallBack;
import com.xts.im.net.ResultSubScriber;
import com.xts.im.net.RxUtils;

public class NewsDetailModel extends BaseModel {
    public void getDetail(int id, final ResultCallBack<NewsDetailBean> callBack) {
        addDisposable(
                HttpUtil.getInstance()
                        .getZHihuApi()
                        .getNewsDetail(id+"")
                        .compose(RxUtils.<NewsDetailBean>rxSchedulerHelper())
                        .subscribeWith(new ResultSubScriber<NewsDetailBean>() {
                            @Override
                            protected void onSuccess(NewsDetailBean newsDetailBean) {
                                callBack.onSuccess(newsDetailBean);
                            }
                        })
        );

    }
}
