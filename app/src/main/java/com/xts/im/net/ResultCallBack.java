package com.xts.im.net;

public interface ResultCallBack<T> {
    void onSuccess(T t);
    void onFail(String msg);
}
