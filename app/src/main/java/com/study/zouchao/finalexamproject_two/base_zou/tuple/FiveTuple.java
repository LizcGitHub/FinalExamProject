package com.study.zouchao.finalexamproject_two.base_zou.tuple;

/**
 * Created by Administrator on 2017/5/29.
 */

public class FiveTuple<A, B, C, D, E> {
    public final A aa;
    public final B bb;
    public final C cc;
    public final D dd;
    public final E ee;

    public FiveTuple(A a, B b, C c, D d, E e) {
        aa = a;
        bb = b;
        cc = c;
        dd = d;
        ee = e;
    }

    @Override
    public String toString() {
        return "FiveTuple{" +
                "aa=" + aa +
                ", bb=" + bb +
                ", cc=" + cc +
                ", dd=" + dd +
                ", ee=" + ee +
                '}';
    }
}
