package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

public class BindCardBean extends BaseBean {

	public BindCardBean(Context context, String pan, String password) {
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.token = SharedPreferencesUtils.getString(context, Constants.token, "");
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp + token);
		bizData = new BizData(pan, password);

	}

	public String appCode;
	public String timeStamp;
	public String nonce;
	public String signature;
	public String token;

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
