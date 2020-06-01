package com.xts.im.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xts.im.R;

import java.util.ArrayList;
import java.util.List;

public class RlvContactAdapter extends BaseRlvAdapter {
    public ArrayList<String> mList;

    public RlvContactAdapter(ArrayList<String> list) {

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
        vh.mTv.setText(mList.get(position));

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<String> usernames) {
        mList.addAll(usernames);
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
