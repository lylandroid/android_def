package com.wi360.pay.sdk.bean;

import android.content.Context;

import com.wi360.pay.sdk.base.BaseBean;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;

/**
 * 获取信用信息接口Bean
 * 
 * @author Administrator
 * 
 */
public class GetPayBean extends BaseBean {

	public GetPayBean() {

	}

	public GetPayBean(Context context) {
		this.appId = getAppId(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.mobileNum = SharedPreferencesUtils.getString(context, Constants.mobileNum, "");
		this.token = SharedPreferencesUtils.getString(context, Constants.token, "");
		// this.mobileNum = DBUtils.getUser(context).getMobileNum();
		// this.token = DBUtils.getUser(context).getToken();
		this.signature = getMD5Str(appId + getAppKey(context) + nonce + timeStamp + token);
	}

	public String appId;// 是AUTH系统统一分配的APPID
	public String mobileNum;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID
	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String token;// 是AUTH系统统一分配的APPID

}
