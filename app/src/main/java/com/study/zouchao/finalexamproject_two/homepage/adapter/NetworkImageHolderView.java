package com.study.zouchao.finalexamproject_two.homepage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;
import com.study.zouchao.finalexamproject_two.homepage.model.result.BannerItem;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;


/**
 * 广告栏的Holder
 * Created by Administrator on 2017/2/9.
 */

public class NetworkImageHolderView implements Holder<BannerItem> {
    private static final String TAG = "HolderView";
    private ImageView iv;
    @Override
    public View createView(Context context) {
        iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        Log.d(TAG, "createView");
        return iv;
    }

    @Override
    public void UpdateUI(final Context context, int position, BannerItem data) {
        Log.d(TAG, "updateUi.."+data);
        ZouImgLoader.loadImage(context, iv, data.getUrl(), R.drawable.error_pic);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(context, "click");
            }
        });
    }
}
