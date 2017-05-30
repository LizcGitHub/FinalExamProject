package com.study.zouchao.finalexamproject_two.base_zou.tuple;

/**
 * Created by Administrator on 2017/5/29.
 */

public class FourTuple<A, B, C, D> {
    public final A a;
    public final B b;
    public final C c;
    public final D d;

    public FourTuple(A aa, B bb, C cc, D dd) {
        a = aa;
        b = bb;
        c = cc;
        d = dd;
    }

    @Override
    public String toString() {
        return "FourTuple{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                '}';
    }
}
