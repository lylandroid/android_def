package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;
//{"pan":"1234123412341234123",
//"appCode":"dianziquan",
//"timeStamp":"TIMESTAMP", 
//"nonce":"NONCE",
//"signature":"MD5(appCode+appKey+nonce+timeStamp+token)",
//"token":"qpoiwreq99eekkeuurqwerq23ewrm"}

public class CardDetailBean  extends BaseBean{
	public CardDetailBean(Context context,String pan) {
		this.appCode = getAppCode(context);
		this.timeStamp = System.currentTimeMillis()+"";
		this.nonce = genRandNum(12)+"";
		this.token = SharedPreferencesUtils.getString(context, "token", "");
		this.signature = getMD5Str(appCode + getAppKey(context) + nonce + timeStamp+token);
		this.pan = pan;
		
	}
	
	public String appCode;
	public String timeStamp;
	public String nonce;
	public String signature;
	public String token;
	public String pan;
	
	
}
