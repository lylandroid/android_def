package com.wi360.mobile.wallet.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.Md5Utils;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 
 * 获取短信验证码 正常时的返回JSON数据包示例： {"errcode":0,"errmsg":"已经发送短信验证码"} 错误时的JSON数据包示例：
 * {"errcode":1010,"errmsg":"发送短信验证码失败"}
 * 
 * @author Administrator
 * 
 */
public class UpdateUserInfoBean extends BaseBean {

	public UpdateUserInfoBean() {
	}

	public UpdateUserInfoBean(Context context) {
		this.appCode = getAppCode(context);
//		this.appKey = "12345678";
		this.timeStamp = System.currentTimeMillis()+"";
		this.nonce = genRandNum(12)+"";
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp);
		this.token = SharedPreferencesUtils.getString(context, Constants.token, "");
	}

	public String appCode;// 是AUTH系统统一分配的APPID
//	public String appKey;
	public String timeStamp;// 是AUTH系统统一分配的APPID
	public String nonce;// 是AUTH系统统一分配的APPID
	public String signature;// 是AUTH系统统一分配的APPID
	
	public String token;
	
	public UserInfoBean userInfo = new UserInfoBean();
	
	public class UserInfoBean{
		public String name="我是女神";
		public String headPic;
	}

}
