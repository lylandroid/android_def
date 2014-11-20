package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

public class GetTokenBean extends BaseBean {

	public String appCode;
	public String nonce;
	public String signature;
	public String timeStamp;

	public UserToken userToken;

	public GetTokenBean(Context context) {
		appCode = getAppCode(context);
		nonce = genRandNum(12) + "";
		timeStamp = System.currentTimeMillis() + "";
		signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp);
		userToken = new UserToken(context);
	}

	public class UserToken {

		public UserToken(Context context) {
			this.appCode = getAppCode(context);
			this.timeStamp = System.currentTimeMillis() + "";
			this.nonce = genRandNum(12) + "";
			this.token = SharedPreferencesUtils.getString(context, "token", "");
			this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp + token);
		}

		public String appCode;
		public String timeStamp;
		public String nonce;
		public String signature;
		public String token;
	}
}
