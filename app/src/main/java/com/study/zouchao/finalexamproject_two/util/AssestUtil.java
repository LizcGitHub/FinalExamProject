package com.study.zouchao.finalexamproject_two.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/12.
 */

public class AssestUtil {
    public static String getTextFileFromAssest(Context context, String filename) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(filename);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            return new String(buffer, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
