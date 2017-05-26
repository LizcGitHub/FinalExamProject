package com.study.zouchao.finalexamproject_two.base_zou.cache;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/18.
 */

public class SchoolPicsCacheUtil {
    private static final String TAG = "SchoolPicsCacheUtil";
    private static final String KEY_PICS = "KEY_PICS";
    private static Gson mGson = new Gson();
    /**
     * 保存图片路径
     *  以json形式保存图片list
     */
    public static void saveSchoolPics(Context context, List<String> picsData) {
        if (picsData == null)   return;
        String json = mGson.toJson(picsData);
        Log.i(TAG, json);
        SharedPreUtil.putString(context, KEY_PICS, json);
    }


    /**
     *
     */
    public static void clearSchoolPicsCache(Context context) {
        SharedPreUtil.removeKey(context, KEY_PICS);
    }


    /**
     * 更新：事实上重新put即可
     * @param context
     * @param newData
     */
    public static void updateSchoolPicsCache(Context context, List<String> newData) {
        clearSchoolPicsCache(context);
        saveSchoolPics(context, newData);
    }


    /**
     *
     * @param context
     * @return
     */
    public static List<String> getSchoolPicsCacheByList(Context context) {
        String json = SharedPreUtil.getString(context, KEY_PICS, "");
        return mGson.fromJson(json, new TypeToken<List<String>>(){}.getType());
    }
}
