package com.creditpay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.creditpay.client.DeviceInfoParam;
import com.creditpay.client.GetCreditParam;
import com.creditpay.client.GetCreditResp;
import com.creditpay.client.GetSmsParam;
import com.creditpay.client.GetSmsResp;
import com.creditpay.client.LoginInfoParam;
import com.creditpay.client.LoginParam;
import com.creditpay.client.LoginResp;
import com.creditpay.client.Pay;
import com.creditpay.client.PayParam;
import com.creditpay.client.PayResp;
import com.creditpay.component.JsonUtil;
import com.creditpay.component.XmlInterfManager;
import com.creditpay.util.Logger;
import com.creditpay.util.MD5Util;
import com.creditpay.util.PhoneUtil;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;

class CreditPayLogic {
	public CreditPayLogic() {

	}

	public GetSmsResp getSms(String appId, String mobileNum, Context context) {
		String timestamp = (int) ((long) System.currentTimeMillis() / 1000)
				+ "";
		String nonce = getRandom();
		String signature = MD5Util.MD5(appId + getKeyHash(context) + nonce
				+ timestamp);

		GetSmsParam param = new GetSmsParam();
		param.appId = appId;
		param.mobileNum = mobileNum;
		param.timeStamp = timestamp;
		param.nonce = nonce;
		param.signature = signature;

		String json = JsonUtil.toJsonStringByObject(param);
		Logger.d("request=" + json);
		GetSmsResp resp = (GetSmsResp) XmlInterfManager.getInstance()
				.sendRequestBackJson(Constant.GETSMS_URL, json,
						GetSmsResp.class);
		Logger.d("resp=" + resp);
		return resp;
	}

	public LoginResp login(String appId, String mobileNum, String smsCode,
			Context context) {
		String timestamp = (int) ((long) System.currentTimeMillis() / 1000)
				+ "";
		String nonce = getRandom();
		String signature = MD5Util.MD5(appId + getKeyHash(context) + nonce
				+ timestamp);

		LoginParam param = new LoginParam();
		LoginInfoParam loginInfo = new LoginInfoParam();
		DeviceInfoParam deviceInfo = new DeviceInfoParam();
		loginInfo.mobileNum = mobileNum;
		loginInfo.smsCode = smsCode;
		deviceInfo.mac = PhoneUtil.getMacAddress(context);
		deviceInfo.deviceId = PhoneUtil.getDeviceId(context);
		deviceInfo.imsi = PhoneUtil.getImsi(context);
		param.appId = appId;
		param.appKeyHash = getKeyHash(context);
		param.timeStamp = timestamp;
		param.nonce = nonce;
		param.signature = signature;
		param.loginInfo = loginInfo;
		param.deviceInfo = deviceInfo;

		String json = JsonUtil.toJsonStringByObject(param);
		Logger.d("request=" + json);
		LoginResp resp = (LoginResp) XmlInterfManager.getInstance()
				.sendRequestBackJson(Constant.LOGIN_URL, json, LoginResp.class);
		Logger.d("resp=" + resp);
		return resp;
	}

	public PayResp pay(String appId, String mobileNum, String token, int sum,
			String alias, String productName, String sellerUserId,
			Context context) {
		String timestamp = (int) ((long) System.currentTimeMillis() / 1000)
				+ "";
		String nonce = getRandom();
		String signature = MD5Util.MD5(appId + getKeyHash(context) + nonce
				+ timestamp+token);

		PayParam param = new PayParam();
		Pay pay = new Pay();
		pay.sum = sum;
		pay.alias = alias;
		pay.productName = productName;
		pay.sellerUserId = sellerUserId;
		param.appId=appId;
		param.pay = pay;
		param.timeStamp = timestamp;
		param.nonce = nonce;
		param.signature = signature;
		param.token = token;

		String json = JsonUtil.toJsonStringByObject(param);
		Logger.d("request=" + json);
		PayResp resp = (PayResp) XmlInterfManager.getInstance()
				.sendRequestBackJson(Constant.PAY_URL, json, PayResp.class);
		Logger.d("resp=" + resp);
		return resp;
	}

	public GetCreditResp getCredit(String appId, String mobileNum,
			String token, Context context) {
		String timestamp = (int) ((long) System.currentTimeMillis() / 1000)
				+ "";
		String nonce = getRandom();
		String signature = MD5Util.MD5(appId + getKeyHash(context) + nonce
				+ timestamp);

		GetCreditParam param = new GetCreditParam();
		param.appId = appId;
		param.mobileNum = mobileNum;
		param.timeStamp = timestamp;
		param.nonce = nonce;
		param.signature = signature;
		param.token = token;
		
		String json = JsonUtil.toJsonStringByObject(param);
		Logger.d("request=" + json);
		GetCreditResp resp = (GetCreditResp) XmlInterfManager.getInstance()
				.sendRequestBackJson(Constant.GETCREDIT_URL, json, GetCreditResp.class);
		Logger.d("resp=" + resp);
		return resp;
	}

	public String getKeyHash(Context context) {
		String keyhash = null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(info.signatures[0].toByteArray());
			keyhash = Base64.encodeToString(md.digest(), Base64.DEFAULT)
					.toString().trim();
			Logger.d("keyHash=" + keyhash);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyhash;
	}

	public String getRandom() {
		String nonce = "";
		for (int i = 0; i < 5; i++) {
			nonce += new Random().nextInt(10);
		}
		return nonce;
	}
}
