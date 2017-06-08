package com.study.zouchao.finalexamproject_two.baidumap;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.zouchao.finalexamproject_three.R;

public class BaiduMapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map);
    }
    public static void actionStartMapActivity(Context context) {
        Intent intent = new Intent(context, BaiduMapActivity.class);
        context.startActivity(intent);
    }
}
