package com.itheima.mynews35.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SettingPager extends BasePager {

	public SettingPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
//		((MainActivity)context).getSlidingMenu().setMode(SlidingMenu.TOUCHMODE_FULLSCREEN);
		TextView tv = new TextView(context);
		tv.setText("SettingPager 设置");
		return tv;
	}

	@Override
	public void initData() {

	}

}
