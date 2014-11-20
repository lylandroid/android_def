/**
 * 文件名称 : XmlInterfManager.java
 * <p>
 * 作者信息 : yangxiongjie
 * <p>
 * 创建时间 : 2013-3-15, 上午9:35:18
 * <p>
 * 版权声明 : Copyright (c) 2009-2012 Hydb Ltd. All rights reserved
 * <p>
 * 评审记录 :
 * <p>
 */

package com.creditpay.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.creditpay.util.Logger;

/**
 * xml接口管理器，管理发送请求和解析xml文件
 * <p>
 */
public final class XmlInterfManager {

	private static final int TIME_OUT = 60 * 1000;

	private static String TAG = XmlInterfManager.class.getSimpleName();

	private static XmlInterfManager xmlInterfManager;

	private XmlInterfManager() {

	}

	public static XmlInterfManager getInstance() {
		if (xmlInterfManager == null) {
			xmlInterfManager = new XmlInterfManager();
		}
		return xmlInterfManager;
	}

	public static void destroy() {
		xmlInterfManager = null;
	}

	public static void initManager() {
		xmlInterfManager = new XmlInterfManager();
	}

	/**
	 * 发送请求，返回的是json数据
	 * 
	 * @param url
	 *            请求url
	 * @param param
	 *            请求参数
	 * @return
	 */
	public Object sendRequestBackJson(String url, String jsonParam,
			Class<?> c) {
		String result = null;
		result = postRequest(url, jsonParam);
		if (result == null) {
			return null;
		}
		return JsonUtil.simpleJsonToObject(result, c);
	}


	/**
	 * 发送post请求
	 * 
	 * @param uef
	 * @return
	 */
	private String postRequest(String url, String data) {
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(data, "utf-8"));
			DefaultHttpClient client = new DefaultHttpClient();
			// 请求超时
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					TIME_OUT);
			HttpResponse resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(resp.getEntity(), "utf-8");
				Logger.d(url + "\n" + data + "\n===============\n"
						+ result);
				return result;
			} else {
				Logger.d(url + "\n================\n retCode="
						+ resp.getStatusLine().getStatusCode());
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			Logger.d("json post request UnsupportedEncodingException");
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			Logger.d("json post request ClientProtocolException");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Logger.d("json post request IOException");
			e.printStackTrace();
			return null;
		}
	}

}
