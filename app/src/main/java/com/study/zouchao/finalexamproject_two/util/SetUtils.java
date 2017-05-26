package com.study.zouchao.finalexamproject_two.util;

import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/5/19.
 */

public class SetUtils {
    public static <E> void  traverseListWithIndex(String tag, List<E> list) {
        for (int i = 0; i < list.size(); i++) {
            Log.i(tag, "index:"+i+"..."+"E:"+list.get(i));
        }
    }

    public static <E> void  traverseList(String tag, List<E> list) {
        for (E ele : list) {
            Log.i(tag, "E:"+ele);
        }
    }

    public static <K, V> void traverseMap(String tag, Map<K, V> map) {
        Set<K> keySet = map.keySet();
        for (K key : keySet) {
            V val = map.get(key);
            Log.i(tag, "K:"+key+"..."+"V:"+val);
        }
    }
}
