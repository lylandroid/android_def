package com.wi360.mobile.wallet.https;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();

	// public static final String BASE_URL = "http://192.168.1.88:8888/auction/android/";

	public static String getRequest(String url, Map<String, String> headers) throws Exception {

		HttpGet get = new HttpGet(url);
		if (headers != null && headers.size() > 0) {
			// 设置头信息
			for (Map.Entry<String, String> info : headers.entrySet()) {
				get.setHeader(info.getKey(), info.getValue());
			}
		}
		// 发送get请求
		HttpClient wrapClient = wrapClient(httpClient);
		HttpResponse httpResponse = wrapClient.execute(get);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}

	public static String postRequest(String url, Map<String, String> rawParams, Map<String, String> headers) throws Exception {
		HttpPost post = new HttpPost(url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// 设置请求参数
		post.setEntity(new UrlEncodedFormEntity(params, "gbk"));
		if (headers != null && headers.size() > 0) {
			// 设置头信息
			for (Map.Entry<String, String> info : headers.entrySet()) {
				post.setHeader(info.getKey(), info.getValue());
			}
		}
		// 发送POST请求
		HttpClient wrapClient = wrapClient(httpClient);
		HttpResponse httpResponse = wrapClient.execute(post);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}

	public static String postRequest(String url, String paramString, Map<String, String> headers) throws Exception {
		HttpPost post = new HttpPost(url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		post.setEntity(new StringEntity(paramString, "UTF-8"));
		// 设置请求参数
		if (headers != null && headers.size() > 0) {
			// 设置头信息
			for (Map.Entry<String, String> info : headers.entrySet()) {
				post.setHeader(info.getKey(), info.getValue());
			}
		}
		// 发送POST请求
		HttpClient wrapClient = wrapClient(httpClient);
		HttpResponse httpResponse = wrapClient.execute(post);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			String result = EntityUtils.toString(httpResponse.getEntity());
			return result;
		}
		return null;
	}

	/**
	 * 
	 * 通常，你会像下面那样来创建HttpClient：

	我们将需要告诉client使用一个不同的TrustManager。TrustManager
	（http://download.oracle.com/docs/cd/E17476_01/javase/1.5.0/docs/api/javax/net/ssl/TrustManager.html）
	是一个检查给定的证书是否有效的类。SSL使用的模式是X.509（http://en.wikipedia.org/wiki/X.509），
	对于该模式Java有一个特定的TrustManager，称为X509TrustManager。首先我们需要创建这样的TrustManager。

	下面我们需要找到一个将TrustManager设置到我们的HttpClient的方法。
	TrustManager只是被SSL的Socket所使用。Socket通过SocketFactory创建。
	对于SSL Socket，有一个SSLSocketFactory
	（http://download.oracle.com/docs/cd/E17476_01/javase/1.5.0/docs/api/javax/net/ssl/SSLSocketFactory.html）。
	当创建新的SSLSocketFactory时，你需要传入SSLContext到它的构造方法中。在SSLContext中，我们将包含我们新创建的TrustManager。

	首先我们需要得到一个SSLContext：

	TLS是SSL的继承者，但是它们使用相同的SSLContext。

	然后我们需要使用我们上面新创建的TrustManager来初始化该上下文：

	最后我们创建SSLSocketFactory：

	现在我们仍然需要将SSLSocketFactory注册到我们的HttpClient上。这是在SchemeRegistry中完成的：

	我们注册了一个新的Scheme，使用协议https，我们新创建的SSLSocketFactory包含了我们的TrustManager，
	然后我们告诉HttpClienthttps的默认端口是443.
	 * */
	public static HttpClient wrapClient(HttpClient base) {
		try {
			//SSL使用的模式是X.509
			//对于该模式Java有一个特定的TrustManager，称为X509TrustManager。首先我们需要创建这样的TrustManage
			//TLS是SSL的继承者，但是它们使用相同的SSLContext。
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			//将TrustManager设置到我们的HttpClient的方法
			ctx.init(null, new TrustManager[] { tm }, null);
//			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
//			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
//			sr.register(new Scheme("https", ssf, 443));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception ex) {
			return null;
		}
	}
}
