package com.wi360.mobile.wallet.https;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class NewHttpUtil {
	private static final String TAG = "billy_HttpUtil";

	/**
	 * post方式请求数据.发送的是json数据
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendhttpByPost(String url, String params) {

		HttpPost httpRequest = new HttpPost(url);
		httpRequest.addHeader("Accept-Encoding", "gzip");
		httpRequest.addHeader("Content-Type", "application/json");
		String strResult = null;
		try {
			
			
			// 发出HTTP request
//			httpRequest.setEntity(new StringEntity(params, "UTF-8"));
//			System.out.println("post发送的params是:" + params);
//			url = 
			
			
			HttpClient httpClient = null;
			//判断请求协议方式
			if(url.startsWith("https://")){
				// 取得HTTP response
				httpClient = HttpUtil.wrapClient(new DefaultHttpClient());
			}else{
				httpClient = new DefaultHttpClient();
			}
			//https请求方式
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			// 若状态码为200 ok
			// Content-Encoding
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				Header[] headers = httpResponse.getHeaders("Content-Encoding");
				boolean isUseGzip = false;
				for (Header header : headers) {
					if ("Content-Encoding".equals(header.getName())) {
						if ("gzip".equalsIgnoreCase(header.getValue())) {
							isUseGzip = true;
						}
					}
				}
				if (isUseGzip) {
					strResult = IoUtil.convertStreamToString(new GZIPInputStream(httpResponse.getEntity().getContent()));
				} else {
					strResult = EntityUtils.toString(httpResponse.getEntity());
				}
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return strResult;
	}

	/**
	 * 发送 url get请求 创建URL，并使用URLConnection/HttpURLConnection
	 * 
	 * @param url
	 * @return
	 */
	public static String sendGetByURLConn(String url) {
		String result = "";
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		get.addHeader("Accept-Encoding", "gzip");
		// get.setHeaders(headers);Content-Encoding
		try {
			HttpResponse response = client.execute(get);
			System.out.println(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Header[] headers = response.getHeaders("Content-Encoding");
				boolean isUseGzip = false;
				for (Header header : headers) {
					if ("Content-Encoding".equals(header.getName())) {
						if ("gzip".equalsIgnoreCase(header.getValue())) {
							isUseGzip = true;
						}
					}
				}
				if (isUseGzip) {
					result = IoUtil.convertStreamToString(new GZIPInputStream(response.getEntity().getContent()));
				} else {
					result = EntityUtils.toString(response.getEntity());
				}
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return null;

	}

	/**
	 * http post 请求
	 * 
	 * @param url
	 * @return
	 */
	public static String sendhttpByPost(String url, List<NameValuePair> params) {

		for (int i = 0; i < params.size(); i++) {
			// ////billy-debugSystem.out.println(params.get(i).getName() + ":" +
			// params.get(i).getValue());
		}

		HttpPost httpRequest = new HttpPost(url);
		String strResult = null;
		try {
			// 发出HTTP request
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 取得HTTP response
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

			// 若状态码为200 ok
			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				// 取出回应字串
				strResult = EntityUtils.toString(httpResponse.getEntity());

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return strResult;
	}

	public static String sendhttpByPut(String url, String params) {

		// ////billy-debugSystem.out.println("params:" + params);

		HttpPut httpRequest = new HttpPut(url);
		String strResult = null;
		try {
			// 发出HTTP request
			System.out.println("put发送的params是:" + params);
			httpRequest.setEntity(new StringEntity(params, "UTF-8"));
			// 取得HTTP response
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);

			// 若状态码为200 ok
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 取出回应字串
				strResult = EntityUtils.toString(httpResponse.getEntity());

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return strResult;
	}

	/**
	 * 发送 url get请求 失败有重发逻辑
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGetByURLConn(String url) {
		String retStr = sendGetByURLConn(url);
		try {
			for (int i = 0; i < 3; i++) {
				if (retStr != null)
					break;
				if (i != 0)
					Thread.sleep(500); // 1秒=1000
				retStr = sendGetByURLConn(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStr;
	}

	/**
	 * 获取网络html代码
	 * @param url
	 * @return
	 */
	public String getHtmlCode(String url) {
		String html = null;
		try {
			URL htmlURL = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) htmlURL.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);

			InputStream inStream = conn.getInputStream();

			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			int len = 0;

			while ((len = inStream.read(buffer)) != -1) {

				outStream.write(buffer, 0, len);

			}

			inStream.close();

			byte[] data = outStream.toByteArray();

			html = new String(data, "gbk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}
}