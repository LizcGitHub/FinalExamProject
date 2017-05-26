package com.study.zouchao.finalexamproject_two.util;

/**
 * Created by Administrator on 2017/2/15.
 */

public class EventBusEvent {
    private String tag;
    private Object obj;
    private String msg;
    public EventBusEvent(String tag, Object obj) {
        this.tag = tag;
        this.obj = obj;
    }

    public EventBusEvent(String tag, Object obj, String msg) {
        this.tag = tag;
        this.obj = obj;
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "EventBusEvent{" +
                "tag='" + tag + '\'' +
                ", obj=" + obj +
                ", msg='" + msg + '\'' +
                '}';
    }
}
