package com.study.zouchao.finalexamproject_two.score.contract;

import android.support.v7.widget.RecyclerView;



import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/24.
 */

public interface IScoreContract {
    /**
     * Model
     */
    interface IScoreModel {
        /**
         * 加载当前学年的成绩
         * @param url
         * @param params
         * @param listener
         */
        void connScoreCurrentAdademicYear(String url, Map<String, String> params, IScoreContract.IConnListener listener);

        /**
         * 加载其他学年的成绩
         * @param url
         * @param params
         * @param listener2
         */
        void connScoreOtherAdademicYear(String url, Map<String, String> params, IConnReturnDocListener otherlistener);
    }

    /**
     * 查询成绩Model成功回调接口
     */
    /**
     * 回调接口
     */
    interface IConnListener {
        void onSuccess(Connection.Response response) throws UnsupportedEncodingException;
        void onFailure(Throwable throwable);
    }
    interface IConnReturnDocListener {
        void onSuccess(Document document);
        void onFailure(Throwable throwable);
    }


    /**
     * Presenter
     */
    interface IScorePresenter {
        /**
         * 选择 学年&学期
         * @param year
         * @param term
         */
        void chooseYearAndTerm(String year, String term);

        void onDestroyPresenter();
    }

    /**
     * View
     */
    interface IScoreView {
        //
        void setAdapter(RecyclerView.Adapter adapter);
        //弹出提示框
        void showDialog(String promoteTv);
        //加载动画
        void showLoading(boolean isShow);
    }
}
