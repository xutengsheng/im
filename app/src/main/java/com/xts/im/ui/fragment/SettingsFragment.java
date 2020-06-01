package com.xts.im.ui.fragment;

import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;

import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.base.Constants;
import com.xts.im.presenter.SettingsPresenter;
import com.xts.im.util.SpUtil;
import com.xts.im.view.SettingsView;

import butterknife.BindView;

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsView {

    //夜间模式,有两套资源,资源的名称相同,一旦切换日夜间模式,系统会去不同的位置查找资源,
    //我们只需要将两套资源修改为我们想要的资源即可
    @BindView(R.id.sc)
    SwitchCompat mSc;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }

    @Override
    protected SettingsPresenter initPresenter() {
        return new SettingsPresenter();
    }

    @Override
    protected void initData() {
        mSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*if (日间模式){
                    //切换夜间模式
                }else {
                    //切换日间模式
                }*/
                //帮我们切换日夜间模式
                //切换日夜间模式的时候,activity会重建,
                //只有用户点击了swichCompat才需要切换模式
                /*if (buttonView.isPressed()) {
                    ((MainActivity)getActivity()).mFlag = "fdlksfjk";
                    //记录由于设置模式导致activity销毁,需要显示的fragment类型
                    SpUtil.setParam(Constants.CURRENT_FRAG_TYPE, MainActivity.TYPE_SETTINGS);

                    UIModeUtil.changeModeUI((AppCompatActivity) getActivity());
                }*/
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initView(View inflate) {
        //根据当前日夜间模式决定switchcompat是否选中
        int mode = (int) SpUtil.getParam(Constants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        if (mode == AppCompatDelegate.MODE_NIGHT_NO){
            //日间模式
            mSc.setChecked(false);
        }else {
            mSc.setChecked(true);
        }

    }

}
