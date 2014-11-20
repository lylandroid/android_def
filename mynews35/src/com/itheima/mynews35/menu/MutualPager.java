package com.itheima.mynews35.menu;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.itheima.mynews35.base.BasePager;
/**
 * 新闻中心,互动页面
 * @author Administrator
 *
 */
public class MutualPager extends BasePager {

	public MutualPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		TextView tv = new TextView(context);
		tv.setText("互动");
		return tv;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
