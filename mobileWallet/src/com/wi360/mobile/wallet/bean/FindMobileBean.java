package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 5.4.2 查询话费充值接口
 * 
 * @author Administrator
 * 
 */
public class FindMobileBean extends BaseBean {

	public FindMobileBean(Context context, String mobileNum) {
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.token = SharedPreferencesUtils.getString(context, Constants.token, "");
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp);
		bizData = new BizData(mobileNum);

	}

	public String appCode;
	public String token;
	public String nonce;
	public String signature;
	public String timeStamp;

	public BizData bizData;

	public class BizData {

		public BizData(String mobileNum) {
			this.mobileNum = mobileNum;
		}

		public String mobileNum;
	}
}
