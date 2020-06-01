package com.xts.im.presenter;


import com.xts.im.base.BasePresenter;
import com.xts.im.bean.DailyNewsBean;
import com.xts.im.model.DailyNewsModel;
import com.xts.im.net.ResultCallBack;
import com.xts.im.view.DailyNewsView;

public class DailyNewsPresenter extends BasePresenter<DailyNewsView> {

    private DailyNewsModel mDailyNewsModel;


    @Override
    protected void initModel() {
        mDailyNewsModel = new DailyNewsModel();
        addModel(mDailyNewsModel);
    }

    /**
     * 如果日期是"",那么代表是要最新新闻,如果不为空,请求给定日期的新闻
     *
     * @param date 日期,格式:20200202
     */
    public void getData(String date) {
        mDailyNewsModel.getData(date, new ResultCallBack<DailyNewsBean>() {
            @Override
            public void onSuccess(DailyNewsBean bean) {
                if (bean != null && mView != null) {
                    mView.setData(bean);
                }
            }

            @Override
            public void onFail(String msg) {
                if (mView != null) {
                    mView.showToast(msg);
                }
            }
        });
    }
}
