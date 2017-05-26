package com.study.zouchao.finalexamproject_two.homepage.model.result;

/**
 * Created by Administrator on 2017/2/9.
 */

public class DataResult {
    private DataBean data;
    private String msg;
    private int    code;

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataResult() {
    }

    public DataBean getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public DataResult(DataBean data, String msg, int code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String toString() {
        return "DataResult{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
