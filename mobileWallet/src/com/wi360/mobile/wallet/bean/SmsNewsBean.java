package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 新卡激活页面,发送验证码bean
 * 
 * @author Administrator
 * 
 */
public class SmsNewsBean extends BaseBean {

	public SmsNewsBean() {
	}

	public SmsNewsBean(Context context, String pan, String password) {
		this.appCode = getAppCode(context);
		// this.appKey = "12345678";
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.token = SharedPreferencesUtils.getString(context, Constants.token, "");
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp + token);
		this.bizData = new BizData(pan, password);
	}

	public String appCode;// 是AUTH系统统一分配的APPID
	public String token;// 是AUTH系统统一分配的APPID

	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID

	public BizData bizData;

	public class BizData {

		public BizData(String pan, String password) {
			this.pan = pan;
			this.password = password;
		}

		public String pan;
		public String password;
	}
}
