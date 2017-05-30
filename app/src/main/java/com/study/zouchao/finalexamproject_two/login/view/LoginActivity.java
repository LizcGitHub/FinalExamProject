package com.study.zouchao.finalexamproject_two.login.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.base_zou.ZouImgLoader;
import com.study.zouchao.finalexamproject_two.login.contract.LoginContract;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.study.zouchao.finalexamproject_two.login.presenter.LoginPresenter;
import com.study.zouchao.finalexamproject_two.mainxmut.view.MainXUMTActivity;
import com.study.zouchao.finalexamproject_two.util.DialogUtil;
import com.study.zouchao.finalexamproject_two.util.SharedPreUtil;
import com.study.zouchao.finalexamproject_two.util.ToastUtils;

import org.jsoup.Connection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginContract.ILoginView {
    private static final String TAG = "MainA";
    private String mUser = "";
    private String mPass = "";
    //验证码
    private String mCheckCode = "";
    private LoginPresenter mPresenter;

    //验证码图片
    @BindView(R.id.id_iv_checkCode)
    ImageView mIvCheckCode;
    //登陆按钮
    @BindView(R.id.id_btn_login)
    Button mBtnLogin;
    //账号输入框
    @BindView(R.id.id_edit_username)
    EditText mEditUser;
    //密码输入框
    @BindView(R.id.id_edit_password)
    EditText mEditPass;
    //验证码输入框
    @BindView(R.id.id_edit_checkcode)
    EditText mEditText;
    //验证码Loading框
    @BindView(R.id.id_loading_checkcode)
    ProgressBar mLoadingCheckCode;
    //大Loading动画
    ProgressDialog mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //显示上一次登陆成功后的账号密码
        showLastUserAndPwd();
        mPresenter = new LoginPresenter(this, this);
    }


    private void showLastUserAndPwd() {
        String lastUser = SharedPreUtil.getString(this, C_XMUT.sUser, "");
        String lastPwd = SharedPreUtil.getString(this, C_XMUT.sPwd, "");
        //显示在界面上
        mEditUser.setText(lastUser);
        mEditPass.setText(lastPwd);
    }

    @Override
    public void loadCheckCode(final byte[] bytes) {
        //确认可以加载
        if (mIvCheckCode == null) return;
        //图片加载
        ZouImgLoader.loadImage(LoginActivity.this, mIvCheckCode,
                bytes, R.drawable.error,  true, DiskCacheStrategy.NONE);
    }

    /**
     * 登陆Loading
     * @param isShow
     */
    @Override
    public void showLoading(boolean isShow) {
        if (isShow)
            mLoading = ProgressDialog.show(this, "", "正在加载...");
        else mLoading.dismiss();
    }

    @Override
    public void finishCurrentActivity() {
        finish();
    }

    @Override
    public void action2MainActivity(Connection.Response response) {
        ToastUtils.showShort(this, "登陆成功!!!");
        MainXUMTActivity.actionMain(this, C_XMUT.LOGINED_SUCCESS_HTML, response.body().trim());
    }

    /**
     * 设置验证码图片的可见性
     *      true:
     *
     * @param isShow
     */
    @Override
    public void showCheckCodeImage(boolean isShow) {
        /*
         *  如果为true
         *    设置验证码图片可见
         *    设置验证码Loding动画不可见
         */
        mLoadingCheckCode.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mIvCheckCode.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 登录失败后 弹出提示框给用户
     * @param promoteTv
     */
    @Override
    public void showLoginErrorDialog(String promoteTv) {
        //弹出提示框 告诉用户登录失败
        DialogUtil.showSingleAlertDialog(this, "登陆失败", promoteTv, null);
        //登陆失败 更改EditText
        loginErrorUpateEditText(promoteTv);
    }

    /**
     * 测试生命周期
     */
    @Override
    public void testView() {
        Log.d(TAG, "testView..为什么没炸");
        //TODO:试试
        Log.d(TAG, "mEditUser没炸？？" + mEditUser.getText().toString());
        //todo:
    }

    /**
     * 登陆失败后 更改UI
     *    分别更改用户名,密码,验证码的EditText
     *      设置editText 内容为空("")
     *      且获取焦点
     */
    private void loginErrorUpateEditText(String promoteTv) {
        String checkcodeError = getResources().getString(R.string.checkCodeError);
        String userError = getResources().getString(R.string.userError);
        String pwdError = getResources().getString(R.string.pwdError);
        //验证码错误
        if (promoteTv.equals(checkcodeError)) {
            //内容重设为空
            mEditText.setText("");
            //获取焦点
            mEditText.requestFocus();
        }
        //用户名不存在
        if (promoteTv.equals(userError)) {
            mEditUser.setText("");
            mEditUser.requestFocus();
        }
        //密码错误
        if (promoteTv.equals(pwdError)) {
            mEditPass.setText("");
            mEditPass.requestFocus();
        }
    }

    /**
     * 点击监听器
     * @param v
     */
    @OnClick({R.id.id_btn_login, R.id.id_iv_checkCode})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_login :
                //点击登录按钮
                mCheckCode = mEditText.getText().toString().trim();
                mUser = mEditUser.getText().toString().trim();
                mPass = mEditPass.getText().toString().trim();
                //登录
                mPresenter.attemptLogin(mUser, mPass, mCheckCode);
                break;
            case R.id.id_iv_checkCode :
                //重新获取,加载验证码
                mPresenter.reloadCheckCode();
                break;
        }
    }

    public static void actionLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 生命周期
     */
    @Override
    protected void onDestroy() {
        //onDestroy()时销毁Presenter
        mPresenter.onDestroyPresenter();
        super.onDestroy();
    }
}
