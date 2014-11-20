package com.wi360.mobile.wallet.base;

import java.io.File;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.view.View;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public abstract class BasePager {

	protected View view;
	public Context context;
	public boolean is_load = false;
	protected String fileDir = null;
	//是否调用onstart,默认不被调用
	protected boolean isOnstart= false;

	// public SlidingMenu sm;
	public BasePager(Context context) {
		this.context = context;
		fileDir = context.getFilesDir().getAbsolutePath();
		view = initView();
		// sm = ((MainActivity)context).getSlidingMenu();
	}

	public View getRootView() {
		onstart();
		return view;
	}

	public void initTitleBar(View view) {
		// Button btn_left = (Button) view.findViewById(R.id.btn_left);
		// btn_left.setVisibility(View.GONE);
		// ImageButton imgbtn_left = (ImageButton)
		// view.findViewById(R.id.imgbtn_left);
		// imgbtn_left.setImageResource(R.drawable.img_menu);
		// txt_title = (TextView) view.findViewById(R.id.txt_title);
		//
		// ImageButton btn_right = (ImageButton)
		// view.findViewById(R.id.btn_right);
		// btn_right.setVisibility(View.GONE);
	}

	public void loadData(HttpMethod method, String url, RequestParams params,
			RequestCallBack<String> callBack) {
		HttpUtils httpUtils = new HttpUtils();
		if (params == null) {
			params = new RequestParams();
		}

		httpUtils.send(method, url, params, callBack);
	}

	public abstract View initView();

	public abstract void initData();

	/**
	 * 模拟activity中onstart方法,在主线程中执行,不要做耗时操作
	 */
	public void onstart() {
	}

}
