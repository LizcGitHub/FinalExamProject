package com.study.zouchao.finalexamproject_two.mainxmut.presenter;

import android.content.Context;
import android.util.Log;


import com.study.zouchao.finalexamproject_two.login.loginStatus.StuInfo;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.mainxmut.contract.MainContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2016/12/9.
 */

public class MainPresenter implements MainContract.IMainPresenter {
    private Context mContext;
    private MainContract.IMainView mView;
    public MainPresenter(Context context, MainContract.IMainView view) {
        mContext = context;
        mView = view;
    }
    /**
     * 解析出 查课表和查成绩 的url
     * @param html
     */
    @Override
    public void parseHTML(String html) {
        Log.d("得到的html", html);
        Document doc = Jsoup.parse(html);
        //得到登陆的真实姓名
        String realName = doc.getElementById("xhxm").html();
        mView.setTitle(realName);
        StuInfo.stuName = realName;
        //得到成绩和课表的url
        Elements elesA = doc.getElementsByTag("a");
        for (Element eleA : elesA) {
            if (eleA.html().equals("个人成绩查询")) {
                C_XMUT.SCORE_URL = C_XMUT.BASE_URL + eleA.attr("href");
                Log.d("score_url", C_XMUT.SCORE_URL);
            }
            if (eleA.html().equals("个人课表查询")) {
                C_XMUT.COURSE_URL = C_XMUT.BASE_URL + eleA.attr("href");
                Log.d("course_url", C_XMUT.COURSE_URL);
            }
        }
        //访问成绩页面
        connScorePage();
    }
    private void connScorePage() {

    }

    @Override
    public void onDestroyPresenter() {

    }
}
    