package com.example.googleplay35.fragment;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;

public class FragmentFactory {
	// 首页的fragment
	public static final int TAB_HOME = 0;
	// 应用
	public static final int TAB_APP = 1;
	// 游戏
	public static final int TAB_GAME = 2;
	// 专题
	public static final int TAB_SUBJECT = 3;
	// 推荐
	public static final int TAB_RECOMMEND = 4;
	// 分类
	public static final int TAB_CATEGORY = 5;
	// 排行
	public static final int TAB_TOP = 6;
    //把fragment缓存的内存
	private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();
	private static BaseFragment fragment;

	public static BaseFragment createFragment(int position) {
		//我看看内存里面是否有fragment，如果没有，然后就创建一个fragment，如果有，就直接返回
		fragment = mFragments.get(position);
		if (fragment == null) {
			switch (position) {
			case TAB_HOME:
				fragment = new HomeFragment();
				break;

			case TAB_APP:
				fragment = new AppFragment();
				break;
			case TAB_GAME:
				fragment = new GameFragment();
				break;
			case TAB_SUBJECT:
				fragment = new SubjectFragment();
				break;
			case TAB_RECOMMEND:
				fragment = new RecommendFragment();
				break;
			case TAB_CATEGORY:
				fragment = new CategoryFragment();
				break;
			case TAB_TOP:
				fragment = new TopFragment();
				break;
			}

			mFragments.put(position, fragment);
		}

		return fragment;
	}

}
