package com.study.zouchao.finalexamproject_two.main.view;

import android.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;
import com.study.zouchao.finalexamproject_two.downloaddata.all.view.activity.AllDownloadActivity;
import com.study.zouchao.finalexamproject_two.login.view.LoginActivity;
import com.study.zouchao.finalexamproject_two.pdfview.PdfViewActivity;
import com.study.zouchao.finalexamproject_two.searchbusactivity.SearchBusActivity;
import com.study.zouchao.finalexamproject_two.searchtel.SearchTelActivity;
import com.study.zouchao.finalexamproject_two.searchweather.SearchWeatherModel;
import com.study.zouchao.finalexamproject_two.searchweather.entity.WeatherEntity;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Toolbar mToolbar;
    private LinearLayout mNavHead;
    private ImageView mIvHeadImg;
    private TextView mTvWeather;
    private TextView mTvNickname, mTvEmail;
    private boolean mIsFirstComing = true;              //第一次进入    (另一状态表示返回)    //TODO:可以考虑去除 考虑到：当页面被重新加载时
    private ImageView mIvBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mToolbar.setTitle("明理精工 与时偕行");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));

//        //全透明 沉浸式状态栏
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mIvBg = (ImageView) navigationView.findViewById(R.id.id_iv_head_bg);
        mTvWeather = (TextView) navigationView.findViewById(R.id.id_tv_weather);
        ZouImgLoader.loadImageWithBlur(this, mIvBg, R.drawable.google_sm_, R.drawable.error_pic);
        navigationView.setNavigationItemSelectedListener(this);
        initHeadLayout(navigationView);
        connWeather();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {
            AllDownloadActivity.actionStartAllDownloadActivity(this);
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(getBaseContext(), "请先登陆~~~", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_map) {
            SearchBusActivity.actionSearchBusActivity(this);
        } if (id == R.id.nav_manage) {
            Toast.makeText(getBaseContext(), "请先登陆~~~", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            SearchTelActivity.actionStartSearchTelActivity(this);
        } else if (id == R.id.nav_send) {
            Toast.makeText(getBaseContext(), "敬请期待~~~", Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* 据说是可以 防止 Activity销毁了 Fragment没销毁*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
    private void initHeadLayout(View view) {
        mNavHead = (LinearLayout) view.findViewById (R.id.nav_header);
        mNavHead.setOnClickListener(this);
        mIvHeadImg = (ImageView) mNavHead.findViewById (R.id.iv_head_img);
        mTvNickname = (TextView) mNavHead.findViewById (R.id.tv_nickname);
//        mTvEmail = (TextView) mNavHead.findViewById (R.id.tv_email);
        ZouImgLoader.loadImage(this, mIvHeadImg, R.drawable.headimg_pikaqu, R.drawable.error_pic);
    }
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.nav_header :
                LoginActivity.actionLoginActivity(this);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }
    @Override
    protected void onResume() {                      //在这里面判断是否登陆
        super.onResume();
    }

    public void setLoginedView(String nickname, String email) {
        if ( ! nickname.equals("") )
            mTvNickname.setText(nickname);
        if ( ! email.equals("") )
            mTvEmail.setText(email);
        //TODO:还有个头像 图片 开启线程加载
//        mIsInitViewFinish = true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBusUtils.post(new EventBusEvent(EventBusEvent_C.EVENT_ON_BACK_IN_MAINACTIVITY, "main按下back"));
            return true;           //选择为true表示拦截 //会导致原本按下back会销毁activity但是现在不会了
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    public void connWeather() {
        new SearchWeatherModel().conn()
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        onSuccessGetWeather(response);
                    }
                });
    }

    private void onSuccessGetWeather(Response response) {
        if (response.body() == null)    return;
        try {
            String json =  response.body().string();
            parseWeather(new Gson().fromJson(json, WeatherEntity.class));

        } catch (IOException e) {e.printStackTrace();}
    }

    private void parseWeather(WeatherEntity entity) {
        LogLongUtil.logI("天气", entity.toString());
        List<WeatherEntity.HeWeatherBean>  heWeathers = entity.getHeWeather();
        if (heWeathers.size() < 1)  return;
        WeatherEntity.HeWeatherBean heWeatherBean = heWeathers.get(0);
        if (heWeatherBean.getNow() == null) return;
        WeatherEntity.HeWeatherBean.NowBean nowBean = heWeatherBean.getNow();
        showNowTmp(nowBean.getTmp());
    }

    private void showNowTmp(final String nowTmp) {
        Log.d("现在温度", nowTmp);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvWeather.setText(nowTmp);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
