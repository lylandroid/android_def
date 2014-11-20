package com.wi360.mobile.wallet.menu;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wi360.mobile.wallet.base.BasePager;

public class PicPager extends BasePager {

	public PicPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView() {
		TextView textView = new TextView(context);
		textView.setText("组图");
		return textView;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
