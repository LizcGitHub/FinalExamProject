package com.study.zouchao.finalexamproject_two.base_zou.loading;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface ILoadingDelegate {
    /**
     * show my_loading message
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide my_loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(String msg);

    /**
     * show exception message
     */
    void showException(String msg);

    /**
     * show net error
     */
    void showNetError();

    /**
     * show empty
     */
    void showEmpty();
}
