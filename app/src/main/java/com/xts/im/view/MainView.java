package com.xts.im.view;

import com.hyphenate.chat.EMGroup;
import com.xts.im.base.BaseView;

import java.util.List;

public interface MainView extends BaseView {
    void setContacts(List<EMGroup> groupList ,List<String> list);


}
