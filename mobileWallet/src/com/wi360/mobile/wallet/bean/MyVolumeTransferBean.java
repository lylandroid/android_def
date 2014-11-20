package com.wi360.mobile.wallet.bean;

import java.util.HashMap;
import java.util.Map;

import android.R;
import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

public class MyVolumeTransferBean extends BaseBean {

	public MyVolumeTransferBean(Context context, String assisCode, String mobile) {
		content = new Content(context, assisCode, mobile);

	}

	// // 暂时不需要
	// public String operType;
	// // 暂时不需要
	// public String operValue;
	// // 暂时不需要
	// public String password;

	public Content content;
	public String appNo = "ZJ206180";
	public String service = "trade.assignBarCode";
	public String ver = "1.0";

	public class Content {
		public String requestType = "chinamobile_nfc";
		public UserToken userToken;
		public String mobile;
		// 辅助码
		public String assisCode;

		public Content(Context context, String assisCode, String mobile) {
			this.assisCode = assisCode;
			this.mobile = mobile;
			userToken = new UserToken(context);
		}

	}

	public class UserToken {

		public UserToken(Context context) {
			this.appCode = getAppCode(context);
			this.timeStamp = System.currentTimeMillis() + "";
			this.nonce = genRandNum(12) + "";
			this.token = SharedPreferencesUtils.getString(context, "token", "");
			this.signature = getMD5Str(appCode + nonce + timeStamp + token);
		}

		public String appCode;
		public String timeStamp;
		public String nonce;
		public String signature;
		public String token;
	}

	public Map<String, String> getJson(Context context, String jsonBeanStr) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", jsonBeanStr);
		map.put("sign", genSign(getKeyZJHT(context), jsonBeanStr));

		return map;
	}
}
