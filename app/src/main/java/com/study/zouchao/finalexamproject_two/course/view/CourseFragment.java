package com.study.zouchao.finalexamproject_two.course.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.course.contract.CourseContract;
import com.study.zouchao.finalexamproject_two.course.presenter.CoursePresenter;
import com.study.zouchao.finalexamproject_two.login.model.C_XMUT;
import com.wx.wheelview.widget.WheelViewDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/9.
 */

public class CourseFragment extends Fragment implements CourseContract.ICourseView {
    //Loading
    private ProgressDialog mLoading;
    //课程表格
    @BindView(R.id.id_tv_course_table)
    TextView mTvCourseTable;
    //用于保存每一天LinearLayout的Id的数组
    private int[] mWeeksId = new int[] {R.id.id_ll_week_1, R.id.id_ll_week_2,
                                        R.id.id_ll_week_3, R.id.id_ll_week_4,
                                        R.id.id_ll_week_5, R.id.id_ll_week_6,
                                        R.id.id_ll_week_7};
    private View mContentView;
    private CourseContract.ICoursePresenter mPresenter;

    //滚动选择器的item
    private List<String> mPickItems;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_course, null);
        ButterKnife.bind(this, mContentView);
        setTitle("");
        initView();
        //创建Presenter
        mPresenter = new CoursePresenter(getContext(), this);
        return mContentView;
    }

    private void initView() {
        mPickItems = new ArrayList<>();
        //初始化
        for (int i = 0; i < C_XMUT.totalWeeksNum; i ++) {
            mPickItems.add("第"+(i+1)+"周");
        }
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    @Override
    public void showCourse(String tableHtml) {
        mTvCourseTable.setText(Html.fromHtml(tableHtml));
    }

    /**
     * 得到各个布局View
     */
    @Override
    public View findViewById(int index) {
        return mContentView.findViewById(mWeeksId[index]);
    }

    /**
     * 显示Loading动画
     * @param isShow
     */
    @Override
    public void showLoading(boolean isShow) {
        if (isShow)
            mLoading = ProgressDialog.show(getContext(), "", "正在加载");
        else mLoading.dismiss();
    }

    /**
     * 监听器
     */
    @OnClick({R.id.id_fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_fab :
                //滚动选择器
                new WheelViewDialog(getContext())
                        .setTitle("选择当前周数:")
                        .setButtonText("确定")
                        .setDialogStyle(Color.parseColor("#6699ff"))
                        .setCount(5)
                        .setItems(mPickItems)
                        .setSelection(C_XMUT.CURRENT_WEEK - 1)                        //设置当前周
                        .setOnDialogItemClickListener(new WheelViewDialog.OnDialogItemClickListener() {
                            @Override
                            public void onItemClick(int position, String s) {
                                Log.i("当前周数", position+1+"");
                                mPresenter.clickFab(position+1);
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        //取消绑定
        mPresenter.onDestroyPresenter();
        super.onDestroy();
    }
}
