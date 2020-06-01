package com.xts.im.view;

import com.xts.im.base.BaseView;

public interface LoginView extends BaseView {
    void go2MainActivity();
    void go2Register(int typeNormalRegister, String uid, String typeid);
}
