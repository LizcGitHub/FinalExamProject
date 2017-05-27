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

    public static final String URL = "URL";
    public static void actionStartTravelDetailActivity(Context context, String url) {
        Intent intent = new Intent(context, TravelDetailActivity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }
}
