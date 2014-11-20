package com.wi360.sms.marketing.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
 * 客户访问详情
 * 
 * @author Administrator
 * 
 */
public class UserAccessDescActivity extends BaseActivity {

	@ViewInject(R.id.txt_title)
	protected TextView txt_title;

	@Override
	public void initView() {
		view = View.inflate(context, R.layout.layout_user_access_desc, null);
		ViewUtils.inject(this, view);
		txt_title.setText("客户访问详情");

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
