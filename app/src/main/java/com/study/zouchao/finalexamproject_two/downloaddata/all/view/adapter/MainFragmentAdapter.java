package com.study.zouchao.finalexamproject_two.downloaddata.all.view.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Fragment适配器
 * @author Tercel
 *
 */
public class MainFragmentAdapter extends FragmentStatePagerAdapter{
	private List<Fragment> data;		
	public MainFragmentAdapter(FragmentManager fm, List<Fragment> data) {
		super(fm);
		this.data = data;
	}

	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return data.get(position);
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}
}
