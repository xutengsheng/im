package com.xts.im.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;

import com.xts.im.R;
import com.xts.im.ui.fragment.ZhihuFragment;

public class ZhihuNewsActivity extends AppCompatActivity {

    private ConstraintLayout mCl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_news);
        initView();
    }

    private void initView() {
        mCl = (ConstraintLayout) findViewById(R.id.cl);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.cl,new ZhihuFragment())
                .commit();
    }
}
