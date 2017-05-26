package com.study.zouchao.finalexamproject_two.homepage.model.impl;

import android.util.Log;

import com.study.zouchao.finalexamproject_two.homepage.model.entity.DataBean;
import com.study.zouchao.finalexamproject_two.util.C;
import com.study.zouchao.finalexamproject_two.util.ParseUtil;
import com.study.zouchao.finalexamproject_two.util.RegexUtils;
import com.study.zouchao.finalexamproject_two.util.RxSchedulers;
import com.study.zouchao.finalexamproject_two.util.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/5/17.
 */

public class CourseDataConnModel {
    private static final String TAG = "DataA";
    private List<String> mNewsUrls;
    private LinkedHashMap<String, List<DataBean>> mData;
    private String mCurDepart;
    private IGetCourseDataListener mListener;

    public void connCourseData(IGetCourseDataListener listener) {
        mNewsUrls = new ArrayList<>();
        mData = new LinkedHashMap<>();
        mListener = listener;
        start();
    }
    private void start() {
        try {
            conn();
        } catch (IOException e) {
            e.printStackTrace();
            onFailureCallback(e, "连接失败");
        }
    }

    private void conn() throws IOException {
        Observable.just("http://xb.xmut.edu.cn/")
                .map(new Func1<String, Document>() {
                    @Override
                    public Document call(String url) {
                        try {
                            return Jsoup.connect(url)
                                    .timeout(C.CONN_TIME_OUT)
                                    .get();
                        } catch (IOException e) {
                            e.printStackTrace();
//                            onFailureCallback(e, "获取不到数据 文件下载");
                            return null;
                        }
                    }
                })
//                .map(RxSchedulers.<Document>sleep(2000))

                .compose(RxSchedulers.<Document>io_main())
                .subscribe(new Action1<Document>() {
                    @Override
                    public void call(Document doc) {
//                        LogLongUtil.logI("更多图片html", doc.toString());
                        parseToSeeMorePic(doc);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        onFailureCallback(throwable, "获取数据失败");
                    }
                });

    }


    private void parseToSeeMorePic(Document doc) {
        if (doc == null) {
            onFailureCallback(null, "获取不到数据 文件下载");
            return;
        }
        Element eleContent = doc.select("#Wmydatalist1_divlist").get(0);
        Elements elesDivs = eleContent.getElementsByTag("div");
        for (Element eleDiv : elesDivs) {
            if (eleDiv != null) {
                Elements eleDivDatas = eleDiv.select("#column_mcbg");
                Elements elesItems = eleDiv.select(".item");
                if (eleDivDatas.size() > 0) {
                    Log.i("嗯。。datasize>0", eleDivDatas.size()+"");
                    parseToDepartmentName(eleDivDatas.get(0));
                } else if (elesItems.size() > 0){
                    parseToDataItem(elesItems.get(0));
                } else  {}
            } else {
                Log.i("解析更多图片", "某个img元素不存在");
            }
        }
        mCurDepart = null;
//        printMap("输出Map", mData);
        onSuccessCallback(mData);
    }

    private void parseToDepartmentName(Element eleDepartment) {
//        LogLongUtil.logD("嗯。。", "是学院名>>>>" + eleDepartment.select("#column_mcbg").toString());
        Elements elesH5 = eleDepartment.select("h5");
        if (elesH5.size() <= 0) return;
        Element eleH5 = elesH5.get(0);
        String departmentName = eleH5.html();
        if (!StringUtils.isEmpty(departmentName)) {
            if (!mData.containsKey(departmentName)) {
                List<DataBean> departmentData = new ArrayList<>();
                mData.put(departmentName, departmentData);
                mCurDepart = departmentName;
            }
        }
    }

    private void parseToDataItem(Element eleItem) {
        DataBean dataBean = new DataBean();
        /*
         * 解析digest
         */
        Element eleDigestDiv = ParseUtil.parseElementByCssSelect(eleItem, ".digest", 0);
        if (eleDigestDiv == null)               return;
        Element eleADownloadUrl = ParseUtil.parseElementByCssSelect(eleDigestDiv, "a", 1);
        if (eleADownloadUrl == null)            return;
        long totalSize = getDataTotalSize(eleADownloadUrl);
        String downloadRelativeUrl = ParseUtil.parseValueByAttrKey(eleADownloadUrl, "href");
        if (StringUtils.isEmpty(downloadRelativeUrl) || StringUtils.isEmpty(mCurDepart))   return;
        List<DataBean> curDepartDataList = mData.get(mCurDepart);
        if (curDepartDataList==null)            return;
        String absoulteUrl = getDownloadAbsoulteUrlByRelativeUrl(downloadRelativeUrl);
        if (StringUtils.isEmpty(absoulteUrl))   return;

        dataBean.dataDownloadUrl = absoulteUrl;
        dataBean.totalSize = totalSize;
        dataBean.department = mCurDepart;
        /*
         * 解析 title
         */
        Element eleTitleDiv = ParseUtil.parseElementByCssSelect(eleItem, ".title", 0);
        if (eleTitleDiv != null) {
            Element eleATitle = ParseUtil.parseElementByCssSelect(eleTitleDiv, "a", 0);
            if (eleATitle != null) {
                dataBean.title = validateTitle(eleATitle.html())+".pdf";
            }
        }
        curDepartDataList.add(dataBean);
//        Log.d("嗯。。刚添加的路径", absoulteUrl);
    }

    /**
     * 去掉title的 &nbsp; <br>
     * @param title
     * @return
     */
    private String validateTitle(String title) {
        String res = "";
        res = RegexUtils.removeSpaceHtml(title);
        res = RegexUtils.removeBrHtml(res);
        return res;
    }


    private long getDataTotalSize(Element eleA) {
        Element eleSpan = ParseUtil.parseElementByCssSelect(eleA, "span", 0);
        if (eleSpan == null)    return 0L;
        Log.i("嗯。。内部html", eleSpan.html());
        String spanHtml = eleSpan.html();
        if (StringUtils.isEmpty(spanHtml))  return 0L;
        Matcher matcher = RegexUtils.regexMatcher(spanHtml, "(?<=PDF:).*(?=KB)");
        if(matcher.find()) {
            return Long.valueOf(matcher.group()) * 1000;   //单位：B(Byte)
        }
        return 0L;
    }

    private String getDownloadAbsoulteUrlByRelativeUrl(String relativeUrl) {
        if (StringUtils.isEmpty(relativeUrl))   return null;
        return "http://xb.xmut.edu.cn".concat(relativeUrl);
    }

    private void printMap(String tag, Map hashMap) {
        Set<String> keys = hashMap.keySet();
        for (String key : keys) {
//            LogLongUtil.logD(tag+"..."+"K.."+key, "V.."+hashMap.get(key));
        }
    }


    private void onFailureCallback(Throwable throwable, String msg) {
        if (mListener != null) {
            mListener.onFailure(throwable, msg);
        }
    }

    private void onSuccessCallback(LinkedHashMap<String, List<DataBean>> data) {
        if (mListener != null) {
            mListener.onSuccess(data);
        }
    }


    public interface IGetCourseDataListener {
        void onSuccess(LinkedHashMap<String, List<DataBean>> data);
        void onFailure(Throwable throwable, String msg);
    }
}
