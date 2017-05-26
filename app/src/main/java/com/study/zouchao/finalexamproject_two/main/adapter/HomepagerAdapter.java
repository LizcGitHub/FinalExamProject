package com.study.zouchao.finalexamproject_two.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Mr.Egg on 2017/1/19.
 * 主页viewpager适配器
 */

public class HomepagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragments;

    public HomepagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public HomepagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
