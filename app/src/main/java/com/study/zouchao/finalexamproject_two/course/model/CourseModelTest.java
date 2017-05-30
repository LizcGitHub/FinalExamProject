package com.study.zouchao.finalexamproject_two.course.model;

import com.study.zouchao.finalexamproject_two.course.contract.CourseContract;
import com.study.zouchao.finalexamproject_two.course.model.bean.CourseInfo;
import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/23.
 */

public class CourseModelTest implements CourseContract.ICourseModel {

    @Override
    public void connCoursePage(String url, Map<String, String> cookies, LoginContract.ILoginModel.IConnListener listener) throws UnsupportedEncodingException {
    }

    /**
     * TODO:测试数据
     * @param listener
     */
    @Override
    public void connTest(ITestListener listener) {
        listener.onSuccess(testData());
    }

    private List<CourseInfo>[] testData() {
//        //每周所有课程(定义了一个数组)
        List<CourseInfo>[] allCourses = new ArrayList[7];
        /**
         * 每天所有课程
         */
        //周一
        List<CourseInfo> course_1 = new ArrayList<>();
//        course_1.add(new CourseInfo("软件工程", 1, 1, 2, "陈烨", "厦软A-203"));
//        course_1.add(new CourseInfo("UML", 1, 3, 2, "刘斌", "厦软B-210"));
        allCourses[0] = course_1;
        //周二
        List<CourseInfo> course_2 = new ArrayList<>();
//        course_2.add(new CourseInfo("J2EE", 2, 1, 2, "周世忠", "厦软A-407"));
//        course_2.add(new CourseInfo("专英", 2, 3, 2, "周宏博", "厦软A-208"));
//        course_2.add(new CourseInfo("Flash", 2, 9, 3, "朱薇", "厦软A-508机"));
        allCourses[1] = course_2;
        //周三
        List<CourseInfo> course_3 = new ArrayList<>();
//        course_3.add(new CourseInfo("软件工程", 3, 3, 2, "陈烨", "厦软A-203"));
        allCourses[2] = course_3;
        //周四
        List<CourseInfo> course_4 = new ArrayList<>();
//        course_4.add(new CourseInfo("软件项目", 4, 1, 2, "刘梅兰", "厦软A-208"));
//        course_4.add(new CourseInfo("J2EE", 4, 5, 2, "周世忠", "厦软实训楼105"));
        allCourses[3] = course_4;
        return allCourses;
    }
}
