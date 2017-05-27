package com.study.zouchao.finalexamproject_two.traveldetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.MyBaseActivity;

/**
 * Created by Administrator on 2017/5/26.
 */

public class TravelDetailActivity extends MyBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
    }

    public static final String KEY_URL = "URL";
    public static final String KEY_BG_IMG = "BG_IMG";
    public static final String KEY_TITLE = "TITLE";
    public static void actionStartTravelDetailActivity(Context context, String title, String bgImg, String url) {
        Intent intent = new Intent(context, TravelDetailActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        intent.putExtra(KEY_BG_IMG, bgImg);
        context.startActivity(intent);
    }
}
