package com.study.zouchao.finalexamproject_two.base_zou.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/1/19.
 */

public class VaryViewHelper implements IVaryViewHelper {
    private View view;
    private ViewGroup parentView;
    private ViewGroup.LayoutParams params;
    private int viewIndex;
    private View currentView;
    private Context mContext;

    public VaryViewHelper(View view, Context context) {
        super();
        this.view = view;
        mContext = context;
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        //把targetView再显示出来(也就是隐藏Loading动画)
        showLayout(view);
    }

    @Override
    public void showLayout(View view) {
        if (parentView == null) {
            init();
        }
        this.currentView = view;
        // 如果已经是那个view，那就不需要再进行替换操作了
        if (parentView.getChildAt(viewIndex) != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            parentView.removeViewAt(viewIndex);
            parentView.addView(view, viewIndex, params);
        }
    }

    private void init() {
        params = view.getLayoutParams();
        if (view.getParent() != null) {
            parentView = (ViewGroup) view.getParent();
        } else {
            parentView = (ViewGroup) view.getRootView().findViewById(android.R.id.content);
        }
        int count = parentView.getChildCount();
        for (int index = 0; index < count; index++) {
            if (view == parentView.getChildAt(index)) {
                viewIndex = index;
                break;
            }
        }
        currentView = view;
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null, false);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public View getView() {
        return view;
    }
}
