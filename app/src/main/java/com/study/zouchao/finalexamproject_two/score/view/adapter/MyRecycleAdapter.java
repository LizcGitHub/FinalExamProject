package com.study.zouchao.finalexamproject_two.score.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.study.zouchao.finalexamproject_three.R;
import com.study.zouchao.finalexamproject_two.score.model.bean.ScoreInfo;
import com.study.zouchao.finalexamproject_two.util.NumUtils;

import java.util.List;

/**
 * RecyclerView 的 Adapter
 */

public class MyRecycleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    /*
     * 成绩bean数据的集合
     */
    private List<ScoreInfo> mData;

    public MyRecycleAdapter(Context context, List<ScoreInfo> data) {
        mContext = context;
        mData = data;
    }

    /**
     * 在这里自动实例化ViewHolder对象
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_score, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * 在这里设置界面 相当于ListView的getView
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //获得成绩单项
        ScoreInfo bean = mData.get(position);
        //设置成绩
        holder.mTvScore.setText(bean.getScore());
        //设置课程名
        holder.mTvCourseName.setText(bean.getCourseName());
        //设置学分
        holder.mTvCredit.setText(bean.getCredit());
        //学年(学期)
        String year = bean.getAcademicYear();
        //学期：要求是中文
        String term = NumUtils.getNum2Chinese(bean.getTerm());
        //设置学年学期 eg:2015-2016(一)
        holder.mTvYearTerm.setText(year+"("+term+")");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

/**
 * RecyclerView 的ViewHolder
 *      要求继承RecyclerView.ViewHoler
 */
class MyViewHolder extends RecyclerView.ViewHolder {
    /*
     * 成绩, 课程名, 学分, 学年+学期
     */
    TextView mTvScore, mTvCourseName, mTvCredit, mTvYearTerm;

    public MyViewHolder(View itemView) {
        super(itemView);
        /*
         *  在这里findViewById 找到子item
         */
        mTvScore = (TextView) itemView.findViewById(R.id.id_tv_score);
        mTvCourseName = (TextView) itemView.findViewById(R.id.id_tv_course_name);
        mTvCredit = (TextView) itemView.findViewById(R.id.id_tv_credit);
        mTvYearTerm = (TextView) itemView.findViewById(R.id.id_tv_year_term);
    }
}
