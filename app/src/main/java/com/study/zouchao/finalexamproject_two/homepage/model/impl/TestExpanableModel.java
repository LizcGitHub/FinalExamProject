package com.study.zouchao.finalexamproject_two.homepage.model.impl;


import com.study.zouchao.finalexamproject_two.downloaddata.entity.FileInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Administrator on 2017/2/9.
 */

public class TestExpanableModel {
    private static final String BAIDU_MAP_URL = "http://gdown.baidu.com/data/wisegame/5ea137a5f6da37e5/baiduditu_788.apk";
    private static final String XINLAN_URL = "http://gdown.baidu.com/data/wisegame/3aa241917510dca9/xinlangtiyu_301000010.apk";
    private static final String BAIDU_NEWS_URL = "http://gdown.baidu.com/data/wisegame/17d27a5dd506d33a/baiduxinwen_6101.apk";
    private static final String QQ_MUSIC_URL = "http://gdown.baidu.com/data/wisegame/a59e511a47580e0e/QQkongjian_98.apk";
    private static final String WANGZHERONGYAO_URL = "http://apk.hiapk.com/appdown/com.tencent.tmgp.sgame?planid=3779937&seid=c769df8b-e6b0-0001-6baf-f5d71eda1dc6";
    private static final String TAOBAO_URL = "http://download.apk8.com/soft/2015/%E6%B7%98%E5%AE%9D.apk";
    public TestMapBean getTestMap() {
        return new TestMapBean();
    }

    public class TestMapBean {
        public List<String> parent;
        public List<FileInfo> child;
        public Map<String, List<FileInfo>> map;

        public TestMapBean() {
            map = new HashMap<>();
            parent = new ArrayList<>();
            child = new ArrayList<>();

            parent = new ArrayList<>();
            parent.add("Java编程基础");
            parent.add("Android从入门到精通");
            parent.add("C语言入门");

            map = new HashMap<>();

            List<FileInfo> list1 = new ArrayList<>();
            list1.add(new FileInfo(0, "", "不可用", 0, 0));
            list1.add(new FileInfo(0, "", "不可用", 0, 0));
            list1.add(new FileInfo(4, WANGZHERONGYAO_URL, "王者荣耀.apk", 0, 0));
            map.put("Java编程基础", list1);

            List<FileInfo> list2 = new ArrayList<>();
            list2.add(new FileInfo(0, "", "不可用", 0, 0));
            list2.add(new FileInfo(0, BAIDU_MAP_URL, "百度地图.apk", 0, 0));
            list2.add(new FileInfo(2, BAIDU_NEWS_URL, "百度新闻.apk", 0, 0));
            map.put("Android从入门到精通", list2);

            List<FileInfo> list3 = new ArrayList<>();
            list3.add(new FileInfo(1, XINLAN_URL, "新浪体育.apk", 0, 0));
            list3.add(new FileInfo(3, QQ_MUSIC_URL, "QQ空间.apk", 0, 0));
            list3.add(new FileInfo(5, TAOBAO_URL, "淘宝.apk", 0, 0));
            map.put("C语言入门", list3);
        }
    }
}
