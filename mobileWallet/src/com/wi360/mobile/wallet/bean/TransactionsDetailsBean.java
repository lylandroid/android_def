package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 交易明细
 * 
 * @author Administrator
 * 
 */
public class TransactionsDetailsBean extends BaseBean {
	public TransactionsDetailsBean(Context context, String pan,String pageIndex) {
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp + token);
		this.token = SharedPreferencesUtils.getString(context, "token", "");
		bizData = new BizData(pan, pageIndex, "10", "20140506", "20150510");

	}

	public BizData bizData;
	public String appCode;
	public String timeStamp;
	public String nonce;
	public String signature;
	public String token;

	public class BizData {
		public BizData(String pan, String pageIndex, String pageSize, String startDate, String endDate) {
			this.pan = pan;
			this.pageIndex = pageIndex;
			this.pageSize = pageSize;
			this.startDate = startDate;
			this.endDate = endDate;
		}

		public String pan;
		public String pageSize;
		public String pageIndex;
		public String startDate;
		public String endDate;
	}
}
