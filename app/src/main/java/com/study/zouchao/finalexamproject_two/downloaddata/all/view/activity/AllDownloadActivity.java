package com.study.zouchao.finalexamproject_two.downloaddata.all.view.activity;

import android.content.Context;
import android.content.Intent;
import android.media.DrmInitData;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.format.Formatter;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.all.view.adapter.MainFragmentAdapter;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadedDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadingDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.downloaded.view.DownloadedFragment;
import com.study.zouchao.finalexamproject_two.downloaddata.downloading.view.fragment.DownloadingFragment;
import com.study.zouchao.finalexamproject_two.util.App;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent;
import com.study.zouchao.finalexamproject_two.util.EventBusEvent_C;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AllDownloadActivity extends AppCompatActivity {
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.id_tv_finish)
    TextView mTvFinish;
    @BindView(R.id.btn_left)
    CardView mBtnLeft;
    @BindView(R.id.btn_right)
    CardView mBtnRight;
    @BindView(R.id.id_tv_left)
    TextView mTvLeft;
    @BindView(R.id.id_tv_right)
    TextView mTvRight;
    private MainFragmentAdapter mAdapter;
    private Fragment mFragmentDownloading, mFragmentDownloaded;
    private Display display = null;  //获取屏幕
    public static int screen_width = 0;
    /*得到容量大小*/
    private File path = null;
    private StatFs statFs = null;      //用于查询文件系统的相关信息
    private long blockSize = 0;
    private long totalBlocks = 0;
    private long availableBlocks = 0;
    private String totalInfo = null;
    private String availableInfo = null;
    /*容量条*/
    private TextView leftWeight = null;
    private TextView rightWeight = null;
    private TextView tvRom = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_download);
        ButterKnife.bind(this);
        init();
    }
    private void init() {

        mViewPager.setOffscreenPageLimit(4);		//这个很重要,设置4个界面来回切换不会重新加载
        List<Fragment> data = new ArrayList<>();

        mFragmentDownloading = new DownloadingFragment();
        mFragmentDownloaded = new DownloadedFragment();

        data.add(mFragmentDownloading);
        data.add(mFragmentDownloaded);

        mAdapter = new MainFragmentAdapter(getSupportFragmentManager(), data);
        mViewPager.setAdapter(mAdapter); 								// 应用适配器
        mViewPager.setOnPageChangeListener(pageChangeListener); 		// 监听界面滑动

        getRomSize();
        initRomView();
//        initViewPager();
    }


    private void getRomSize(){   //得到机身内存容量大小
        path = Environment.getDataDirectory();
        statFs = new StatFs(path.getPath());
        blockSize = statFs.getBlockSize();
        totalBlocks = statFs.getBlockCount();
        availableBlocks = statFs.getAvailableBlocks();
        totalInfo = Formatter.formatFileSize(this, blockSize * totalBlocks);
        availableInfo = Formatter.formatFileSize(this, blockSize * availableBlocks);
    }

    private void initRomView(){
        /*容量条*/
        leftWeight = (TextView) findViewById (R.id.left_weight);
        rightWeight = (TextView) findViewById (R.id.right_weight);
        tvRom = (TextView) findViewById(R.id.tv_rom);
        tvRom.setText("总空间：" + totalInfo + "/可用空间：" + availableInfo);
        /*屏幕*/
        display = getWindowManager().getDefaultDisplay();
        screen_width = display.getWidth();
        float weight = (float) availableBlocks / (float) totalBlocks;
        int weightInfo = (int) (screen_width * weight);
        int num = screen_width - weightInfo;
        leftWeight.setWidth(num);
        rightWeight.setWidth(weightInfo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusEvent event) {
        switch (event.getTag()) {
            case EventBusEvent_C.EVENT_UPDATE_ROM_UI :
                updateRomInfo();
                break;
        }
    }

    private void updateRomInfo() {
        getRomSize();
        initRomView();
        ToastUtils.showShort(this, "更新ROM大小");
    }


    @OnClick({R.id.id_tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tv_finish :
                clearAll();
                break;
        }
    }

    private void clearAll() {
        //删除数据库
        DownloadingDAOImpl.deleteDownloadingList(this);
        DownloadedDAOImpl.deleteDownloadedList(this);
        ToastUtils.showShort(App.getAppContext(), "Clear All Success");
    }

    /**
     * 滑动监听
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            showSelectedUi(index);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void showSelectedUi(int index) {
        if (index == 0) {
            mTvLeft.setTextColor(getResources().getColor(R.color.colorAccent));
            mTvRight.setTextColor(getResources().getColor(R.color.text_color_gray));
        }
        if (index == 1){

            mTvRight.setTextColor(getResources().getColor(R.color.colorAccent));
            mTvLeft.setTextColor(getResources().getColor(R.color.text_color_gray));
        }
    }

    public static void actionStartAllDownloadActivity(Context context) {
        Intent intent = new Intent(context, AllDownloadActivity.class);
        context.startActivity(intent);
    }
}
