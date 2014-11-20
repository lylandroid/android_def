package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 激活汇通卡bean
 * 
 * @author Administrator
 * 
 */
public class NewsCardActivateBean extends BaseBean {

	public NewsCardActivateBean(Context context, String pan, String password, String verifyCode) {
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.token = SharedPreferencesUtils.getString(context, "token", "");
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp + token);
		bizData = new BizData(pan, password, verifyCode);

	}

	public String appCode;
	public String timeStamp;
	public String nonce;
	public String signature;
	public String token;
	//

	public BizData bizData;

	public class BizData {

		public BizData(String pan, String password, String verifyCode) {
			this.pan = pan;
			this.password = password;
			this.verifyCode = verifyCode;
		}

		public String pan;
		public String password;
		public String verifyCode;
	}
}
