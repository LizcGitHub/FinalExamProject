package com.study.zouchao.finalexamproject_two.homepage.model.impl;

import android.content.Context;

import com.study.zouchao.finalexamproject_two.base_zou.cache.AdPicCacheUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/5/18.
 */

public class AdCacheModel {
    public void save2SharePre(Context context, List<String> data) {
        AdPicCacheUtil.saveAdPics(context, data);
    }
    public List<String> listAdPics(Context context) {
        return AdPicCacheUtil.getAdPicsCacheByList(context);
    }
}
