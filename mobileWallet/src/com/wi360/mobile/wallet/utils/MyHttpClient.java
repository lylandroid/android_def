package com.wi360.mobile.wallet.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wi360.mobile.wallet.https.HttpHelper;
import com.wi360.mobile.wallet.https.HttpHelper.HttpResult;

public class MyHttpClient<T> {
	
	private String serverUrl = "http://www.51pme.com/auth";
	//private String serverUrl = "http://localhost:8080/auth";
	
	private String password;
	//这一个是可以的
	private String token;
	
	private String mobile="13912345678";
	
	private String appCode = "zjhtwallet";	
	private String appKey = "12345678";

	/**
	 * 客户端向服务端发送请求
	 * 
	 * @param url
	 *            :请求地址
	 * @param t
	 *            :请求对象
	 * @return:返回json
	 */
	public T sendPost(String url, T t) {
		
		
//		JSONObject jsonData = new JSONObject();
//		jsonData.put("mobile", mobile);
//		jsonData.put("appCode", appCode);
//		jsonData.put("time", System.currentTimeMillis());
//		jsonData.put("nonce",  genRandNum(12));
//			
//		String signData = appCode + appKey + jsonData.get("nonce") + jsonData.get("time");		
//		jsonData.put("sign", Md5Utils.encode(signData));
		
		
		
		Gson gson = new Gson();
		String json = gson.toJson(t);
		byte[] jsonByte = null;
		try {
			jsonByte = json.getBytes(Constants.charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		HttpResult httpResult = HttpHelper.post(url, jsonByte);
		
		
		String resJson = null;
		if(httpResult !=null){
			resJson = httpResult.getString();
			T resT = (T) gson.fromJson(resJson, t.getClass());
			httpResult.close();
			return null;
		}
		return null;
	}
	
	
public static long genRandNum(int codeLen){
		
		StringBuffer tmpBuff = new StringBuffer("1,2,3,4,5,6,7,8,9,0");		

		java.util.Random r = new Random();
		String[] strArray = tmpBuff.toString().split(",");		
		
		StringBuffer resultBuff = new StringBuffer();		
		
		for(int i=0;i < codeLen;i++){
			 int k = r.nextInt();
			 resultBuff.append(String.valueOf(strArray[Math.abs(k % 10)]));
		}		
		return Long.parseLong(resultBuff.toString());
		
	}
}
