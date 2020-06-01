package com.xts.im.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xts.im.R;
import com.xts.im.adapter.BaseRlvAdapter;
import com.xts.im.base.BaseApp;
import com.xts.im.bean.DataBean;
import com.xts.im.db.DataBeanDao;
import com.xts.im.widget.ItemCallBack;

import java.util.ArrayList;
import java.util.Collections;

public class RlvSpecialShowAdapter extends BaseRlvAdapter implements ItemCallBack {
    private ArrayList<DataBean> mTitles;
    private final DataBeanDao mDataBeanDao;

    public RlvSpecialShowAdapter(ArrayList<DataBean> titles) {
        mDataBeanDao = BaseApp.getInstance().getDaoSession().getDataBeanDao();
        mTitles = titles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special, parent, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH) holder;
        final DataBean bean = mTitles.get(position);
        vh.mTvTitle.setText(bean.getName());
        vh.mSc.setChecked(bean.isSelect());

        //swichcompat的状态切换监听
        vh.mSc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用户修改的状态需要保存下来
                if (buttonView.isPressed()) {
                    bean.setSelect(isChecked);
                    mDataBeanDao.insertOrReplace(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final SwitchCompat mSc;

        public VH(@NonNull View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mSc = itemView.findViewById(R.id.sc);
        }
    }

    /**
     * 删除某个条目的时候可以调用这个方法
     *
     * @param position
     */
    @Override
    public void onItemDelete(int position) {
        /*mTitles.remove(position);
        notifyDataSetChanged();*/
    }

    /**
     * 条目交换位置得回调方法
     *
     * @param startPosition
     * @param toPosition
     */
    @Override
    public void onItemMove(int startPosition, int toPosition) {
        //工具类交换集合的两个位置的数据
        Collections.swap(mTitles, startPosition, toPosition);
        //局部刷新
        notifyItemMoved(startPosition, toPosition);
        mTitles.get(startPosition).setIndex(startPosition);
        mTitles.get(toPosition).setIndex(toPosition);
        mDataBeanDao.insertOrReplaceInTx(mTitles.get(startPosition),mTitles.get(toPosition));
    }
}
