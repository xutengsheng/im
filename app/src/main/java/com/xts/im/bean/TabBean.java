package com.xts.im.bean;

import java.io.Serializable;

public class TabBean implements Serializable {
    public String title;
    public boolean isSelect;

    public TabBean(String title, boolean isSelect) {
        this.title = title;
        this.isSelect = isSelect;
    }
}
