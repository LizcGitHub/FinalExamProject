package com.study.zouchao.finalexamproject_two.score.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.study.zouchao.finalexamproject_three.R;


public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    /**
     * 用于启动当前Activity
     * @param context
     * @param key
     * @param value
     */
    public static void actionScoreActivity(Context context, String key, String value) {
        Intent intent = new Intent(context, ScoreActivity.class);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }
}
