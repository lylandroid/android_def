package com.itheima.mynews35.menu;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.itheima.mynews35.base.BasePager;
/**
 * 组图页面
 * @author Administrator
 *
 */
public class PicPager extends BasePager {

	public PicPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		TextView tv = new TextView(context);
		tv.setText("组图");

		return tv;
	}

	@Override
	public void initData() {

	}

}
