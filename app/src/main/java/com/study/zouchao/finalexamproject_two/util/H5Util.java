package com.study.zouchao.finalexamproject_two.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2017/5/27.
 */

public class H5Util {
    /**
     *  图片跟随手机自适应
     * @param elesimgs
     */
    public static void imgAutoScale(Document doc) {
        Elements elesimgs = doc.select("img");
        for (Element eleImg : elesimgs) {
            eleImg.attr("width", "100%")
                    .attr("height", "auto");
        }
    }

    /**
     * 去掉所有<a>链接
     */
    public static void removeAllHref(Document doc) {
        Elements elesA = doc.select("a");
        for (Element eleA : elesA) {
            if (eleA.hasAttr("href")) {
                eleA.removeAttr("href");
            }
        }
    }
}
