package com.study.zouchao.finalexamproject_two.base_zou.cache;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.zouchao.finalexamproject_two.travellist.model.TravelItem;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/5/18.
 */

public class TravelCacheUtil {
    private static final String TAG = "TravelCacheUtil";
    private static final String KEY_TRAVEL_ITEM = "KEY_TRAVEL_ITEM";
    private static Gson mGson = new Gson();
    /**
     * 保存图片路径
     *  以json形式保存图片list
     */
    public static void saveAdPics(Context context, List<TravelItem> picsData) {
        if (picsData == null)   return;
        String json = mGson.toJson(picsData);
        Log.i(TAG, json);
        SharedPreUtil.putString(context, KEY_TRAVEL_ITEM, json);
    }


    /**
     *
     */
    public static void clearAdPicsCache(Context context) {
        SharedPreUtil.removeKey(context, KEY_TRAVEL_ITEM);
    }


    /**
     * 更新：事实上重新put即可
     * @param context
     * @param newData
     */
    public static void updateAdPicsCache(Context context, List<TravelItem> newData) {
        clearAdPicsCache(context);
        saveAdPics(context, newData);
    }


    /**
     *
     * @param context
     * @return
     */
    public static List<TravelItem> listAdPicsCacheByList(Context context) {
        String json = SharedPreUtil.getString(context, KEY_TRAVEL_ITEM, "");
        return mGson.fromJson(json, new TypeToken<List<TravelItem>>(){}.getType());
    }
}
