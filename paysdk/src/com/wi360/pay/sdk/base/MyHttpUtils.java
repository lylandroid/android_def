package com.wi360.pay.sdk.base;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wi360.pay.sdk.interfaces.MyRequestCallBack;

public class MyHttpUtils {
	public static RequestParams getParams(String json) {
		RequestParams params = new RequestParams();
		params.addHeader("Content-Type", "application/json");
		try {
			StringEntity entity = new StringEntity(json, "UTF-8");
			// entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
			// "application/json"));
			entity.setContentType("application/json");
			entity.setContentEncoding("UTF-8");
			params.setBodyEntity(entity);
			return params;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 * @param json
	 * @param filePath上传的文件
	 * @param requestCall
	 * @return
	 */
	public static String sendPost(String url, String json, String filePath, final MyRequestCallBack requestCall) {
		RequestParams params = getParams(json);
		if (filePath != null) {
			params.addBodyParameter("file", filePath);
		}

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException error, String msg) {
				requestCall.onFailure(error, msg);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				requestCall.onSuccess(responseInfo);
			}
		});
		return null;
	}

}
