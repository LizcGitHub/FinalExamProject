package com.study.zouchao.finalexamproject_two.pdfview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.util.FileUtil;


public class PdfViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
    }

    public static void actionStartPdfViewActivity(Context context, String filePath) {
        Intent intent = new Intent(context, PdfViewActivity.class);
        intent.putExtra(PdfFragment.KEY_FILE_PATH, filePath);
        context.startActivity(intent);
    }

    public static void actionStartPdfViewActivity(Context context, String parentDirName, String subDirName, String fileName) {
        Intent intent = new Intent(context, PdfViewActivity.class);
        intent.putExtra(PdfFragment.KEY_FILE_PATH, getTargetFilePath(parentDirName, subDirName, fileName));
        context.startActivity(intent);
    }

    private static String getTargetFilePath(String parentDirName, String subDirName, String fileName) {
        String targetPath = FileUtil.getFilePath(parentDirName, subDirName, fileName);
        Log.d("pdf路径找得到吗>>>", targetPath);
        return targetPath;
    }
}
