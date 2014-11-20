package com.wi360.pay.sdk.bean;

import java.io.Serializable;

import android.content.Context;

import com.wi360.pay.sdk.base.BaseBean;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;

/**
 * 支付 订单接口
 * 
 * @author Administrator
 * 
 */
public class PayOrderBean extends BaseBean {

	public PayOrderBean() {

	}

	public PayOrderBean(Context context, int sum, String alias, String productName, String sellerUserId) {
		this.appId = getAppId(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.token = SharedPreferencesUtils.getString(context, Constants.token, "");
//		this.token = DBUtils.getUser(context).getToken();
		this.signature = getMD5Str(appId + getAppKey(context) + nonce + timeStamp + token);
		pay = new Pay(sum, alias, productName, sellerUserId);
	}

	public String appId;// 是AUTH系统统一分配的APPID
	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String token;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID

	public Pay pay;

	public class Pay implements Serializable {
		public Pay(int sum, String alias, String productName, String sellerUserId) {
			this.sum = sum;
			this.alias = alias;
			this.productName = productName;
			this.sellerUserId = sellerUserId;
		}

		public int sum;
		public String alias;
		public String productName;
		public String sellerUserId;

		/**
		 * 检查数据格式是否真确
		 * 
		 * @return
		 */
		public int check() {
			if (productName != null) {
				// 默认以英文处理
				if (productName.length() > 28) {
					productName = productName.substring(0, 28);
				}
			} else {
				return 405;
			}
			// 分为单位，最大到千元
			if (sum <= 0 || sum > 1000 * 1000) {
				return 402;
			}
			if(sellerUserId == null) {
				return  403;
			}

			if (sellerUserId != null && sellerUserId.length() > 28) {
				sellerUserId = sellerUserId.substring(0, 28);
			}
			
			// 检测sellerUserId是否包含汉子
			char[] sellerUserIds = sellerUserId.toCharArray();
			for (char c : sellerUserIds) {
				// 如果包含中文
				if (c > 255) {
					return 405;
				}
			}

			return 0;
		}
	}

}
