package com.study.zouchao.finalexamproject_two.searchtel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.zouchao.finalexamproject_three.R;

public class SearchTelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tel);
    }

    public static void actionStartSearchTelActivity(Context context) {
        context.startActivity(new Intent(context, SearchTelActivity.class));
    }
}
