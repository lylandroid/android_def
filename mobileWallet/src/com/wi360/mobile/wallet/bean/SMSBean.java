package com.wi360.mobile.wallet.bean;

import java.util.Date;
import java.util.Random;

import com.wi360.mobile.wallet.utils.Md5Utils;

/**
 * 
 * 获取短信验证码 正常时的返回JSON数据包示例： {"errcode":0,"errmsg":"已经发送短信验证码"} 错误时的JSON数据包示例：
 * {"errcode":1010,"errmsg":"发送短信验证码失败"}
 * 
 * @author Administrator
 * 
 */
public class SMSBean {

	public SMSBean() {
	}

	public SMSBean(String appId, String appKey, String mobileNum) {
		this.appId = appId;
		this.appKey = appKey;
		this.mobileNum = mobileNum;
		this.timeStamp = new Date().getTime() + "";
		this.nonce = new Random().nextInt(1000) + "";
		this.signature = Md5Utils.encode(appId + appKey + nonce + timeStamp);
	}

	public String appId;// 是AUTH系统统一分配的APPID
	public String appKey;// 是AUTH系统统一分配的APPKEY
	public String timeStamp;// 是 时间戳
	public String nonce;// 是 随机数
	public String signature;// "signature":"MD5(appId+appKey+nonce+timeStamp)
	public String mobileNum; // 是 登录的手机号码

	public String smsCode; // 是 通过短信获取到的验证码
	public String errcode;// 状态码
	public String errmsg;// 消息

	@Override
	public String toString() {
		return "{errcode:" + errcode + ", errmsg:" + errmsg + ", appId:"
				+ appId + "appKey:" + appKey + ", timeStamp:" + timeStamp
				+ ", nonce:" + nonce + ", signature:" + signature
				+ ", mobileNum:" + mobileNum + ", smsCode:" + smsCode + "}";
	}

}
