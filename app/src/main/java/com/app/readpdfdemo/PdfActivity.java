package com.app.readpdfdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.MediaController;

import com.artifex.mupdf.MuPDFCore;
import com.artifex.mupdf.MuPDFPageAdapter;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.util.App;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

public class PdfActivity extends AppCompatActivity {
    private static final String KEY_FILE_PATH = "KEY_FILE_PATH";
    private String mAbsoultePath = null;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pdf);
        mListView = (ListView) findViewById(R.id.listview_main);
        init();
    }

    private void init() {
        setFilePath();
        if (StringUtils.isEmpty(mAbsoultePath)) {
            ToastUtils.showShort(this, "文件路径找不到");
            return;
        }
//        String path = Environment.getExternalStorageDirectory()
//                .getAbsolutePath()+"/test.pdf";
        MuPDFCore core;
        try{
            core = new MuPDFCore(this, mAbsoultePath);
            MuPDFPageAdapter adapter = new MuPDFPageAdapter(this, core);
            mListView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShort(App.getAppContext(), "pdf文件打开失败");
            deleteCurFileFromDb();
            /**
                 * TODO:"假如点击后文件打开失败->\n可能用户从文件管理器等手动删除的情况->\n弹出提示框 让用户重新下载" 同时 删除数据库中已下载表中当前的fileInfo
                 */
            ToastUtils.showShort(App.getAppContext(), "假如点击后文件打开失败->\n可能用户从文件管理器等手动删除的情况->\n让用户重新下载");
        }
    }

    //TODO:发条广播 删除数据库数据
    private void deleteCurFileFromDb() {

    }

    private void setFilePath() {
        mAbsoultePath = getIntent().getStringExtra(KEY_FILE_PATH);
    }

    public static void actionStartPdfActivity(Context context, String pdfFilePath) {
        Intent intent = new Intent(context, PdfActivity.class);
        intent.putExtra(KEY_FILE_PATH, pdfFilePath);
        context.startActivity(intent);
    }
}
