package com.wi360.mobile.wallet.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


public class ZjhtClient2 {
	
	/**
	 * SHA-1绛惧悕
	 */
	public static String ALGORITHM="SHA-1";
	private static final String ver= "1.0";
	
	private static final String appNo="ZJ206180";
	private static final String key="wZ4q/r9o7633wbyd9w3WpA6RTO/C+BUN";
	private static final String url= "http://121.8.155.5:9060/zjhtplatform/";
	
	private void send(String service,JSONObject contentJson)throws IOException, JSONException{
		JSONObject json=new JSONObject();
		json.put("content", contentJson);
		json.put("ver", ver);
		json.put("service", service);
		json.put("appNo", appNo);
		Map<String,String> map=new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", json.toString());
		System.out.println(key);
		map.put("sign", genSign(key, json.toString()));
		StringBuffer serviceUrl=new StringBuffer(url);
		serviceUrl.append(service).append("/").append(ver).append("/");
		System.out.println(serviceUrl);
		System.out.println("map:"+map);
		JSONObject response=new JSONObject(HttpUtils.URLPost(serviceUrl.toString(), map));
		if (response.has("content")) {
			System.out.println("content:"+response.getJSONObject("content"));
		}
	}
	
	
	public void queryAllCopons() throws JSONException, IOException{
		String service= "trade.queryAllCoupons";
		/** 鍙傛暟鏋勯�  **/
		JSONObject contentJson = new JSONObject();
		contentJson.put("pageSize", 10);
		contentJson.put("pageIndex", 1);
		/**  msg鍙傛暟缁撴瀯鏋勯�  **/
		send(service,contentJson);
	}
	
	public void getTokenId1() throws JSONException, IOException{
		String service= "channel.getTokenId";
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("verifyAccntType", "01");
		contentJson.put("verifyAccntValue", "7252656553930850840");
		contentJson.put("verifyPwd", "7jDxfhxZ9Wz3beKOTcWsqQr+fKE=");
		/**  msg鍙傛暟缁撴瀯鏋勯�  **/
		send(service,contentJson);
	}
	
	public void getTokenId2() throws JSONException, IOException{
		String service= "channel.getTokenId";
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("verifyAccntType", "01");
		contentJson.put("verifyAccntValue", "6660718000241200100");
		contentJson.put("verifyPwd", "I1mXhHqi4Z6cOpY2wR/uPhKufvdd0crPprAT9Rd9c8pHwTmiWNfsmPEw3UpxmXM5X8Isa+hhxMGHlhs3xTdIXDGonuSQd+MBSrHhS0144yLLkJ49Nss1QXfQ9RbfSgG6Z2YQUagfDnxz1wkscx3PTZNOzFzImQGZuc2v2MJaJOE=");
		/**  msg鍙傛暟缁撴瀯鏋勯�  **/
		send(service,contentJson);
	}

	
	public void getTokenId3() throws JSONException, IOException{
		String service= "channel.getTokenId";
		
		JSONObject contentJson = new JSONObject();
		contentJson.put("verifyAccntType", "01");
		contentJson.put("verifyAccntValue", "6660718905600019333");
		contentJson.put("verifyPwd", "CLhWlG3OG9OE/Pt1rCkS1iKBJSw2sJbElPRjjsU9S7mJqTPochY9dCI2ZtzH8+LQ2KM9kjzI6YXWckJMbURDayWCKYtFMVLC9BzalWlZBa9z/tMt2VilQEcWv2VVpYyYPF8cKb66c6WevdmAXTVW+tffBzqY4qd0ct5bYAkh4ts=");
		/**  msg鍙傛暟缁撴瀯鏋勯�  **/
		send(service,contentJson);
	}
	
	
	public void queryBarCodeList() throws JSONException, IOException{
		String service= "trade.queryBarCodeList";
		/** 鍙傛暟鏋勯�  **/
		JSONObject contentJson = new JSONObject();
		contentJson.put("pageSize", 10);
		contentJson.put("pageIndex", 2);
		contentJson.put("requestType", "chinamobile_nfc");
		
		Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String TIMESTAMP_STR = sdf.format(TIMESTAMP);
        Map requestMap = new HashMap();
        requestMap.put("appId", "dianziquan");
        requestMap.put("timeStamp", "1415608012094");
        requestMap.put("nonce","909459952602");
        requestMap.put("signature", "fa47986a0b1b1224ba4dbdc274b03e71");
        requestMap.put("token", "784Fl1HRIarDGYeZX92GBbyoZc228tPB");
        
        contentJson.put("userToken", requestMap);
		
		
        send(service,contentJson);
	}
	
	
	public void queryBarCodeDetail() throws JSONException, IOException{
        String service = "trade.queryBarCodeDetail";
        JSONObject contentJson = new JSONObject();
        contentJson.put("assisCode","3765775287991322");
        contentJson.put("requestType","chinamobile_nfc");
//        contentJson.put("operType", "02");
//        contentJson.put("operValue", "15989248048");
//        contentJson.put("password", "e10adc3949ba59abbe56e057f20f883e");

        Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String TIMESTAMP_STR = sdf.format(TIMESTAMP);
        Map requestMap = new HashMap();
        requestMap.put("appId", "dianziquan");
        requestMap.put("timeStamp", TIMESTAMP_STR);
        requestMap.put("nonce","NONCE");
        requestMap.put("signature", MessageDigestHelper.encodeByMD5("dianziquan" + TIMESTAMP_STR + "qpoiwreq99eekkeuurqwerq23ewrm", "UTF-8"));
        requestMap.put("token", "qpoiwreq99eekkeuurqwerq23ewrm");
        
        contentJson.put("userToken", requestMap);
		
		
        send(service,contentJson);
	}
	
	
	public void assignBarCode() throws JSONException, IOException{
        String service = "trade.assignBarCode";
        JSONObject contentJson = new JSONObject();
        contentJson.put("assisCode","3765775287991322");
        contentJson.put("requestType","chinamobile_nfc");
        contentJson.put("mobile", "13560090812");
//        contentJson.put("operValue", "15989248048");
//        contentJson.put("password", "e10adc3949ba59abbe56e057f20f883e");

        Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String TIMESTAMP_STR = sdf.format(TIMESTAMP);
        Map requestMap = new HashMap();
        requestMap.put("appId", "dianziquan");
        requestMap.put("timeStamp", TIMESTAMP_STR);
        requestMap.put("nonce","NONCE");
        requestMap.put("signature", MessageDigestHelper.encodeByMD5("dianziquan" + TIMESTAMP_STR + "qpoiwreq99eekkeuurqwerq23ewrm", "UTF-8"));
        requestMap.put("token", "qpoiwreq99eekkeuurqwerq23ewrm");
        
        contentJson.put("userToken", requestMap);
		
		
        send(service,contentJson);
	}
	

	public static void main(String[] args) throws JSONException, IOException{
		ZjhtClient2 client = new ZjhtClient2();
//		client.queryAllCopons();
//		client.getTokenId1();
//		client.getTokenId2();
//		client.getTokenId3();
		client.queryBarCodeList();
//		client.queryBarCodeDetail();
//		client.assignBarCode();
	
	}
	
	/**
	 * 鐢熸垚绛惧悕
	 * @param key 缁堢鎺ュ叆瀵嗛挜
	 * @param msg msg鍙傛暟锛坖son鏍煎紡锛�
	 * @return
	 */
	public static String genSign(String key,String msg){
		return MessageDigestHelper.encode(ALGORITHM, key+msg+key, null);
	}
}
