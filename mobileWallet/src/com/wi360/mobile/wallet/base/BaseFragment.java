package com.wi360.mobile.wallet.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	private View view;
	public Context context;
	public boolean is_load = false;

	// public SlidingMenu sm;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 initData();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getActivity();
		// sm = ((MainActivity)ct).getSlidingMenu();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = initView(inflater);
		return view;
	}

	public View getRootView() {
		return view;
	}

	// 初始化view
	public abstract View initView(LayoutInflater inflater);

	// 初始化数据
	public abstract void initData();

}
