package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;

/**
 * 
 * 获取短信验证码 正常时的返回JSON数据包示例： {"errcode":0,"errmsg":"已经发送短信验证码"} 错误时的JSON数据包示例：
 * {"errcode":1010,"errmsg":"发送短信验证码失败"}
 * 
 * @author Administrator
 * 
 */
public class SMSBean2 extends BaseBean{

	public SMSBean2() {
	}

	public SMSBean2(Context context,String mobile) {
		this.mobileNum = mobile;
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis()+"";
		this.nonce = genRandNum(12)+"";
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp);
		
	}

	public String mobileNum;// 是AUTH系统统一分配的APPID
	public String appCode;// 是AUTH系统统一分配的APPID
	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID
	

}
