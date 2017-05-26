package com.study.zouchao.finalexamproject_two.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.downloaddata.all.view.activity.AllDownloadActivity;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadedDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.db.DownloadingDAOImpl;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.service.DownloadService;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {
        private static final String BAIDU_MAP_URL = "http://xb.xmut.edu.cn/oa/pdfdow.aspx?Sid=GD201702006";
    private static final String XINLAN_URL = "http://xb.xmut.edu.cn/oa/pdfdow.aspx?Sid=JS201701001";
    private static final String BAIDU_NEWS_URL = "http://xb.xmut.edu.cn/oa/pdfdow.aspx?Sid=JS201701001";
//    private static final String QQ_MUSIC_URL = "xb.xmut.edu.cn/oa/pdfdow.aspx?Sid=CL201609015";
//    private static final String WANGZHERONGYAO_URL = "http://apk.hiapk.com/appdown/com.tencent.tmgp.sgame?planid=3779937&seid=c769df8b-e6b0-0001-6baf-f5d71eda1dc6";
//    private static final String TAOBAO_URL = "xb.xmut.edu.cn/oa/pdfdow.aspx?Sid=CL201609015";


//    private static final String BAIDU_MAP_URL = "http://gdown.baidu.com/data/wisegame/5ea137a5f6da37e5/baiduditu_788.apk";
//    private static final String XINLAN_URL = "http://gdown.baidu.com/data/wisegame/3aa241917510dca9/xinlangtiyu_301000010.apk";
//    private static final String BAIDU_NEWS_URL = "http://gdown.baidu.com/data/wisegame/17d27a5dd506d33a/baiduxinwen_6101.apk";
    private static final String QQ_MUSIC_URL = "http://gdown.baidu.com/data/wisegame/a59e511a47580e0e/QQkongjian_98.apk";
    private static final String WANGZHERONGYAO_URL = "http://apk.hiapk.com/appdown/com.tencent.tmgp.sgame?planid=3779937&seid=c769df8b-e6b0-0001-6baf-f5d71eda1dc6";
    private static final String TAOBAO_URL = "http://download.apk8.com/soft/2015/%E6%B7%98%E5%AE%9D.apk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.id_btn_baiduditu, R.id.id_btn_baiduxinwen, R.id.id_btn_xinlangweibo,
            R.id.id_btn_qq_kongjian, R.id.id_btn_wangzherongyao,
            R.id.id_btn_goto, R.id.id_btn_taobao, R.id.id_btn_clear_all})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_baiduditu :
                addDownload(new FileInfo(0, BAIDU_MAP_URL, " X射线耀发：γ射线暴中心引擎的晚期演化.pdf", 0, 0, 986000L));
                break;
            case R.id.id_btn_baiduxinwen :
                addDownload(new FileInfo(0, BAIDU_NEWS_URL, "基于Landsat 8 OLI遥感影像的融合算法比较.pdf", 0, 0, 4801000L));
                break;
            case R.id.id_btn_xinlangweibo :
                addDownload(new FileInfo(0, XINLAN_URL, "新浪体育.apk", 0, 0, 480100000L));
                break;
            case R.id.id_btn_qq_kongjian :
                addDownload(new FileInfo(0, QQ_MUSIC_URL, "QQ空间.apk", 0, 0, 50010000L));
                break;
            case R.id.id_btn_wangzherongyao :
                addDownload(new FileInfo(0, WANGZHERONGYAO_URL, "王者荣耀.apk", 0, 0, 480100000L));
                break;
            case R.id.id_btn_taobao :
                addDownload(new FileInfo(0, TAOBAO_URL, "淘宝.apk", 0, 0, 480100000L));
                break;
            case R.id.id_btn_clear_all :
                //删除数据库
                DownloadingDAOImpl.deleteDownloadingList(this);
                DownloadedDAOImpl.deleteDownloadedList(this);
                ToastUtils.showShort(this, "Clear All Success");
                break;
            case R.id.id_btn_goto :
                //跳
                startActivity(new Intent(this, AllDownloadActivity.class));
                break;
        }
    }
    private void addDownload(FileInfo fileInfo) {
        //先写入DB 正在下载列表
        DownloadingDAOImpl.addDownloadingTask(this, fileInfo);
        //通知 service开始 下载
        Intent intent = new Intent(this, DownloadService.class);
        intent.setAction(DownloadService.ACTION_START);
        intent.putExtra("fileInfo", fileInfo);
        startService(intent);   //启动service
        ToastUtils.showShort(this, fileInfo.getFileName()+"：已加入下载任务列表");
    }
}
