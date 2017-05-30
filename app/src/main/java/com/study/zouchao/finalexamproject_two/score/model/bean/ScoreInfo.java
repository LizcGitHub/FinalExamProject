package com.study.zouchao.finalexamproject_two.score.model.bean;


import com.study.zouchao.finalexamproject_two.util.StringUtils;

/**
 * 学生成绩Bean
 */

public class ScoreInfo {
    /**
     * 0.学年：
     *   eg: 2016-2017
     */
    private String mAcademicYear;

    /**
     * 1.学期：
     *   eg: 1  //第一学期
     */
    private String mTerm;

    /**
     * 2.课程代号:
     *   eg: R2900010
     */
    private String mCourseCode;

    /**
     * 3.课程名称：
     *   eg: 网页动画制作
     */
    private String mCourseName;

    /**
     * 4.课程性质:
     *   eg: 公共选修课
     */
    private String mCourseProperty;

    /**
     * 5.课程归属:
     *   eg: 计算机及其他实用技术
     */
    private String mCourseBelong;

    /**
     * 6.学分:
     *   eg: 2.0
     */
    private String mCredit;

    /**
     * 7.成绩:
     *   eg: 87
     */
    private String mScore;

    /**
     * 8.补考成绩:
     *   eg:
     */
    private String mReExamScore;

    /**
     * 9.是否重修:
     */
    private String mIsRebuild;

    /**
     * 10.开课学院:
     *   eg: 软件工程学院
     */
    private String mInstitute;

    /**
     * 11.备注:
     */
    private String mNotes;

    /**
     * 12.补考备注:
     */
    private String mReExamNotes;

    public ScoreInfo() {}

    public ScoreInfo setAcademicYear(String academicYear) {
        if (isValidAttrValue(academicYear))
            this.mAcademicYear = academicYear;
        else this.mAcademicYear = "";
        return this;
    }

    public String getAcademicYear() {
        return mAcademicYear;
    }

    public String getTerm() {
        return mTerm;
    }

    public ScoreInfo setTerm(String term) {
        if (isValidAttrValue(term))
            this.mTerm = term;
        else this.mTerm = "";
        return this;
    }

    public String getCourseCode() {
        return mCourseCode;
    }

    public ScoreInfo setCourseCode(String courseCode) {
        if (isValidAttrValue(courseCode))
            this.mCourseCode = courseCode;
        else this.mCourseCode = "";
        return this;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public ScoreInfo setCourseName(String courseName) {
        if (isValidAttrValue(courseName))
            this.mCourseName = courseName;
        else this.mCourseName = "";
        return this;
    }

    public String getCourseProperty() {
        return mCourseProperty;
    }

    public ScoreInfo setCourseProperty(String courseProperty) {
        if (isValidAttrValue(courseProperty))
            this.mCourseProperty = courseProperty;
        else this.mCourseProperty = "";
        return this;
    }

    public String getCourseBelong() {
        return mCourseBelong;
    }

    public ScoreInfo setCourseBelong(String courseBelong) {
        if (isValidAttrValue(courseBelong))
            this.mCourseBelong = courseBelong;
        else this.mCourseBelong = "";
        return this;
    }

    public String getCredit() {
        return mCredit;
    }

    public ScoreInfo setCredit(String credit) {
        if (isValidAttrValue(credit))
            this.mCredit = credit;
        else this.mCredit = "";
        return this;
    }

    public String getScore() {
        return mScore;
    }

    public ScoreInfo setScore(String score) {
        if (isValidAttrValue(score))
            this.mScore = score;
        else this.mScore = "";
        return this;
    }

    public String getReExamScore() {
        return mReExamScore;
    }

    public ScoreInfo setReExamScore(String reExamScore) {
        if (isValidAttrValue(reExamScore))
            this.mReExamScore = reExamScore;
        else mReExamScore = "";
        return this;
    }

    public String isRebuild() {
        return mIsRebuild;
    }

    public ScoreInfo setIsRebuild(String isRebuild) {
        if (isValidAttrValue(isRebuild))
            this.mIsRebuild = isRebuild;
        else mIsRebuild = "";
        return this;
    }

    public String getInstitute() {
        return mInstitute;
    }

    public ScoreInfo setInstitute(String institute) {
        if (isValidAttrValue(institute))
            this.mInstitute = institute;
        else this.mInstitute = "";
        return this;
    }

    public String getNotes() {
        return mNotes;
    }

    public ScoreInfo setNotes(String notes) {
        if (isValidAttrValue(notes))
            this.mNotes = notes;
        else mNotes = "";
        return this;
    }

    public String getReExamNotes() {
        return mReExamNotes;
    }

    public ScoreInfo setReExamNotes(String reExamNotes) {
        if (isValidAttrValue(reExamNotes))
            this.mReExamNotes = reExamNotes;
        else this.mReExamNotes = "";
        return this;
    }

    @Override
    public String toString() {
        return "ScoreInfo{" +
                "mAcademicYear='" + mAcademicYear + '\'' +
                ", mTerm='" + mTerm + '\'' +
                ", mCourseCode='" + mCourseCode + '\'' +
                ", mCourseName='" + mCourseName + '\'' +
                ", mCourseProperty='" + mCourseProperty + '\'' +
                ", mCourseBelong='" + mCourseBelong + '\'' +
                ", mCredit='" + mCredit + '\'' +
                ", mScore='" + mScore + '\'' +
                ", mReExamScore='" + mReExamScore + '\'' +
                ", mIsRebuild='" + mIsRebuild + '\'' +
                ", mInstitute='" + mInstitute + '\'' +
                ", mNotes='" + mNotes + '\'' +
                ", mReExamNotes='" + mReExamNotes + '\'' +
                '}';
    }

    /**
     * 判断某个属性的值是否为合法字符串(不是空字符串&&不为&nbsp;)
     * @param attrValue
     * @return
     */
    public boolean isValidAttrValue(String attrValue) {
        if (StringUtils.isEmpty(attrValue) || attrValue.equals("&nbsp;"))
            return false;
        return true;
    }
}
