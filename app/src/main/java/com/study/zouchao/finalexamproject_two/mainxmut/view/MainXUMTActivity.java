package com.study.zouchao.finalexamproject_two.mainxmut.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.simple.eventbus.EventBus;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;

public class MainXUMTActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_xmut);
        EventBus.getDefault().register(this);
        //通过eventBus发送出去
        EventBus.getDefault().post(getIntent().getStringExtra(C_XMUT.LOGINED_SUCCESS_HTML), "MainXMUTFragment");
    }
    public static void actionMain(Context context, String key, String value) {
        Intent intent = new Intent(context, MainXUMTActivity.class);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
