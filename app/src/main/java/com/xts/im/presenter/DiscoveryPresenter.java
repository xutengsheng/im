package com.xts.im.presenter;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.exceptions.HyphenateException;
import com.xts.im.base.BasePresenter;
import com.xts.im.net.ThreadManager;
import com.xts.im.util.LogUtil;
import com.xts.im.view.DiscoveryView;

public class DiscoveryPresenter extends BasePresenter<DiscoveryView> {
    @Override
    protected void initModel() {

    }

    public void addFriend(final String name) {
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        //参数为要添加的好友的username和添加理由
                        try {
                            EMClient.getInstance().contactManager().addContact(name, "");
                            mView.showToast("添加成功");
                            mView.addFriendSuccess();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            mView.showToast("添加失败");
                        }
                    }
                });
    }

    public void addGroup(final String groupid) {
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        //参数为要添加的好友的username和添加理由
                        try {
                            //如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                            EMClient.getInstance().groupManager().joinGroup(groupid);//需异步处理
                            //需要申请和验证才能加入的，即group.isMembersOnly()为true，调用下面方法
                            //EMClient.getInstance().groupManager().applyJoinToGroup(groupid, "求加入");//需异步处理
                            mView.showToast("添加成功");
                            mView.addFriendSuccess();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            mView.showToast("添加失败");
                        }
                    }
                });
    }

    public void createGroup(final String name) {
        ThreadManager.getInstance()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        //参数为要添加的好友的username和添加理由
                        try {
                            /**
                             * 创建群组
                             * @param groupName 群组名称
                             * @param desc 群组简介
                             * @param allMembers 群组初始成员，如果只有自己传空数组即可
                             * @param reason 邀请成员加入的reason
                             * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
                             *               option.inviteNeedConfirm表示邀请对方进群是否需要对方同意，默认是需要用户同意才能加群的。
                             *               option.extField创建群时可以为群组设定扩展字段，方便个性化订制。
                             * @return 创建好的group
                             * @throws HyphenateException
                             */
                            EMGroupOptions option = new EMGroupOptions();
                            option.maxUsers = 200;
                            option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;

                            EMGroup group = EMClient.getInstance().groupManager().createGroup(name, "", new String[]{}, "", option);
                            LogUtil.print("groupid:"+group.getGroupId());
                            mView.showToast("创建成功");
                            mView.addFriendSuccess();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            mView.showToast("创建失败");
                        }
                    }
                });
    }

    public void logout() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                mView.showToast("退出成功");
                mView.logoutSuccess();

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                mView.showToast("退出失败");

            }
        });
    }
}
