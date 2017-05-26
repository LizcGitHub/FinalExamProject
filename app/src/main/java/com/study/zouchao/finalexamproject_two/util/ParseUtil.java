package com.study.zouchao.finalexamproject_two.util;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2017/5/13.
 */

public class ParseUtil {
    public static Element parseElementByCssSelect(Element element, String cssSelect, int index) {
        Elements eles = element.select(cssSelect);
        if (eles.size()<0 || eles.size()<=index)    return null;
        return eles.get(index);
    }

    public static String parseValueByAttrKey(Element element, String attrKey) {
        return element.attr(attrKey);
    }
}
