package com.xts.im.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.base.Constants;
import com.xts.im.presenter.AboutPresenter;
import com.xts.im.view.AboutView;

import butterknife.BindView;

public class AboutFragment extends BaseFragment<AboutPresenter> implements AboutView {

    public static final String TAG = "AboutFragment";
    @BindView(R.id.tv)
    TextView mTv;
    private String mTitle;

    public static AboutFragment newInstance(String title){
        AboutFragment fragment = new AboutFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.DATA,title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected AboutPresenter initPresenter() {
        return new AboutPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView(View inflate) {
        Bundle arguments = getArguments();
        mTitle = arguments.getString(Constants.DATA);
        mTv.setText(mTitle);
        Log.d(TAG, "initView: "+ mTitle);;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: "+mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: "+mTitle);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: "+mTitle);
    }
}
