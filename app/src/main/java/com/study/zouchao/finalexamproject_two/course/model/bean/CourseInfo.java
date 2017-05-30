package com.study.zouchao.finalexamproject_two.course.model.bean;

import android.util.Log;

/**
 * 课程信息
 *      用于显示
 */

public class CourseInfo {
    private static final String TAG = "CourseInfo";
    /**
     * 课程名
     */
    private String mCourseName;    //...........

    /**
     * 今天是星期几
     */
    private int mWeekNum;         //..................

    /**
     * 第几节开始
     */
    private int mStartNum;      //................

    /**
     * 一共需要上几节
     */
    private int mStepNum;        //..............

    /**
     * 第几周开始
     */
    private int mStartWeek;     //..........

    /**
     * 第几周结束
     */
    private int mEndWeek;        //............

    /**
     * 任课教师
     */
    private String mTech;         //..........

    /**
     * 上课教室
     */
    private String mClassRoom;  //............

    /**
     * 是否有单双周限制
     *      0..没有
     *      1..单周
     *      2..双周
     */
    private int mSingleOrDoubleWeekMode = 0;   //.........

    public CourseInfo() {}

    public CourseInfo(String couseName, int weekNum, int startNum, int stepNum, int startWeek, int endWeek, String tech, String classRoom) {
        this.mCourseName = couseName;
        this.mWeekNum = weekNum;
        this.mStartNum = startNum;
        this.mStepNum = stepNum;
        this.mStartWeek = startWeek;
        this.mEndWeek = endWeek;
        this.mTech = tech;
        this.mClassRoom = classRoom;
    }

    public CourseInfo setCourseName(String mCourseName) {
        this.mCourseName = mCourseName;
        return this;
    }

    public CourseInfo setWeekNum(int mWeekNum) {
        this.mWeekNum = mWeekNum;
        return this;
    }

    public CourseInfo setStartNum(int mStartNum) {
        this.mStartNum = mStartNum;
        return this;
    }

    public CourseInfo setStartWeek(int mStartWeek) {
        this.mStartWeek = mStartWeek;
        return this;
    }

    public CourseInfo setEndWeek(int mEndWeek) {
        this.mEndWeek = mEndWeek;
        return this;
    }

    public CourseInfo setStepNum(int mStepNum) {
        this.mStepNum = mStepNum;
        return this;
    }

    public CourseInfo setTech(String mTech) {
        this.mTech = mTech;
        return this;
    }

    public CourseInfo setClassRoom(String mClassRoom) {
        this.mClassRoom = mClassRoom;
        return this;
    }

    public CourseInfo setSingleOrDoubleWeekMode(int mSingleOrDoubleWeekMode) {
        this.mSingleOrDoubleWeekMode = mSingleOrDoubleWeekMode;
        return this;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public int getWeekNum() {
        return mWeekNum;
    }

    public int getStartNum() {
        return mStartNum;
    }

    public int getStartWeek() {
        return mStartWeek;
    }

    public int getEndWeek() {
        return mEndWeek;
    }

    public int getStepNum() {
        return mStepNum;
    }

    public String getTech() {
        return mTech;
    }

    public String getClassRoom() {
        return mClassRoom;
    }

    public int getSingleOrDoubleWeekMode() {
        return mSingleOrDoubleWeekMode;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "mCourseName='" + mCourseName + '\'' +
                ", mWeekNum=" + mWeekNum +
                ", mStartNum=" + mStartNum +
                ", mStartWeek=" + mStartWeek +
                ", mEndWeek=" + mEndWeek +
                ", mStepNum=" + mStepNum +
                ", mTech='" + mTech + '\'' +
                ", mClassRoom='" + mClassRoom + '\'' +
                ", mSingleOrDoubleWeekMode=" + mSingleOrDoubleWeekMode +
                '}';
    }

    /**
     * 通过当前周判断 是否是否当前课程需要显示在课程表界面上
     * @param currentWeek 当前周
     * @return 可以显示
     */
    public boolean isNeedShowCourse(int currentWeek) {
        //如果：小于第一周或者大于最后一周 返回false表示不需要显示出来
        if (currentWeek<mStartWeek || currentWeek>mEndWeek)
            return false;
        //不等于0代表具有单双周限制
        if (mSingleOrDoubleWeekMode != 0) {
            Log.d(TAG, "当前周。。单双。。" + currentWeek%2);
            //当前周为双周 但需在单周显示
            if (mSingleOrDoubleWeekMode==1 && currentWeek%2==0)
                return false;
            //当前周为单周 但需在双周显示
            if (mSingleOrDoubleWeekMode==2 && currentWeek%2!=0)
                return false;
        }
        return true;
    }
}
