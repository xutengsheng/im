package com.xts.im.wxapi;


import android.os.Bundle;

import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.xts.im.R;


public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }

}
