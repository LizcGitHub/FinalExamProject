package com.study.zouchao.finalexamproject_two.course.presenter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_two.course.contract.CourseContract;
import com.study.zouchao.finalexamproject_two.course.model.CourseModel;
import com.study.zouchao.finalexamproject_two.course.model.bean.CourseInfo;
import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;
import com.study.zouchao.finalexamproject_two.login.loginStatus.LoginStatus;
import com.study.zouchao.finalexamproject_two.login.loginStatus.StuInfo;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.util.ColorUtil;
import com.study.zouchao.finalexamproject_two.util.LogUtils;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;
import com.study.zouchao.finalexamproject_two.util.WeekUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/9.
 */

public class CoursePresenter implements CourseContract.ICoursePresenter {
    private static final String TAG = "CourseP";
    private Context mContext;
    private CourseContract.ICourseView mView;
    /**
     * 当前课程表中所有的课程(不管要不要显示出来)
     */
    private List<CourseInfo> mAllCourseInfos = new ArrayList<>();

    /**
     * 要显示出来的所有课程
     *      WEEK_DAY_NUM..7
     * 可以放7个集合(每一天要显示的课程集)的数组
     */
    List<CourseInfo>[] mShowCourseInfos;

    public CoursePresenter(Context context, CourseContract.ICourseView view) {
        mContext = context;
        mView = view;
        initData();
        startLoad();
//        testLoad();
    }
    private void initData() {
        mShowCourseInfos = new ArrayList[C_XMUT.WEEK_DAY_NUM];
        //将一周7天的课程集合添加进来
        for (int i = 0; i < C_XMUT.WEEK_DAY_NUM; i ++) {
            mShowCourseInfos[i] = new ArrayList<>();
        }
    }

    /**
     * 开始加载数据
     */
    private void startLoad() {
        try {
            //显示Loading动画
            mView.showLoading(true);
            /**
             * 连接网络
             */
            new CourseModel().connCoursePage(C_XMUT.COURSE_URL, LoginStatus.cookies, new LoginContract.ILoginModel.IConnListener() {
                @Override
                public void onSuccess(Connection.Response response) {
                    LogUtils.d(TAG, response.body());
                    parseHTML(response.body());
                    mView.setTitle(StuInfo.stuClass);
                    //取消Loading动画
                    mView.showLoading(false);
                }
                @Override
                public void onFailure(Throwable throwable) {
//                    ToastUtils.showLong(mContext, "获取课表失败");
                    //取消Loading动画
                    mView.showLoading(false);
                    //提示用户失败
                    ToastUtils.showShort(mContext, "\t\t\t  获取课表失败, 请检查网络等设置\n(也有可能是学校系统SB了..反正不是我SB..)");
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取课表HTML后解析出需要的数据
     */
    private void parseHTML(String html) {
        StuInfo.stuClass = null;
        Document doc = Jsoup.parse(html);
        //获取学院信息
        String institute = doc.getElementById("Label7").html();
        StuInfo.stuInstitute = doc.getElementById("Label7").html();
        Log.i("学生信息", "学院.."+institute);
        //获取专业信息
        String major = doc.getElementById("Label8").html();
        StuInfo.stuMajor = doc.getElementById("Label8").html();
        Log.i("学生信息", "专业.."+major);
        //获取班级信息
        String stuClass = doc.getElementById("Label9").html();
        StuInfo.stuClass = doc.getElementById("Label9").html();
        Log.i("学生信息", "班级.."+stuClass);

        Element courseTableEle = doc.getElementById("Table1");
        parseCourseHtml(courseTableEle);
        mView.showCourse(courseTableEle.toString());
    }

    /**
     * 解析出课程Html
     * @param courseTableEle
     */
    private void parseCourseHtml(Element courseTableEle) {
        for (Element tdEle : courseTableEle.getElementsByTag("td")) {
            LogUtils.i("所有课程HTML", tdEle.toString());
            /**
             * 只能找带rowspan属性的
             *      即：至少要连续上两节课
             *      eg:  J2EE架构与程序设计<br>周二第1,2节{第1-15周|单周}<br>周世忠(周世忠)<br>厦软A-407<br><br>面向对象的系统分析与设计<br>周二第1,2节{第2-16周|双周}<br>刘斌(刘斌)<br>厦软实训楼104
             */
            if (!StringUtils.isEmpty(tdEle.html().trim())
                    && !tdEle.html().trim().equals("&nbsp;")
                    && tdEle.html().trim().split("<br>").length>=2) {
                Log.i("找到课程", tdEle.html());
                //生成bean对象
                createCourseBean(tdEle.html().trim());
            }
        }
    }

    /**
     *
     * @param courseHtml 某一节课的Html代码
     *           eg:  J2EE架构与程序设计<br>周二第1,2节{第1-15周|单周}<br>周世忠(周世忠)<br>厦软A-407<br><br>面向对象的系统分析与设计<br>周二第1,2节{第2-16周|双周}<br>刘斌(刘斌)<br>厦软实训楼104
     */
    private void createCourseBean(String courseHtml) {
        /*
         * 每个课程由<br>分成 课程名、节数、任课教师、任课地点 四个部分
         */
        LogUtils.i("即将解析的 课程html", courseHtml);
        //可能有多个课程
        String[] arrCoursesAttrs = splitHtmlByBrTag(courseHtml);
        //以4个为一组分成各个单一课程
        splitSingleCourseBy4Attr(arrCoursesAttrs);
    }

    /**
     * 以4个属性为一组分成各个课程
     * @param arrCoursesAttrs
     */
    private void splitSingleCourseBy4Attr(String[] arrCoursesAttrs) {

        List<String> singleCourseInfo = new ArrayList<>();
        for(int i = 0; i < arrCoursesAttrs.length; i++) {
            if(arrCoursesAttrs[i].equals("")) {
                createSingleCourseBeanBy4SingleCourseAttrs(singleCourseInfo);
                //一个新的List对象
                singleCourseInfo = new ArrayList<>();
                continue;
            }
            singleCourseInfo.add(arrCoursesAttrs[i]);
            if(i == arrCoursesAttrs.length-1)
                createSingleCourseBeanBy4SingleCourseAttrs(singleCourseInfo);
        }
        //生成要显示的课程集合
        initShowCourseInfos();
    }

    /**
     * 通过单个课程的4个属性集
     *  生成单个课程的bean对象
     * @param singleCourse
     */
    private void createSingleCourseBeanBy4SingleCourseAttrs(List<String> singleCourse) {
//        Log.i(".............分成单一课程", "...................");
        CourseInfo bean = new CourseInfo();
        setCourseBeanBaseAttrs(bean, singleCourse);
        //将当前课程bean添加到所有课程集合中去
        mAllCourseInfos.add(bean);
        Log.i("来吧！！让我看下bean的所有属性是否正确", bean.toString());
    }

    /**
     * 设置课程bean除courseTime以外的所有基本属性
     * @param bean
     * @param singleCourse
     */
    private void setCourseBeanBaseAttrs(CourseInfo bean, List<String> singleCourse) {
        /*
         * 课程属性：课程名
         */
        String courseName = singleCourse.get(0);
        Log.i("课程属性", "courseName.."+courseName);
        /*
         * 课程属性：任课教师
         */
        String teacher = singleCourse.get(2);
        Log.i("课程属性", "teacher.."+teacher);
        /*
         * 课程属性：上课教室
         */
        String room = singleCourse.get(3);
        Log.i("课程属性", "room.."+room);

        //设置bean
        bean.setCourseName(courseName)
                .setTech(teacher)
                .setClassRoom(room);

        /*
         * 课程属性：时间：该属性需要判断、分割
         */
        String courseTimeAttrs = singleCourse.get(1);
        //TODO:健壮性处理
        if (StringUtils.isEmpty(courseTimeAttrs))
            return;
        Log.i("该门课的课程时间属性", courseTimeAttrs);
        parseCourseTime(bean, courseTimeAttrs);
    }

    /**
     * 解析courseTimeAttrs字符串
     *      eg: 周二第1,2节{第2-16周|双周}
     *   从中找到
     *      1.第几周上课 2.到第几周 3.上几节 4.第一节课是第几节 是否有单双周限制
     * @param courseTimeAttrs
     */
    private void parseCourseTime(CourseInfo bean, String courseTimeAttrs) {
        /*
         * 字符串截取：找出该门课是星期几开课
         */
        //eg: 3
        int week = WeekUtils.Chinese2Int(courseTimeAttrs.substring(1, 2));
        bean.setWeekNum(week);
        Log.i("课程属性", "星期.."+week);

        /*
         * 找到属性: startNum和stepNum
         */
        findAttrStartNumAndStepNum(bean, courseTimeAttrs);

        /*
         * 找到属性：startWeek 和 endWeek
         */
        findStartWeekAndEndWeek(bean, courseTimeAttrs);
    }

    /**
     * 解析courseTimeAttrs字符串
     *      找到startNum和stepNum
     * @param courseTimeAttrs
     */
    private void findAttrStartNumAndStepNum(CourseInfo bean, String courseTimeAttrs) {
         /*
         * 正则表达式：找出该门课程的 startNum以及stepNum
         */
        //eg: 1,2,3
        String regex = "(?<=第).*(?=节)";
        Matcher matcher = regexMatcher(courseTimeAttrs, regex);
        String resultStr = "";
        while (matcher.find()) {
            resultStr = matcher.group();
        }
        /*
         * 字符串分割 以 , 为分割找出startNum和stepNum
         */
        //课程属性：startNum
        int startNum =  Integer.valueOf(resultStr.split(",")[0]);
        Log.i("课程属性", "startNum.."+startNum);
        //课程属性：stepNum
        int stepNum = resultStr.split(",").length;
        Log.i("课程属性", "stepNum.."+stepNum);

        //设置bean
        bean.setStartNum(startNum)
                .setStepNum(stepNum);
    }

    /**
     * 解析courseTimeAttrs字符串
     *      找到startWeek和endWeek
     * @param courseTimeAttrs
     */
    private void findStartWeekAndEndWeek(CourseInfo bean, String courseTimeAttrs) {
        /*
         * 正则表达式：找出该门课程的 startWeek和endWeek
         */
        String regex = "";
        if (findIsSingleOrDoubleWeekLimit(bean, courseTimeAttrs)) {
            /*
             * 存在单双限制
             *  eg: 周四第1,2节{第1-15周|单周}
             *
             */
            regex = "(?<=\\{第).*(?=周\\|)";

        } else {
            /*
             * 不存在单双限制
             *  eg: 周一第1,2节{第1-16周}
             */
            //查找子串：第..周内   eg:1-15
            regex = "(?<=\\{第).*(?=周\\})";
        }
        Matcher matcher = regexMatcher(courseTimeAttrs, regex);
        String resultStr = "";
        while (matcher.find()) {
            resultStr = matcher.group();
        }
        LogUtils.i("分割周之前字符串", resultStr);
        /*
         * 字符串分割：以 - 为分割找出startWeek和endWeek
         */
        //startWeek
        int startWeek = Integer.valueOf(resultStr.split("-")[0]);
        Log.i("课程属性", "startWeek.."+startWeek);
        //endWeek
        int endWeek = Integer.valueOf(resultStr.split("-")[1]);
        Log.i("课程属性", "endWeek.."+endWeek);

        //设置bean
        bean.setStartWeek(startWeek)
                .setEndWeek(endWeek);
    }

    /**
     * 判断是否有单双周限制
     *      并且给bean当中的mSingleOrDoubleWeekMode赋值
     * @param bean
     * @param courseTimeAttrs
     * @return
     */
    private boolean findIsSingleOrDoubleWeekLimit(CourseInfo bean, String courseTimeAttrs) {
        /*
         * 正则表达式：用于找出
         *      {第1-15周|单周}
         */
        String regex1 = "(?<=\\{).*(?=\\})";
        Matcher matcher1 = regexMatcher(courseTimeAttrs, regex1);
        String resultStr1 = "";
        while (matcher1.find()) {
            resultStr1 = matcher1.group();
        }
        Log.i("单双周结果", "resultStr1.."+resultStr1);
        /*
         *   正则表达式：用于找出是否纯在单双周限制
         *   eg : 单
         */
        //分出 "单" "双" 这一个字
        Pattern pattern2 = Pattern.compile("(?<=\\|).*(?=周)");

        Matcher matcher2 = pattern2.matcher(resultStr1);
        String singleDoubleResult = "";
        if(matcher2.find()) {
            //存在单双周限制
            singleDoubleResult = matcher2.group();
            Log.i("单双周结果", matcher2.group());
            //将结果写入bean中
            bean.setSingleOrDoubleWeekMode(judgeSingleWeekOrDoubleWeek(singleDoubleResult));
            return true;
        } else {
            Log.i("单双周结果", "没有单双周限制");
            //不存在单双周限制
            bean.setSingleOrDoubleWeekMode(0);
            return false;
        }
    }

    /**
     * 生成要显示的课程集合
     *      遍历 所有课程的的集合
     *          从中找到 当前周 需要显示出来的课程
     *      如果找到就将其放到mShowCourseInfos(需要显示的)课程集合中去
     *
     */
    private void initShowCourseInfos() {
        //先清空再填入
        initData();

        for (CourseInfo singleCourse : mAllCourseInfos) {
            if (singleCourse.isNeedShowCourse(C_XMUT.CURRENT_WEEK)) {
                //第几周显示
                int weekNum = singleCourse.getWeekNum();
                //当前的课程集合
                List<CourseInfo> weekCourseInfos = mShowCourseInfos[weekNum - 1];
                weekCourseInfos.add(singleCourse);
            }
        }
        //显示出来
        showCourse(mShowCourseInfos);
    }

    /**
     * 将中文的 "单" "双" 转成int
     * @param week
     * @return
     */
    private int judgeSingleWeekOrDoubleWeek(String week) {
        if (StringUtils.isEmpty(week))
            return 0;
        if (week.equals("单"))
            return 1;
        if (week.equals("双"))
            return 2;
        return 0;
    }

    /**
     * 正则匹配
     * @param inputStr
     * @param regex
     * @return
     */
    private Matcher regexMatcher(String inputStr, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher;
    }

    /**
     *  通过<br>标签将html分割
     * @param courseHtml 某一节课的Html代码与上面一致
     * @return 分割后的属性数组
     */
    private String[] splitHtmlByBrTag(String courseHtml) {
        //分割
        String[] brArr = courseHtml.split("<br>");
        return brArr;
    }

    /**
     * 将需要显示的课程显示出来
     * @param courseInfos
     */
    private void showCourse(List<CourseInfo>[] courseInfos) {
        //从周一 遍历到 周日
        for (int week = 0; week < courseInfos.length; week ++) {
            //find界面上的每天课程的RelativeLayout
            RelativeLayout weekLayout = (RelativeLayout) mView.findViewById(week);
            //先清空再添加
            clearAllShowCourseView(weekLayout);
            //一天当中所有的课程
            List<CourseInfo> weekCourses = courseInfos[week];
            //有可能一整天都没课, 所以要判断是否为空
            if (weekCourses!=null &&
                    !weekCourses.isEmpty()) {
                //添加每一门课
                for (CourseInfo course : weekCourses) {
                    addCourse2Layout(weekLayout, course);
                }
            }
        }
    }

    /**
     * 清空界面上所有
     * @param weekLayout
     */
    private void clearAllShowCourseView(RelativeLayout weekLayout) {
        weekLayout.removeAllViews();
    }

    /**
     * 为week的layout中添加课程
     * @param weekLayout  纵向的WeekLayout
     * @param course      某一课程bean
     */
    private void addCourse2Layout(RelativeLayout weekLayout, CourseInfo course) {
        //需要显示的信息：课程名@教室
        String showInfo = course.getCourseName()+"@"+course.getClassRoom();
        //第一节课开始于startNum节
        int startNum = course.getStartNum();
        //需要上的节数
        int stepNum = course.getStepNum();
        //添加TextView到Layout中去是实现课程表
        TextView tvCourse = new TextView(mContext);
        //TextView的Params
        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, C_XMUT.ITEM_SINGLE_HEIGHT*stepNum);
        int marginTop = (startNum-1)*C_XMUT.ITEM_SINGLE_HEIGHT;
        //设置marginTop
        tvParams.setMargins(0, marginTop, 0, 0);
        //为TextView设置基本属性信息
        tvCourse.setLayoutParams(tvParams);
        tvCourse.setTextColor(Color.WHITE);
        tvCourse.setGravity(Gravity.CENTER);
        //设置文字最大字数
        //设置文字
        tvCourse.setText(showInfo);
        //为课程设置一个随机的颜色
        tvCourse.setBackgroundColor(ColorUtil.randomBaseColor(mContext));
        //添加课程
        weekLayout.addView(tvCourse);
    }

    /*
     * Presenter层
     */

    /**
     * 点击滚动选择器 选择当前周
     * @param currentWeek
     */
    @Override
    public void clickFab(int currentWeek) {
        //重设当前周
        C_XMUT.CURRENT_WEEK = currentWeek;
        //刷新课程数据
        initShowCourseInfos();
    }

    @Override
    public void onDestroyPresenter() {

    }
}

