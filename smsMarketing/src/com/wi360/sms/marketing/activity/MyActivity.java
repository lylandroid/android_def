package com.wi360.sms.marketing.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.base.BaseActivity;
import com.wi360.sms.marketing.utils.ActivityAnimationUtils;

/**
 * 回拨记录页面
 * 
 * @author Administrator
 * 
 */
public class MyActivity extends BaseActivity {

	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	@Override
	public void initView() {
		view = View.inflate(context, R.layout.layout_my, null);
		ViewUtils.inject(this, view);
		txt_title.setText("我的");
	}

	@Override
	public void initListener() {

	}

	@OnClick(R.id.parent_login)
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.parent_login:
			ActivityAnimationUtils.rightToLeftInAnimation(context, LoginActivity.class);
			break;
		}
	}

	@Override
	public void initValue() {

	}

}
