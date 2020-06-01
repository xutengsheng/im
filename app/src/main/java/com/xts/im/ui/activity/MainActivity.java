package com.xts.im.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.ArrayMap;
import android.widget.FrameLayout;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.xts.im.R;
import com.xts.im.base.BaseActivity;
import com.xts.im.base.BaseApp;
import com.xts.im.presenter.MainPresenter;
import com.xts.im.ui.fragment.DiscoveryFragment;
import com.xts.im.view.MainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

    @BindView(R.id.fl)
    FrameLayout mFl;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mFragments;
    private FragmentManager mManager;
    private int mLastPosition;
    private ArrayMap<String,Integer> mTypeArray = new ArrayMap<>();


    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getContacts();
    }

    @Override
    protected void initListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initView() {
        mManager = getSupportFragmentManager();
        initPers();
        initTitles();
        initTab();
        initFragments();
        showFirstFragment();
    }

    private void initTab() {
        for (int i = 0; i < mTitles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(i)));
        }
    }

    private void showFirstFragment() {
        switchFragment(0);
    }

    private void switchFragment(int position) {
        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment showFragment = mFragments.get(position);
        Fragment hideFragment = mFragments.get(mLastPosition);

        if (!showFragment.isAdded()) {
            transaction.add(R.id.fl, showFragment);
        }

        transaction.hide(hideFragment);
        transaction.show(showFragment);
        transaction.commit();

        mLastPosition = position;
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });

        mFragments.add(conversationListFragment);
        EaseContactListFragment contactListFragment = new EaseContactListFragment();

        //contactListFragment.setContactsMap(getContacts());
        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,mTypeArray.get(user.getUsername()));
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername());
                startActivity(intent);
            }
        });
        mFragments.add(contactListFragment);

        mFragments.add(DiscoveryFragment.newInstance());
    }

    private void initTitles() {
        mTitles = new ArrayList<>();
        mTitles.add(BaseApp.getRes().getString(R.string.conversation));
        mTitles.add(BaseApp.getRes().getString(R.string.contacts));
        mTitles.add(BaseApp.getRes().getString(R.string.discovery));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initPers() {
        String[] pers = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this, pers, 100);
    }

    @Override
    public void setContacts(final List<EMGroup> groupList , final List<String> list) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTypeArray.clear();
                EaseContactListFragment fragment = (EaseContactListFragment) mFragments.get(1);
                HashMap<String, EaseUser> map = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    map.put(list.get(i),new EaseUser(list.get(i)));
                    mTypeArray.put(list.get(i),EaseConstant.CHATTYPE_SINGLE);
                }

                for (int i = 0; i < groupList.size(); i++) {
                    String groupId = groupList.get(i).getGroupId();
                    map.put(groupId,new EaseUser(groupId));
                    mTypeArray.put(groupId,EaseConstant.CHATTYPE_GROUP);
                }
                fragment.setContactsMap(map);
            }
        });

    }

    public void refreshContacts() {
        mPresenter.getContacts();
    }
}
