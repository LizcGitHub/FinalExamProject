package com.study.zouchao.finalexamproject_two.homepage.model.impl;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.base_zou.cache.CourseDataCacheUtil;
import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class CourseDataCacheModel {
    public void save2SharePre(Context context, LinkedHashMap<String, List<FileInfo>> data) {
        CourseDataCacheUtil.saveCourseData(context, data);
    }
    public LinkedHashMap<String, List<FileInfo>> listCoureseDataByMap(Context context) {
        return CourseDataCacheUtil.getCourseCacheByMap(context);
    }
}
