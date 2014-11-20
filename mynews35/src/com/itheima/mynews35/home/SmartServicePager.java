package com.itheima.mynews35.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SmartServicePager extends BasePager {

	public SmartServicePager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		TextView tv = new TextView(context);
		tv.setText("SmartServicePager 服务");
		return tv;
	}

	@Override
	public void initData() {
//		((MainActivity)context).getSlidingMenu().setMode(SlidingMenu.TOUCHMODE_FULLSCREEN);

	}

}
