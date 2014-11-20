package com.itheima.mobileguard.activities;

import android.os.Bundle;
import android.view.View;

import com.itheima.mobileguard.R;

public class Setup1Activity extends BaseSetupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}

	@Override
	public void showNext() {
		startActivityAndFinishSelf(Setup2Activity.class);
	}

	@Override
	public void showPre() {
		
	}
	
}
