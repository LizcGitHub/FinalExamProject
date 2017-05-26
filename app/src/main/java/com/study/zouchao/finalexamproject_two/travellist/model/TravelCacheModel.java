package com.study.zouchao.finalexamproject_two.travellist.model;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.base_zou.cache.TravelCacheUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class TravelCacheModel {
    public void save2SharePre(Context context, List<TravelItem> data) {
        TravelCacheUtil.saveAdPics(context, data);
    }
    public List<TravelItem> listSchoolPics(Context context) {
        return TravelCacheUtil.listAdPicsCacheByList(context);
    }
}
