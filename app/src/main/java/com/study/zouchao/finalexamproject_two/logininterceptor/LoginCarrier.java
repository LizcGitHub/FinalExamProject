package com.study.zouchao.finalexamproject_two.logininterceptor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用于临时保存  登陆跳转信息的 bean类
 */

public class LoginCarrier implements Parcelable {
    //登陆后需要跳转的activity  为activity的完整路径名
    private String mTargetActivity;
    //跳转后需要的数据
    private Bundle mTargetBundle;

    public LoginCarrier(String target, Bundle bundle) {
        mTargetActivity = target;
        mTargetBundle = bundle;
    }
    /**
     *  在登陆页面登陆成功后调用的方法
     *      跳转到登陆之后的跳转页面
     *   ctx: 登陆界面之前的界面
     */
    public void loginSuccessAndInvoke(Context context) {
        //需要跳转的activity
        Intent intent = new Intent();
        //跳转到目标activity
        intent.setAction(mTargetActivity);
        if (mTargetBundle != null) {
            //带参数的
            intent.putExtras(mTargetBundle);
        }
        //与singleTop类似 若目标已经是栈顶则不new 否则new
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //添加了此标记 并且 目标activity已经存在 则会将目标activity上的activity全部销毁 然后将目标activity置于栈顶(显示出来)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //已经登陆了！！跳跳跳跳！！！
        context.startActivity(intent);
    }

    public LoginCarrier(Parcel parcel) {
        // 按变量定义的顺序读取
        mTargetActivity = parcel.readString();
        mTargetBundle = parcel.readParcelable(Bundle.class.getClassLoader());
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // 按变量定义的顺序写入
        parcel.writeString(mTargetActivity);
        parcel.writeParcelable(mTargetBundle, flags);
    }
    public static final Creator<LoginCarrier> CREATOR = new Creator<LoginCarrier>() {
        @Override
        public LoginCarrier createFromParcel(Parcel source) {return new LoginCarrier(source);}
        @Override
        public LoginCarrier[] newArray(int arg0) {return new LoginCarrier[arg0];}
    };
}
