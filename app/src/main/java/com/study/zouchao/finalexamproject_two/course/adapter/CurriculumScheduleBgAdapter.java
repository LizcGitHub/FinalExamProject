package com.study.zouchao.finalexamproject_two.course.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.study.zouchao.finalexamproject_three.R;

/**
 * Created by Administrator on 2017/5/30.
 */

public class CurriculumScheduleBgAdapter extends BaseAdapter {
    private Context mContext;
    public CurriculumScheduleBgAdapter(Context context) {
        mContext = context;
    }
    @Override
    public int getCount() {
        return 70;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_course_grid_bg, parent, false);
    }
}
