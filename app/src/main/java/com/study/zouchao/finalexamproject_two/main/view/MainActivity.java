package com.study.zouchao.finalexamproject_two.main.view;

import android.app.Fragment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.all.view.activity.AllDownloadActivity;
import com.study.zouchao.finalexamproject_two.login.view.LoginActivity;
import com.study.zouchao.finalexamproject_two.pdfview.PdfViewActivity;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.EventBusUtils;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Toolbar mToolbar;
    private LinearLayout mNavHead;
    private ImageView mIvHeadImg;
    private TextView mTvNickname, mTvEmail;
    private boolean mIsFirstComing = true;              //第一次进入    (另一状态表示返回)    //TODO:可以考虑去除 考虑到：当页面被重新加载时
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
        navigationView.setNavigationItemSelectedListener(this);
        initHeadLayout(navigationView);
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
        if (id == R.id.nav_camera) {
            replaceFragment(0);
        } else if (id == R.id.nav_gallery) {
            AllDownloadActivity.actionStartAllDownloadActivity(this);
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(getBaseContext(), "敬请期待~~~", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            replaceFragment(3);
        } else if (id == R.id.nav_share) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(int fragmentIndex) {
        Fragment fragment = null;
//        if (fragmentIndex == 0)
//            fragment = new HomePageFragment();
//        if (fragmentIndex == 3)
//            fragment = new AboutUsFragment();
//        android.app.FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();   //提交事务
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
