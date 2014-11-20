package com.wi360.mobile.wallet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public abstract class BaseActivity extends Activity {

	protected AsyncTask asyncTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	public abstract void initView();

	public abstract boolean initData();



}
