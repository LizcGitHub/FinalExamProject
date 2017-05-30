package com.study.zouchao.finalexamproject_two.mainxmut.view;

import android.graphics.ImageFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.course.view.CourseActivity;
import com.study.zouchao.finalexamproject_two.mainxmut.contract.MainContract;
import com.study.zouchao.finalexamproject_two.mainxmut.presenter.MainPresenter;
import com.study.zouchao.finalexamproject_two.score.view.activity.ScoreActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subcriber;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.HTTP;
import retrofit2.http.POST;


/**
 * Created by Administrator on 2016/12/9.
 */

public class MainXMUTFragment extends Fragment implements MainContract.IMainView {
    private View mContentView;
    private MainContract.IMainPresenter mPresenter;
    public MainXMUTFragment() {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        org.simple.eventbus.EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_main_xmut, null);
        ButterKnife.bind(this, mContentView);
        mPresenter = new MainPresenter(getContext(), this);
        return mContentView;
    }

    @Subcriber(tag = "MainXMUTFragment")
    public void onEventGetMainHTML(String html) {
        getActivity().setTitle("ABCDE");
        mPresenter.parseHTML(html);
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle("欢迎您：" + title);
    }
    @OnClick({R.id.id_btn_score, R.id.id_btn_course})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_score :
                //跳转到成绩查询页面
                ScoreActivity.actionScoreActivity(getActivity(), null, null);
                break;
            case R.id.id_btn_course :
                //跳转到课表页面
                CourseActivity.actionScoreActivity(getActivity(), null, null);
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
