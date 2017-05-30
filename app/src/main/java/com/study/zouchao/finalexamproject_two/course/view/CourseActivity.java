package com.study.zouchao.finalexamproject_two.course.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.study.zouchao.finalexamproject_three.R;


public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
    }
    public static void actionScoreActivity(Context context, String key, String value) {
        Intent intent = new Intent(context, CourseActivity.class);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

}
