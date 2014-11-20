package com.wi360.mobile.wallet;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;

/**
 * 关于我们
 * 
 * @author Administrator
 * 
 */
public class AboutMyActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		setContentView(R.layout.layout_about_us);
		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("关于我们");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);
		
		//按返回按钮的时候销毁activity和返回键一样的工作
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myOnKeyDown();
			}
		});
	}

	@Override
	public boolean initData() {
		return false;
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(AboutMyActivity.this,
				MainActivity.class);
		return true;
	}

}
