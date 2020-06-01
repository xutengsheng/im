package com.xts.im.view;

import com.xts.im.base.BaseView;
import com.xts.im.bean.TencentBean;

import java.util.ArrayList;

public interface TencentView extends BaseView {
    void setData(ArrayList<TencentBean> list);
}
