package com.wi360.mobile.wallet.bean;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.bean.MyVolumeBean2.Content;
import com.wi360.mobile.wallet.bean.MyVolumeBean2.UserToken;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

public class MyVolumeDetailBean extends BaseBean {
	public Content content;
	public String appNo = "ZJ206180";
	public String service = "trade.queryBarCodeDetail";
	public String ver = "1.0";

	public MyVolumeDetailBean(Context context, String assisCode) {
		content = new Content(context, assisCode);
	}

	public class Content {
		public String requestType = "chinamobile_nfc";
		public UserToken userToken;
		// 辅助码
		public String assisCode;

		public Content(Context context, String assisCode) {
			this.assisCode = assisCode;
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
		public String nonce;
		public String signature;
		public String timeStamp;
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
//	public Map<String, String> getJson2(Context context, String jsonBeanStr) {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("appNo", "ZJ206180");
//		map.put("service", "trade.queryBarCodeDetail");
//		map.put("ver", ver);
//		map.put("msg", jsonBeanStr);
//		map.put("sign", "bdzriKhpA6y+2iHZmay6bQqSBCc=");
//		
//		return map;
//	}
}
