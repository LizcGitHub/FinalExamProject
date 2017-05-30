package com.study.zouchao.finalexamproject_two.score.presenter;

import android.content.Context;
import android.util.Log;


import com.study.zouchao.finalexamproject_two.login.loginStatus.LoginStatus;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.score.contract.IScoreContract;
import com.study.zouchao.finalexamproject_two.score.model.bean.ScoreInfo;
import com.study.zouchao.finalexamproject_two.score.model.impl.ScoreModel;
import com.study.zouchao.finalexamproject_two.score.view.adapter.MyRecycleAdapter;
import com.study.zouchao.finalexamproject_two.util.LogUtils;
import com.study.zouchao.finalexamproject_two.util.RegexUtils;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by Administrator on 2016/12/24.
 */

public class ScorePresenter implements IScoreContract.IScorePresenter {
    private static final String TAG = "ScorePresenter";
    private Context mContext;
    //view层
    private IScoreContract.IScoreView mView;
    //model层
    private IScoreContract.IScoreModel mModel;

    /**
     * 成绩数据集合
     */
    private List<ScoreInfo> mScoreInfos;

    /**
     * RecyclerView的Adapter
     */
    private MyRecycleAdapter mAdapter;

    public ScorePresenter(Context context, IScoreContract.IScoreView view) {
        mContext = context;
        mView = view;
        //初始化
        initData();
        //开始加载
        startLoad();
        //绑定EventBus
        EventBus.getDefault().register(this);
    }

    /**
     * 用于实例化RecycleView的成绩集合
     */
    private void initData() {
        //实例化Model层
        mModel = new ScoreModel();
        //实例化RecycleView的数据集合
        mScoreInfos = new ArrayList<>();
        //实例化Adapter
        mAdapter = new MyRecycleAdapter(mContext, mScoreInfos);
        //设置RecyclerView的adapter
        mView.setAdapter(mAdapter);
    }

    /**
     * 开始从网路加载数据
     *      当前学年
     *          注：查询当前学年不需要参数
     */
    private void startLoad() {
        //显示Loading动画
        mView.showLoading(true);
        //开始加载
        mModel.connScoreCurrentAdademicYear(C_XMUT.SCORE_URL, null, new IScoreContract.IConnListener() {
            @Override
            public void onSuccess(Connection.Response response) throws UnsupportedEncodingException {
                LogUtils.i("当前学年的成绩", response.body().trim());
                /*
                 * 可能成绩不允许查询(要先进行教学质量评价之类的)
                 */
                //要解析的HTML
                String html = response.body().trim();
                if (! isAllowSearchScore(html))
                    mView.showDialog(getPromoteTv(html));
                else {
                    //取出__VIEWSTATE
                    parseOut_ViewState(response);
                    //解析成绩数据
                    parseOutScoreTableData(Jsoup.parse(response.body().trim()));
                    //加载完成后,显示数据
                    showScoreData();
                }
                mView.showLoading(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                //隐藏Loading动画
                mView.showLoading(false);
                //提示用户失败
                ToastUtils.showShort(mContext, "\t\t\t  获取成绩失败, 请检查网络等设置\n" +
                        "(也有可能是学校系统SB了..反正不是我SB..)");
            }
        });
    }

    /**
     * 有几种不允许查询成绩的情况:
     *      1.请您先进行教学质量评价！                //会弹出一个提示框 解析出其中的内容 然后回到之前的页面
     * @param html
     */
    private boolean isAllowSearchScore(String html) {
        if (StringUtils.isEmpty(getPromoteTv(html)))
            return true;
        return false;
    }

    /**
     * 获取提示框Alert中的内容
     * @param html
     * @return
     */
    private String getPromoteTv(String html) {
        /**
         * 正则表达式匹配：
         *      用于匹配弹出框
         *      eg: <script language='javascript'>alert('请您先进行教学质量评价！');window.opener=null;history.go(-1);parent.document.getElementById('dqwz').innerText=parent.document.getElementById('lj').innerText;</script>
         */
        String regex = "(?<=alert\\(\\').*(?=\\'\\);)";
        Matcher matcher = RegexUtils.regexMatcher(html, regex);
        String promoteTv = "";
        while (matcher.find()) {
            promoteTv = matcher.group();
        }
        return promoteTv;
    }

    /**
     * 获取当前学年成绩时，解析出 一大串的 __VIEWSTATE
     * @param response
     */
    private void parseOut_ViewState(Connection.Response response) {
        final Document doc = Jsoup.parse(response.body().trim());
        String _viewState = doc.select("input[name=__VIEWSTATE]").val();
        /*
         * 将__ViewState_Score赋值
         */
        LoginStatus.__ViewState_Score = _viewState;
        LogUtils.d("解析出的_viewstate", _viewState);
        //加载其他学年的成绩
//        loadOtherAcademicYearScore();
    }

    /**
     *  * 加载其他学年的成绩
     * 注：要想取得其他学年的数据 需要先获取当前学年的数据 然后获取其中的__VIEWSTATE
     * @param year 学年
     * @param term 学期
     */
    private void loadOtherAcademicYearScore(String year, String term) {
        //开始加载
        mView.showLoading(true);
        //连接网络
        mModel.connScoreOtherAdademicYear(C_XMUT.SCORE_URL, getRequestParams(year, term), new IScoreContract.IConnReturnDocListener() {
            @Override
            public void onSuccess(Document doc) {
                //解析出成绩数据
                parseOutScoreTableData(doc);
                //加载完成后显示数据
                showScoreData();
                //隐藏Loading
                mView.showLoading(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                mView.showLoading(false);
                Log.d(TAG, "OtheronFailure有执行？");
                //提示用户失败
                ToastUtils.showShort(mContext, "\t\t\t  获取成绩失败, 请检查网络等设置\n" +
                        "(也有可能是学校系统SB了..反正不是我SB..)");
            }
        });
    }

    /**
     * 解析HTML代码 并获得其中的成绩数据
     *   tr:一科一行
     *   td:某一科中的某一个属性
     * @param doc
     */
    private void parseOutScoreTableData(Document doc) {
        //再开始之前先clear掉
        mScoreInfos.clear();
        //先获得大Table
        Element tableEle = doc.getElementById("DataGrid1");
        //获得table中所有的tr
        Elements trEles = tableEle.getElementsByTag("tr");
        //去掉第一行tr(第一行是标题栏)
        trEles.remove(0);
        //遍历每个tr中的每个td
        for (Element trEle : trEles) {
            //得到某一科的所有属性
            Elements tdEles = trEle.getElementsByTag("td");
            //创建一个bean对象
            ScoreInfo bean = new ScoreInfo();
            //设置bean的所有属性
            setBeanEveryAttrs(bean, tdEles);
            //将当前bean添加进RecycleView的数据集合中
            mScoreInfos.add(bean);
        }
    }

    /**
     * 设置成绩bean的所有属性
     * @param bean
     * @param tdEles
     */
    private void setBeanEveryAttrs(ScoreInfo bean, Elements tdEles) {
        bean.setAcademicYear(getAttrValueByTrIndex(tdEles, 0))
                .setTerm(getAttrValueByTrIndex(tdEles, 1))
                .setCourseCode(getAttrValueByTrIndex(tdEles, 2))
                .setCourseName(getAttrValueByTrIndex(tdEles, 3))
                .setCourseProperty(getAttrValueByTrIndex(tdEles, 4))
                .setCourseBelong(getAttrValueByTrIndex(tdEles, 5))
                .setCredit(getAttrValueByTrIndex(tdEles, 6))
                .setScore(getAttrValueByTrIndex(tdEles, 7))
                .setReExamScore(getAttrValueByTrIndex(tdEles, 8))
                .setIsRebuild(getAttrValueByTrIndex(tdEles, 9))
                .setInstitute(getAttrValueByTrIndex(tdEles, 10))
                .setNotes(getAttrValueByTrIndex(tdEles, 11))
                .setReExamNotes(getAttrValueByTrIndex(tdEles, 12));
    }

    /**
     * 在数据加载完成后将其显示出来
     */
    public void showScoreData() {
        LogUtils.d(TAG, "来吧~~让我看下所有的成绩数据");
        for (ScoreInfo bean : mScoreInfos) {
            LogUtils.d("成绩bean!!!", bean.toString());
        }
        //设置adapter
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 根据属性的索引值得到其中的html值
     * @param tdEles
     * @param attrIndex
     * @return
     */
    private String getAttrValueByTrIndex(Elements tdEles, int attrIndex) {
        return tdEles.get(attrIndex).html();
    }

    /**
     * 得到请求成绩的参数
     * @return 参数的Map集合
     * @param year 学年
     * @param term 学期
     */
    private Map<String, String> getRequestParams(String year, String term) {
        /*
         * 请求成绩的参数
         */
        Map<String, String> params = new HashMap<>();
        params.put("__EVENTARGUMENT", "");
        params.put("__EVENTTARGET", "");
        params.put("btnCx", " 查  询 ");
        params.put("ddlxq", term);
        params.put("ddlxn", year);
        params.put("__VIEWSTATE", LoginStatus.__ViewState_Score);
        printParams(params);
        return params;
    }

    /**
     * 测试：用于输出 查询其他学年成绩的参数
     * @param map
     */
    private void printParams(Map<String, String> map) {
        for (String key : map.keySet()) {
            Log.i("课程参数", "key.."+key + "\t" + map.get(key));
        }
    }

    /**
     * 点击Fab 选择 学年&学期 成绩切换
     * @param year
     * @param term
     */
    @Override
    public void chooseYearAndTerm(String year, String term) {
        //成绩更新
        loadOtherAcademicYearScore(year, term);
    }


    /**
     * EventBus
     */
    /**
     * 当点击对话框时回调方法
     * @param yearAndTerm
     */
    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventChooseYearAndTerm(String yearAndTerm) {
        Log.i("即将改变的学期~", yearAndTerm);
        String[] result = yearAndTerm.split("~");
        //改变成绩
        chooseYearAndTerm(result[0], result[1]);
    }

    /**
     * 生命周期
     */
    @Override
    public void onDestroyPresenter() {
        EventBus.getDefault().unregister(this);
    }
}