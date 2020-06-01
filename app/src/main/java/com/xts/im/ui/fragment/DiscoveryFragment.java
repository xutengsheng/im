package com.xts.im.ui.fragment;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xts.im.R;
import com.xts.im.base.BaseFragment;
import com.xts.im.presenter.DiscoveryPresenter;
import com.xts.im.ui.activity.ItInfoActivity;
import com.xts.im.ui.activity.LoginActivity;
import com.xts.im.ui.activity.MainActivity;
import com.xts.im.ui.activity.TencentActivity;
import com.xts.im.ui.activity.ZhihuNewsActivity;
import com.xts.im.view.DiscoveryView;

import butterknife.BindView;
import butterknife.OnClick;

public class DiscoveryFragment extends BaseFragment<DiscoveryPresenter> implements DiscoveryView {

    @BindView(R.id.iv_zhihu)
    ImageView mIvZhihu;
    @BindView(R.id.tv_zhihu)
    TextView mTvZhihu;
    @BindView(R.id.cl_zhihu)
    ConstraintLayout mClZhihu;
    @BindView(R.id.iv_it)
    ImageView mIvIt;
    @BindView(R.id.tv_it)
    TextView mTvIt;
    @BindView(R.id.cl_it_info)
    ConstraintLayout mClItInfo;
    @BindView(R.id.iv_tencent)
    ImageView mIvTencent;
    @BindView(R.id.tv_tencent)
    TextView mTvTencent;
    @BindView(R.id.cl_tencent)
    ConstraintLayout mClTencent;
    @BindView(R.id.et_friend)
    EditText mEtFriend;
    @BindView(R.id.et_group)
    EditText mEtGroup;
    @BindView(R.id.et_create_group)
    EditText mEtCreateGroup;
    @BindView(R.id.iv_add_friend)
    ImageView mIvAdd;

    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragment = new DiscoveryFragment();

        return fragment;
    }

    @Override
    protected DiscoveryPresenter initPresenter() {
        return new DiscoveryPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }



    @OnClick({R.id.cl_zhihu, R.id.cl_it_info, R.id.cl_tencent,
            R.id.iv_add_friend,R.id.iv_add_group,R.id.iv_create_group,
            R.id.btn_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cl_zhihu:
                startActivity(new Intent(getContext(), ZhihuNewsActivity.class));
                break;
            case R.id.cl_it_info:
                startActivity(new Intent(getContext(), ItInfoActivity.class));
                break;
            case R.id.cl_tencent:
                startActivity(new Intent(getContext(), TencentActivity.class));
                break;
            case R.id.iv_add_friend:
                addFriend();
                break;
            case R.id.iv_add_group:
                addGroup();
                break;
            case R.id.iv_create_group:
                createGroup();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        mPresenter.logout();
    }

    private void createGroup() {
        String name = mEtCreateGroup.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast("群组名称不能为空");
            return;
        }

        mPresenter.createGroup(name);
    }

    private void addGroup() {
        String name = mEtGroup.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast("群组id不能为空");
            return;
        }

        mPresenter.addGroup(name);
    }

    private void addFriend() {
        String name = mEtFriend.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast("好友账号不能为空");
            return;
        }

        mPresenter.addFriend(name);
    }

    @Override
    public void addFriendSuccess() {
        ((MainActivity)getActivity()).refreshContacts();
    }

    @Override
    public void logoutSuccess() {
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }
}
