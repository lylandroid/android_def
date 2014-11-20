package com.wi360.mobile.wallet.interfaces;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

/*
 * 连接网络回调方法
 */
public abstract class MyRequestCallBack {
	public abstract void onFailure(HttpException error, String msg);

	/**
	 * 成功返回json数据
	 * 
	 * @param responseInfo
	 * @return
	 */
	public abstract void onSuccess(ResponseInfo<String> responseInfo);
}