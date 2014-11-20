package com.wi360.pay.sdk.bean;

import android.content.Context;
import android.util.Base64;

import com.wi360.pay.sdk.base.BaseBean;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;

/**
 * 发送支付短信的bean
 * 
 * @author Administrator
 * 
 */
public class SendPaySmsBean extends BaseBean {

	/**
	 * TODO 还没有开始写
	 */
	public SendPaySmsBean() {

	}

	public SendPaySmsBean(Context context, int sum, String alias, String productName, String sellerUserId) {

		this.appId = getAppId(context);
		this.sum = sum;
		this.alias = alias;
		// this.usedCredit = DBUtils.getUser(context).getUsedCredit();
		this.usedCredit = SharedPreferencesUtils.getInt(context, Constants.usedCredit, 0);
		this.sellerUserId = sellerUserId;
		this.productName = productName;
		this.signature = getMD5Str(appId + getAppKey(context) + this.sum + this.usedCredit);
	}

	// TODO 5个字节，字母和数字组成,自己给的??????????
	public String appId;
	public int sum;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID
	public String alias;// 是AUTH系统统一分配的APPID
	public String sellerUserId;// 是AUTH系统统一分配的APPID
	public String productName;// 是AUTH系统统一分配的APPID

	public int usedCredit;

	public boolean check() {
		boolean isSuccess = true;
		// 分为单位，最大到千元
		if (sum < 0 || sum > 10000 * 100) {
			isSuccess = false;
		} else if (sellerUserId == null) {
			isSuccess = false;
		}

		if (sellerUserId != null && sellerUserId.length() > 28) {
			sellerUserId = sellerUserId.substring(0, 28);
		}

		if (productName != null) {
			// 默认以英文处理
			if (productName.length() > 28) {
				isSuccess = false;
			}
			// 检测sellerUserId是否包含汉子
			char[] sellerUserIds = sellerUserId.toCharArray();
			for (char c : sellerUserIds) {
				// 如果包含中文,长度不超过14
				if (c > 255 && productName.length() > 14) {
					isSuccess = false;
					break;
				}
			}
		} else {
			isSuccess = false;
		}

		return isSuccess;
	}

	@Override
	public String toString() {
		// String res = "CP,[" + appId + "],[" + sum + "],[" + usedCredit +
		// "],[" + signature + "],[" + alias + "],["
		// + sellerUserId + "],[" + productName + "]";
		String res = "CP," + appId + "," + sum + "," + usedCredit + "," + signature + "," + alias + "," + sellerUserId
				+ "," + Base64.encodeToString(productName.getBytes(), Base64.DEFAULT);
		return res;
	}

}
