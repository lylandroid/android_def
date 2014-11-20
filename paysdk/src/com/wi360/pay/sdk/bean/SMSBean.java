package com.wi360.pay.sdk.bean;

import android.content.Context;

import com.wi360.pay.sdk.base.BaseBean;


public class SMSBean extends BaseBean{

	public SMSBean() {
	}

	public SMSBean(Context context,String mobile) {
		this.appId = getAppId(context);
		this.mobileNum = mobile;
		this.timeStamp = System.currentTimeMillis()+"";
		this.nonce = genRandNum(12)+"";
		this.signature = getMD5Str(appId + getAppKey(context) + nonce + timeStamp);
		
	}

	
	public String appId;// 是AUTH系统统一分配的APPID
	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID
	public String mobileNum;// 是AUTH系统统一分配的APPID
	

}
