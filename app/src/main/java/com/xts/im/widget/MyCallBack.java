package com.xts.im.widget;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class MyCallBack extends ItemTouchHelper.Callback {

    private ItemCallBack mItemCallBack;
    private boolean mDrag = true;
    private boolean mSwipe = true;

    public MyCallBack(ItemCallBack adapter){
        mItemCallBack = adapter;
    }

    /**
     * 需要我们给定RecyclerView子条目可以滑动的方向
     * 一般使用makeMovementFlags(int,int)或makeFlag(int, int)来构造我们的返回值,
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //规定上下可以拖拽
        int drag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        int swipe = ItemTouchHelper.LEFT;
        return makeMovementFlags(drag,swipe);
    }

    /**
     * 上下拖拽会触发的回调方法,最后返回true，表示被拖动的ViewHolder已经移动到了目的位置
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        mItemCallBack.onItemMove(viewHolder.getLayoutPosition(),
                target.getLayoutPosition());
        return true;
    }

    /**
     * 左右侧滑的时候会触发的回调
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mItemCallBack.onItemDelete(viewHolder.getLayoutPosition());
    }

    /**
     * 支持长按拖动,默认是true
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return mDrag;
    }

    /**
     * 支持左右滑动,即可以调用到onSwiped()方法,默认是true
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return mSwipe;
    }

    public void setDragEnable(boolean drag){
        this.mDrag = drag;
    }

    public void setSwipeEnable(boolean swipe){
        this.mSwipe = swipe;
    }


}
