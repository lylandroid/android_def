package com.itheima.mynews35.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		TextView tv = new TextView(context);
		tv.setText("GovAffairsPager  指南");
		return tv;
	}

	@Override
	public void initData() {
//		((MainActivity)context).getSlidingMenu().setMode(SlidingMenu.TOUCHMODE_NONE);
	}

}
