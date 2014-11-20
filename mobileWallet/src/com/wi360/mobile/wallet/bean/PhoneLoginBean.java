package com.wi360.mobile.wallet.bean;

import java.io.Serializable;

import com.wi360.mobile.wallet.base.BaseBean;

/**
 * 手机登陆号码登录bean
 * 
 * @author Administrator
 * 
 */
public class PhoneLoginBean extends BaseBean {

	public String appId;// 是AUTH系统统一分配的APPID
	public String timeStamp;// 是 时间戳
	public String nonce;// 是 随机数
	public String signature;// 是 签名值，以AUTH系统为APPID分配的APPKEY参与MD5运算的签名值
	public String mobileNum; // 是 登录的手机号码
	public String smsCode; // 是 通过短信获取到的验证码

	@Override
	public String toString() {
		return "{appId:" + appId + ", timeStamp:" + timeStamp + ", nonce:"
				+ nonce + ", signature:" + signature + ", mobileNum:"
				+ mobileNum + ", smsCode:" + smsCode + "}";
	}

}
