package com.wi360.mobile.wallet.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NetsWorkUtils {
	
	public void loadData(HttpMethod method, String url, RequestParams params,
			RequestCallBack<String> callBack) {
		HttpUtils httpUtils = new HttpUtils();
		if (params == null) {
			params = new RequestParams();
		}

		httpUtils.send(method, url, params, callBack);
	}
}
