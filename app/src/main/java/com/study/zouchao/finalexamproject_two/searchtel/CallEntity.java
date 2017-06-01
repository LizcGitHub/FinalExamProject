package com.study.zouchao.finalexamproject_two.searchtel;

/**
 * Created by Administrator on 2017/5/21.
 */

public class CallEntity {

    private String name;
    private String tel;

    public CallEntity() {
    }

    public CallEntity(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public CallEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public CallEntity setTel(String tel) {
        this.tel = tel;
        return this;
    }
}
