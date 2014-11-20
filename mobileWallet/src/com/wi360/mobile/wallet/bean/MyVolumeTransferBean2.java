package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 电子劵转让
 * 
 * @author Administrator
 * 
 */
public class MyVolumeTransferBean2 extends BaseBean {

	public MyVolumeTransferBean2(Context context, String assisCode,String mobile) {
		this.assisCode = assisCode;
		this.mobile = mobile;
		this.requestType = getRequestType(context);
		this.userToken = new UserToken(context);
	}

	// 辅助码
	public String assisCode;
	// 电话号码
	public String mobile;
	// 汇通宝APP项目：(requestType=huitongbao，默认)；中行APP项目：
	// (requestType=zhonghang)
	public String requestType;

	public UserToken userToken;

	public class UserToken {

		public UserToken(Context context) {
			this.appId = getAppId(context);
			this.timeStamp = System.currentTimeMillis() + "";
			this.nonce = genRandNum(12) + "";
			this.token = SharedPreferencesUtils.getString(context, "token", "");
			this.signature = getMD5Str(appId + nonce + timeStamp + token);
		}

		public String appId;
		public String nonce;
		public String signature;
		public String timeStamp;
		public String token;
	}
}
