package com.study.zouchao.finalexamproject_two.login.presenter;

import android.content.Context;
import android.util.Log;


import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;
import com.study.zouchao.finalexamproject_two.login.loginStatus.LoginStatus;
import com.study.zouchao.finalexamproject_two.login.loginStatus.LoginStatusUtil;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.login.model.LoginModel;
import com.study.zouchao.finalexamproject_two.util.LogLongUtil;
import com.study.zouchao.finalexamproject_two.util.RegexUtils;
import com.study.zouchao.finalexamproject_two.util.StringUtils;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;


/**
 * Created by Administrator on 2016/12/8.
 */

public class LoginPresenter implements LoginContract.ILoginPresenter {
    private static final String TAG = "LoginP";
    //__VIEWSTATE
    private String m__ViewState = "";
    private Context mContext;
    //Cookies
    private Map<String, String> mCookies;
    private LoginContract.ILoginView mView;
    private LoginContract.ILoginModel mModel;
    public LoginPresenter(Context context, LoginContract.ILoginView view) {
        mContext = context;
        mView = view;
        mModel = new LoginModel();
        connXMUT();
    }


    private void connXMUT() {
        //加载连接
        mModel.connXMUT(C_XMUT.XMUT_URL, new LoginContract.ILoginModel.IConnListener() {
            @Override
            public void onSuccess(Connection.Response response) {
                C_XMUT.LOGINED_URL = response.url().toString();
                String[] arr = C_XMUT.LOGINED_URL.split("/");
                C_XMUT.BASE_URL = C_XMUT.LOGINED_URL.substring(0, C_XMUT.LOGINED_URL.length() - arr[arr.length - 1].length());
                Log.d("Base_Url", C_XMUT.BASE_URL);
                Log.d("connXMUT", response.url().toString());
                setAllUrl();
                connDefaultPage();
            }
            @Override
            public void onFailure(Throwable throwable) {
                LogLongUtil.logD("connXMUT", throwable.getMessage());
                throwable.printStackTrace();
                //取消验证码Loading加载 并显示验证码图片
                mView.showCheckCodeImage(true);
                //使显示加载失败图片
                mView.loadCheckCode(null);
                //TODO:递归解决:再次连接..所以要显示验证码Loading动画
                mView.showCheckCodeImage(false);
            }
        });
    }

    /**
     * 获得BaseUrl后设置所有url
     */
    private void setAllUrl() {
        C_XMUT.CHECK_CODE_URL = C_XMUT.BASE_URL + "CheckCode.aspx";
        C_XMUT.logAllUrl();
    }

    /**
     * 获取DefaultPage(Login页面)的HTML
     */
    private void connDefaultPage() {
        mModel.connAndgetCookies(C_XMUT.LOGINED_URL, new LoginContract.ILoginModel.IConnListener() {
            @Override
            public void onSuccess(Connection.Response response) {
                //解析
                judgeDefaultPageResponse(response);
                //请求验证码bytes
                loadCheckCodeByBytes();
            }
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                //取消验证码Loading加载 并显示验证码图片
                mView.showCheckCodeImage(true);
                //使显示加载失败图片
                mView.loadCheckCode(null);
            }
        });
    }

    /**
     * 从DefaultPage(Login页面)的HTML中解析出__viewState和cookies
     * @param response
     */
    private void judgeDefaultPageResponse(Connection.Response response) {
        //得到cookies
        mCookies = response.cookies();
        LogLongUtil.logD("judgeDefaultResponse..", response.body().toString().trim());
        //得到mViewState
        Document doc = Jsoup.parse(response.body().trim());
        m__ViewState = doc.select("input[name=__VIEWSTATE]").val();
        Log.d("parse出的ViewState", m__ViewState);
    }

    /**
     * 加载验证码
     *      获取验证码图片的bytes
     */
    private void loadCheckCodeByBytes() {
        //隐藏验证码图片,并且显示验证码loading动画
        mView.showCheckCodeImage(true);
        /*
         * 通过Model获取验证码bytes
         */
        mModel.getCheckCodeBytes(C_XMUT.CHECK_CODE_URL, mCookies, new LoginContract.ILoginModel.IConnListener() {
            @Override
            public void onSuccess(Connection.Response response) {
                //加载验证码图片
                mView.loadCheckCode(response.bodyAsBytes());
                //取消验证码Loading加载 并显示验证码图片
                mView.showCheckCodeImage(true);
            }
            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                //取消验证码Loading加载 并显示验证码图片
                mView.showCheckCodeImage(true);
                //使显示加载失败图片
                mView.loadCheckCode(null);
            }
        });
    }

    /**
     * 点击登录按钮后：
     *      尝试登陆
     * @param username
     * @param password
     * @param checkCode
     */
    @Override
    public void attemptLogin(final String username, final String password, String checkCode) {
//        LogLongUtil.logI(TAG, username + ".." + password + ".." + checkCode);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(checkCode)) {
            ToastUtils.showLong(mContext, "朋友。。用户名。密码。。验证码请全部输入完整");
            return;
        }
        //显示大Loading动画
        mView.showLoading(true);
        /*
         * 登陆
         */
        mModel.login(C_XMUT.LOGINED_URL, getLoginParams(username, password, checkCode), mCookies, new LoginContract.ILoginModel.IConnListener() {
            @Override
            public void onSuccess(Connection.Response response) {
                Log.d("登陆结果URL", response.url().toString() +"\n" + response.statusCode());
                Log.d("登陆成功的URL", C_XMUT.BASE_URL + "xs_main.aspx?xh="+username);
                //根据返回的页面进行判断
                if (response.url().toString().equals(C_XMUT.BASE_URL + "xs_main.aspx?xh="+username)) {
                    //关闭Loading动画
                    mView.showLoading(false);
                    //登录成功后保存登录状态
                    saveLoginStatus(username, password, m__ViewState, mCookies);
                    //登陆成功后记住账号密码
                    saveUserAndPwd2Disk(username, password);
                    //登录成功
                    mView.action2MainActivity(response);
                    mView.finishCurrentActivity();
                } else {
                    //关闭Loading动画
                    mView.showLoading(false);
                    LogLongUtil.logD("登陆失败的HTML", response.body());
                    judgeLoginFailure(response.body());
                    //用户登录失败后,重新获取验证码
                    reloadCheckCode();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                ToastUtils.showShort(mContext, "登陆失败。。朋友。。请检查网络连接。。");
                throwable.printStackTrace();
                //关闭Loading动画
                mView.showLoading(false);
            }
        });
    }


    /**
     * 登陆成功后 保存账号 密码
     * @param user
     * @param pwd
     */
    private void saveUserAndPwd2Disk(String user, String pwd) {
        LoginStatusUtil.updateUserAndPwd(mContext, user, pwd);
    }


    /**
     * 重新加载验证码
     */
    @Override
    public void reloadCheckCode() {
        //TODO:需要判断一下是否 已经加载好了Base_Url等 假如没有 就重头开始加载
        //通过model获取图片bytes
        loadCheckCodeByBytes();
    }

    /**
     * 登陆失败
     * @param html
     */
    public void judgeLoginFailure(String html) {
        LogLongUtil.logI("登陆失败提示", html);
        /**
         * 字符串:
         *      <script language="javascript" defer="">alert('验证码不正确！！');document.getElementById('TextBox2').focus();</script>
         * 正则表达式:
         *      (?<=alert\\(\\').*(?=\\'\\);)
         *      eg: 取出alert括号中的内容：验证码不正确！！
         */
        String regex = "(?<=alert\\(\\').*(?=\\'\\);)";
        Matcher matcher = RegexUtils.regexMatcher(html, regex);
        //提示文字
        String promoteTv = "";
        while (matcher.find()) {
            promoteTv = matcher.group();
        }
        if (!StringUtils.isEmpty(promoteTv)) {
            mView.showLoginErrorDialog(promoteTv);
        }
    }

    /**
     * 登陆成功后保存登陆状态
     */
    private void saveLoginStatus(String username, String password, String viewState, Map<String, String> cookies) {
        /**
         * 保存登陆状态常量
         */
        LoginStatus.username = username;
        LoginStatus.password = password;
        LoginStatus._ViewState = m__ViewState;
        LoginStatus.cookies = cookies;
        /**
         * 保存Url
         */
        C_XMUT.REFER_URL = C_XMUT.BASE_URL+"xs_main.aspx?xh="+username;
    }
    private Map<String, String> getLoginParams(String username, String password, String checkCode) {
        Map<String, String> params = new HashMap<>();
        params.put("__VIEWSTATE", m__ViewState);
        params.put("Button1", "");
        params.put("hidPdrs", "");
        params.put("hidsc", "");
        params.put("lbLanguage", "");
        params.put("RadioButtonList1", "学生");
        params.put("TextBox2", password);
        params.put("txtSecretCode", checkCode);
        params.put("txtUserName", username);
        return params;
    }



    /**
     * 生命周期
     */
    @Override
    public void onDestroyPresenter() {
        //销毁
        mModel.onDestroyModel();
        //TODO:官方写法
//        //取消和View层的绑定
//        if (mView != null)
//            mView = null;
    }
}
