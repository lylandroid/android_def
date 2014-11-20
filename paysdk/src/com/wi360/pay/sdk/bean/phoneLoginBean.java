package com.wi360.pay.sdk.bean;

import android.content.Context;

import com.wi360.pay.sdk.base.BaseBean;
import com.wi360.pay.sdk.util.PhoneUtil;

/**
 * 手机号码登录接口
 * 
 * @author Administrator
 * 
 */
public class phoneLoginBean extends BaseBean {

	
	public phoneLoginBean() {

	}

	public phoneLoginBean(Context context, String mobile,String smsCode) {
		this.appId = getAppId(context);
		this.appKeyHash = getKeyHash(context);
		this.timeStamp = System.currentTimeMillis() + "";
		this.nonce = genRandNum(12) + "";
		this.signature = getMD5Str(appId + getAppKey(context) + nonce + timeStamp);
		loginInfo = new LoginInfo(mobile,smsCode);
		deviceInfo = new DeviceInfo(context);
	}

	public String appId;// 是AUTH系统统一分配的APPID
	public String appKeyHash;// 是AUTH系统统一分配的APPID
	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID

	public LoginInfo loginInfo;
	public DeviceInfo deviceInfo;

	public class LoginInfo {
		
		public LoginInfo(String mobileNum, String smsCode) {
			this.mobileNum = mobileNum;
			this.smsCode = smsCode;
		}
		public String mobileNum;
		public String smsCode;
	}

	public class DeviceInfo {
		
		public DeviceInfo(Context context) {
			this.mac = PhoneUtil.getMacAddress(context);
			this.deviceId =  PhoneUtil.getDeviceId(context);
			this.imsi =  PhoneUtil.getImsi(context);
		}
		public String mac;
		public String deviceId;
		public String imsi;
	}

}
