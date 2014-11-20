package com.wi360.mobile.wallet.bean;

import java.util.HashMap;
import java.util.Map;

import android.R;
import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

public class MyVolumeBean2 extends BaseBean {

	public MyVolumeBean2(Context context, String pageIndex, String pageSize) {
		content = new Content(context, pageIndex, pageSize);
		
		
		
	}

	// // 暂时不需要
	// public String operType;
	// // 暂时不需要
	// public String operValue;
	// // 暂时不需要
	// public String password;

	public Content content;
	public String appNo = "ZJ206180";
	public String service = "trade.queryBarCodeList";
	public String ver = "1.0";

	public class Content {
		public String pageIndex = "1";
		public String pageSize = "10";
		public String requestType = "chinamobile_nfc";
		public UserToken userToken;

		public Content(Context context, String pageIndex, String pageSize) {
			this.pageIndex = pageIndex;
			this.pageSize = pageSize;
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
		public String token ;
	}

	public Map<String, String> getJson(Context context, String jsonBeanStr) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", jsonBeanStr);
		map.put("sign", genSign(getKeyZJHT(context), jsonBeanStr));

		// result.append("{sign=" + sign);//
		// result.append(",appNo=" + appNo);//
		// result.append(",service=" + service);//
		// result.append(",ver=" + ver);//
		// result.append(",msg=");//
		// result.append(jsonBeanStr);//
		// result.append("}");

		return map;
	}
}
