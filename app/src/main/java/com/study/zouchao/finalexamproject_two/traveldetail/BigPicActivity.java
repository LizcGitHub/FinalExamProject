package com.study.zouchao.finalexamproject_two.traveldetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;
import com.wingsofts.dragphotoview.DragPhotoView;

import uk.co.senab.photoview.PhotoView;

public class BigPicActivity extends AppCompatActivity {
    PhotoView mView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);
        mView = (PhotoView) findViewById(R.id.id_iv);

        ZouImgLoader.loadImage(this, mView, getIntent().getStringExtra("IMG"), R.drawable.error_pic);
    }


    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, BigPicActivity.class);
        intent.putExtra("IMG", url);
        context.startActivity(intent);
    }
}
