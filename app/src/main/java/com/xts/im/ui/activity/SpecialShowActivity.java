package com.xts.im.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xts.im.R;
import com.xts.im.base.Constants;
import com.xts.im.bean.DataBean;
import com.xts.im.ui.adapter.RlvSpecialShowAdapter;
import com.xts.im.widget.MyCallBack;

import java.util.ArrayList;

public class SpecialShowActivity extends AppCompatActivity {

    private ArrayList<DataBean> mTitles;
    private RecyclerView mRlv;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_show);
        mTitles = (ArrayList<DataBean>) getIntent().getSerializableExtra(Constants.DATA);
        initView();
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        mRlv = (RecyclerView) findViewById(R.id.rlv);

        mToolBar.setTitle("兴趣选择");
        setSupportActionBar(mToolBar);

        mRlv.setLayoutManager(new LinearLayoutManager(this));

        RlvSpecialShowAdapter adapter = new RlvSpecialShowAdapter(mTitles);
        mRlv.setAdapter(adapter);

        MyCallBack callBack = new MyCallBack(adapter);
        callBack.setSwipeEnable(false);
        //帮助我们监听子条目状态改变的类
        ItemTouchHelper helper = new ItemTouchHelper(callBack);
        //关联一个RecyclerView
        helper.attachToRecyclerView(mRlv);

    }

    //点返回键会触发的方法
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(Constants.DATA, mTitles);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }
}
