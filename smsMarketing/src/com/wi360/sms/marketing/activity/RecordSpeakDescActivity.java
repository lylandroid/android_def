package com.wi360.sms.marketing.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.base.BaseActivity;

/**
 * 记录通话详情
 * 
 * @author Administrator
 * 
 */
public class RecordSpeakDescActivity extends BaseActivity {

	@ViewInject(R.id.txt_title)
	protected TextView txt_title;

	@Override
	public void initView() {
		view = View.inflate(context, R.layout.layout_record_speak_desc, null);
		ViewUtils.inject(this, view);
		txt_title.setText("记录通话详情");

	}

	@Override
	public void initListener() {

	}

	@Override
	public void initValue() {
	}

	@Override
	public boolean myOnKeyDown(int keyCode, KeyEvent event, Class<?>... clazz) {
		return super.myOnKeyDown(keyCode, event, PotentialUserActivity.class);
	}

}
