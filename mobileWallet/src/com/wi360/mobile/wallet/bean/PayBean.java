package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 手机支付bean
 * 
 * @author Administrator
 * 
 */
public class PayBean extends BaseBean {

	public PayBean(Context context, String mobileNum, String sum) {
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.token = SharedPreferencesUtils.getString(context, Constants.token, "");
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp);
		bizData = new BizData(mobileNum, sum);
	}

	public String appCode;
	public String nonce;
	public String signature;
	public String timeStamp;
	public String token;

	public BizData bizData;

	public class BizData {

		public BizData(String mobileNum, String sum) {
			this.mobileNum = mobileNum;
			this.amount = sum;
		}

		public String mobileNum;
		public String amount;
	}
}
