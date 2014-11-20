package com.wi360.mobile.wallet.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.Md5Utils;

/**
 * 
 * 获取短信验证码 正常时的返回JSON数据包示例： {"errcode":0,"errmsg":"已经发送短信验证码"} 错误时的JSON数据包示例：
 * {"errcode":1010,"errmsg":"发送短信验证码失败"}
 * 
 * @author Administrator
 * 
 */
public class LoginBean extends BaseBean{

	public LoginBean() {
	}

	public LoginBean(Context context,String mobile,String smsCode) {
		this.mobileNum = mobile;
		this.appCode = getAppCode(context);
		this.smsCode = smsCode;
		this.timeStamp = System.currentTimeMillis()+"";
		this.nonce = genRandNum(12)+"";
		String appkey = getAppKey(context);
		this.signature = getMD5Str(appCode + appkey + nonce + timeStamp);
		
	}

	public String mobileNum;// 是AUTH系统统一分配的APPID
	public String appCode;// 是AUTH系统统一分配的APPID
	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID
	
	public String smsCode;

}
