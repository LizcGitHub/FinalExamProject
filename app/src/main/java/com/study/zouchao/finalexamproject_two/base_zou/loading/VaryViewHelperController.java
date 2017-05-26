package com.study.zouchao.finalexamproject_two.base_zou.loading;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

/**
 * Created by Administrator on 2017/1/19.
 */

public class VaryViewHelperController {

    private IVaryViewHelper helper;
    private Context mContext;

    private Context context;
    public VaryViewHelperController(View view, Context context) {
        this(new VaryViewHelper(view, context));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    /**
     * 真正显示Loading动画
     * @param msg
     */
    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.layout_loading);
        if (!StringUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.id_tv_loading);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    /**
     * 真正取消Loadng动画
     */
    public void restore() {
        helper.restoreView();
    }
}
