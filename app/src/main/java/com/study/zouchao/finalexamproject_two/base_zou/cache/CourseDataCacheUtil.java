package com.study.zouchao.finalexamproject_two.base_zou.cache;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/5/18.
 */

public class CourseDataCacheUtil {
    private static final String TAG = "CourseDataCacheUtil";
    private static final String KEY_COURSE_DATA = "EY_COURSE_DATA";
    private static Gson mGson = new Gson();

    
    public static void saveCourseData(Context context, LinkedHashMap<String, List<FileInfo>> data) {
        if (data == null)   return;
        String json = mGson.toJson(data);
        Log.i(TAG, json);
        SharedPreUtil.putString(context, KEY_COURSE_DATA, json);
    }


    public static void clearCourseData(Context context) {
        SharedPreUtil.removeKey(context, KEY_COURSE_DATA);
    }


    public static void updateCourseCache(Context context, LinkedHashMap<String, List<FileInfo>> data) {
        clearCourseData(context);
        saveCourseData(context, data);
    }


    public static LinkedHashMap<String, List<FileInfo>> getCourseCacheByMap(Context context) {
        String json = SharedPreUtil.getString(context, KEY_COURSE_DATA, "");
        return mGson.fromJson(json, new TypeToken<LinkedHashMap<String, List<FileInfo>>>(){}.getType());
    }
}
