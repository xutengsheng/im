package com.xts.im.presenter;

import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.xts.im.base.BasePresenter;
import com.xts.im.model.MainModel;
import com.xts.im.net.ThreadManager;
import com.xts.im.view.MainView;

import java.util.List;

public class MainPresenter extends BasePresenter<MainView> {

    private MainModel mMainModel;

    @Override
    protected void initModel() {
        mMainModel = new MainModel();
        addModel(mMainModel);
    }

    public void getContacts() {
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //群列表
                            //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                            List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();

                            //好友列表
                            final List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();

                            Log.d("tag", "run: "+usernames.toString());

                            mView.setContacts(grouplist,usernames);
                        } catch (HyphenateException e) {
                            e.printStackTrace();

                        }

                    }
                });
    }
}
