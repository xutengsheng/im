package com.xts.im;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.xts.im.adapter.BaseRlvAdapter;
import com.xts.im.adapter.RlvChatAdapter;
import com.xts.im.util.AudioUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRlv;
    private EditText mEtContent;
    /**
     * 发送
     */
    private Button mBtnSend;
    /**
     * 开始录音
     */
    private Button mBtnRecord;
    /**
     * 发送语音
     */
    private Button mBtnSendVoice;
    /**
     * 语音聊天
     */
    private Button mBtnVoiceChat;
    /**
     * 视频聊天
     */
    private Button mBtnVideoChat;
    private RlvChatAdapter mAdapter;
    private String mPath;
    private long mDuration;
    private String mGroup;
    /**
     * 请输入群组名称
     */
    private EditText mEtGroupName;
    /**
     * 创建群
     */
    private Button mBtnCreate;
    /**
     * 请输入群组id
     */
    private EditText mEtGroupId;
    /**
     * 加入群聊
     */
    private Button mBtnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        initView();

        //注册接受消息的监听
        initListener();
    }

    private void initListener() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(final List<EMMessage> messages) {
            //收到消息
            //
            for (int i = 0; i < messages.size(); i++) {
                Log.d("tag", "onMessageReceived: " + messages.get(i).toString());
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addData(messages);
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    private void initView() {
        mRlv = (RecyclerView) findViewById(R.id.rlv);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mBtnRecord = (Button) findViewById(R.id.btn_record);
        mBtnRecord.setOnClickListener(this);
        mBtnSendVoice = (Button) findViewById(R.id.btn_send_voice);
        mBtnSendVoice.setOnClickListener(this);
        mBtnVoiceChat = (Button) findViewById(R.id.btn_voice_chat);
        mBtnVoiceChat.setOnClickListener(this);
        mBtnVideoChat = (Button) findViewById(R.id.btn_video_chat);
        mBtnVideoChat.setOnClickListener(this);

        ArrayList<EMMessage> list = new ArrayList<>();
        mAdapter = new RlvChatAdapter(list);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.setAdapter(mAdapter);

        mRlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter.setOnItemClickListenr(new BaseRlvAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                EMMessage emMessage = mAdapter.mList.get(position);
                //获取本地音频的路径
                EMMessageBody body = emMessage.getBody();
                EMVoiceMessageBody voiceMessageBody = (EMVoiceMessageBody) body;
                String localUrl = voiceMessageBody.getLocalUrl();

                if (!TextUtils.isEmpty(localUrl)) {
                    startVoice(localUrl);
                }
            }
        });
        mEtGroupName = (EditText) findViewById(R.id.et_group_name);
        mBtnCreate = (Button) findViewById(R.id.btn_create);
        mBtnCreate.setOnClickListener(this);
        mEtGroupId = (EditText) findViewById(R.id.et_group_id);
        mBtnJoin = (Button) findViewById(R.id.btn_join);
        mBtnJoin.setOnClickListener(this);
    }

    private void startVoice(String localUrl) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(localUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_send:
                sendTextMsg();
                break;
            case R.id.btn_record:
                if (AudioUtil.isRecording) {
                    //停止录音
                    AudioUtil.stopRecord();
                    mBtnRecord.setText("开始录音");
                } else {
                    //开始录音

                    AudioUtil.startRecord(new AudioUtil.ResultCallBack() {
                        @Override
                        public void onFail(String s) {
                            Log.d("tag", "onFail: ");
                        }

                        @Override
                        public void onSuccess(String absolutePath, long duration) {
                            Log.d("tag", "onSuccess: " + absolutePath + "，duration：" + duration);

                            mPath = absolutePath;
                            mDuration = duration;
                        }
                    });
                    mBtnRecord.setText("停止录音");
                }
                break;
            case R.id.btn_send_voice:
                sendVoice();
                break;
            case R.id.btn_voice_chat:
                break;
            case R.id.btn_video_chat:
                break;
            case R.id.btn_create:
                createGroup();
                break;
            case R.id.btn_join:
                join();
                break;
        }
    }

    private void join() {
        mGroup = mEtGroupId.getText().toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
//如果群开群是自由加入的，即group.isMembersOnly()为false，直接join
                try {
                    EMClient.getInstance().groupManager().joinGroup(mGroup);//需异步处理
                    showToast("加入群组成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //创建群组
    private void createGroup() {
        final String groupName = mEtGroupName.getText().toString().trim();
        if (TextUtils.isEmpty(groupName)) {
            showToast("群组名称不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
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

                try {
                    EMGroup group = EMClient.getInstance().groupManager().createGroup(groupName,
                            "", new String[]{}, "", option);

                    //群组id
                    mGroup = group.getGroupId();
                    Log.d("tag", "run: " + mGroup);
                    showToast("创建成功");

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //发送语音消息
    private void sendVoice() {
        if (TextUtils.isEmpty(mPath)) {
            showToast("请先录音");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //filePath为语音文件路径，length为录音时间(秒)
                EMMessage message = EMMessage.createVoiceSendMessage(mPath, (int) mDuration, mGroup);
//如果是群聊，设置chattype，默认是单聊
                message.setChatType(EMMessage.ChatType.GroupChat);
                EMClient.getInstance().chatManager().sendMessage(message);

                //发送完成，置为""避免再次点击发送相同的语音
                mPath = "";

                //自己发送的消息自己不会收到，需要添加到界面上去
                addMessage(message);
            }
        }).start();
    }

    //发送文本消息
    private void sendTextMsg() {
        final String content = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("消息不能为空");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(content, mGroup);
//如果是群聊，设置chattype，默认是单聊
                message.setChatType(EMMessage.ChatType.GroupChat);
//发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                //自己发送的消息自己不会收到，需要添加到界面上去
                addMessage(message);
            }
        }).start();
    }

    private void addMessage(final EMMessage message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addSingleData(message);
                mEtContent.setText("");
            }
        });
    }

    public void showToast(final String msg) {

        //1.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GroupChatActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
