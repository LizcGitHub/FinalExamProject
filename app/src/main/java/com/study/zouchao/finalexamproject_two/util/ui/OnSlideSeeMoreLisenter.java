package com.study.zouchao.finalexamproject_two.util.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * RecyclerView的上滑更多监听器
 *  （用于判断RecyclerView是否滑动到底部）
 * Created by Administrator on 2017/4/16.
 */

public class OnSlideSeeMoreLisenter extends RecyclerView.OnScrollListener {
    private IOnSlideToBottomListener mListener;
    public OnSlideSeeMoreLisenter(IOnSlideToBottomListener listener) {
        mListener = listener;
    }
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("onScrollStateChange", "onScroll");
        if (newState == RecyclerView.SCROLL_STATE_IDLE && isSlideToBottom(recyclerView)) {
            if (mListener != null) {
                mListener.onSeeMore();
                Log.d("滚动有bug？", "已经滚动到底部了？？");
            }
        }
    }
    /**
     * RecyclerView已经滑动到底部
     */
    private boolean isSlideToBottom(RecyclerView rv) {
//        if (rv == null) return false;
//        if (rv.computeVerticalScrollExtent() + rv.computeVerticalScrollOffset()
//                >= rv.computeHorizontalScrollRange())
//            return true;
//        return false;
        LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = rv.getScrollState();
        if(state == rv.SCROLL_STATE_IDLE && visibleItemCount>0 && lastVisibleItemPosition==totalItemCount-1){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 对外接口
     */
    public interface IOnSlideToBottomListener {
        void onSeeMore();
    }
}



