package com.xts.im.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.xts.im.R;

import java.util.ArrayList;
import java.util.List;

public class RlvChatAdapter extends BaseRlvAdapter {
    public ArrayList<EMMessage> mList;

    public RlvChatAdapter(ArrayList<EMMessage> list) {

        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH vh = new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        VH vh = (VH) holder;
        EMMessage emMessage = mList.get(position);
        //消息来源
        String from = emMessage.getFrom();
        //发送给谁
        String to = emMessage.getTo();
        //消息内容
        EMMessageBody body = emMessage.getBody();
        //发送消息的时间
        long msgTime = emMessage.getMsgTime();

        vh.mTv.setText("消息来自："+from+"，发送给："+to+"，发送时间："+msgTime+"消息内容："+body.toString());
        EMMessage.Type type = emMessage.getType();
        //判断是否为语音消息
        if (type == EMMessage.Type.VOICE) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(v,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<EMMessage> messages) {
        mList.addAll(messages);
        notifyDataSetChanged();
    }

    public void addSingleData(EMMessage message) {
        mList.add(message);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder{

        private final TextView mTv;

        public VH(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv_friend);
        }
    }
}
