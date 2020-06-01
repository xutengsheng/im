package com.xts.im.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xts.im.R;
import com.xts.im.base.Constants;

public class WebActivity extends AppCompatActivity {

    private String mUrl;
    private ConstraintLayout mCl;
    private WebView mWebView;

    public static void startAct(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(Constants.DATA, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mUrl = getIntent().getStringExtra(Constants.DATA);
        initView();
    }

    private void initView() {
        mCl = (ConstraintLayout) findViewById(R.id.cl);
        mWebView = new WebView(this);
        mCl.addView(mWebView);
        mWebView.setWebChromeClient(new WebChromeClient(){

        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(String.valueOf(request.getUrl()));
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        WebSettings settings = mWebView.getSettings();
        //settings.setUseWideViewPort(true);
        //settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void onDestroy() {

        if (mWebView != null){
            mCl.removeAllViews();
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()

            mWebView.stopLoading();
            //退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.destroy();
        }

        super.onDestroy();
    }
}
