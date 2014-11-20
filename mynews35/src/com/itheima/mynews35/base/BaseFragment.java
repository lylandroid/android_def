package com.itheima.mynews35.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mynews35.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public abstract class BaseFragment extends Fragment {

	protected View view;
	/**
	 *
	 */
	protected Context context;

	protected SlidingMenu slidingMenu;

	/**
	 * 判断启动软件时是否加载过缓存数据
	 */
	protected boolean isCacheData;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		slidingMenu = ((MainActivity) context).getResultSlidingMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = initView(inflater);
		return view;
	}

	public View getRootView() {
		return view;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public abstract View initView(LayoutInflater inflater);

	/**
	 * 
	 */
	public abstract void initData();

}
