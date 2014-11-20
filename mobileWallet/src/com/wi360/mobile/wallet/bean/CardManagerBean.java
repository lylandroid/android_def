package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

public class CardManagerBean extends BaseBean {
	public CardManagerBean(Context context) {
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.token = SharedPreferencesUtils.getString(context, "token", "");
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp+token);

	}

	public String appCode;
	public String timeStamp;
	public String nonce;
	public String signature;
	public String token;
}
