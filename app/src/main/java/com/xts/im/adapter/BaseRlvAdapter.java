package com.xts.im.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRlvAdapter extends RecyclerView.Adapter {
    public OnItemClickListenr mOnItemClickListener;

    public interface OnItemClickListenr{
        void onItemClick(View v,int position);
    }

    public void setOnItemClickListenr(OnItemClickListenr listenr){
        this.mOnItemClickListener = listenr;
    }

}
