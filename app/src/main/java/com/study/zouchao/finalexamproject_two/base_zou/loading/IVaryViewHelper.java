package com.study.zouchao.finalexamproject_two.base_zou.loading;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface IVaryViewHelper {

    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();
}
