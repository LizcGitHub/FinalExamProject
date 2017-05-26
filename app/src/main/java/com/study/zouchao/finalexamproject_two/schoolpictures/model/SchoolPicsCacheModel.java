package com.study.zouchao.finalexamproject_two.schoolpictures.model;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.base_zou.cache.SchoolPicsCacheUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/5/18.
 */

public class SchoolPicsCacheModel {
    public void save2SharePre(Context context, List<String> data) {
        SchoolPicsCacheUtil.saveSchoolPics(context, data);
    }
    public List<String> listSchoolPics(Context context) {
        return SchoolPicsCacheUtil.getSchoolPicsCacheByList(context);
    }
}
