package com.wi360.sms.marketing.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.base.BaseActivity;
import com.wi360.sms.marketing.base.MyAsyncTask;
import com.wi360.sms.marketing.base.MyBaseAdapter;
import com.wi360.sms.marketing.bean.ResBean;

/**
 * 回拨记录详情
 * 
 * @author Administrator
 * 
 */
public class BackRecordDescActivity extends BaseActivity {

	@ViewInject(R.id.txt_title)
	protected TextView txt_title;
	
	@ViewInject(R.id.bt_submit)
	private Button bt_delete;

	@Override
	public void initView() {
		view = View.inflate(context, R.layout.layout_back_record_desc, null);
		ViewUtils.inject(this, view);
		txt_title.setText("回拨记录详情");
		bt_delete.setText("删  除");

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
