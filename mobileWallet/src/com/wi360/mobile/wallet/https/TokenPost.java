package com.wi360.mobile.wallet.https;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TokenPost {

	private String serverUrl = "https://www.51pme.com/auth";
	// private String serverUrl = "http://localhost:8080/auth";

	private String password;

	private String token;

	private String mobile = "13048868272";

	private String appCode = "zjhtwallet";
	private String appKey = "12345678";

	public TokenPost() {

	}

	
	public static void main(String[] args) {
		TokenPost demo = new TokenPost();
		demo.testGetPassword();
	}

	// @Test
	public void testGetPassword() {

		String requestUrl = serverUrl + "/user/get_smscode";

		JSONObject jsonData = new JSONObject();
		jsonData.put("mobile", mobile);
		jsonData.put("appCode", appCode);
		jsonData.put("time", System.currentTimeMillis());
		jsonData.put("nonce", genRandNum(12));

		String signData = appCode + appKey + jsonData.get("nonce")
				+ jsonData.get("time");
		jsonData.put("sign", DigestUtils.md5Hex(signData));

		System.out.println(signData);

		String result = doPostJSON(requestUrl, jsonData.toJSONString());

		String password = "";
		if (result != null) {
			JSONObject json1 = JSON.parseObject(result);
			int begin = result.indexOf("短信验证码");
			int end = result.indexOf("\",", begin);
			if (begin <= 0) {
				// log.error(result);
				return;
			}

			password = result.substring(begin + 6, end);
			// log.info("短信验证码 = " + password);

			// assertEquals(6, password.length());
		}

		return;
	}

	private long genRandNum(int codeLen) {

		StringBuffer tmpBuff = new StringBuffer("1,2,3,4,5,6,7,8,9,0");

		java.util.Random r = new Random();
		String[] strArray = tmpBuff.toString().split(",");

		StringBuffer resultBuff = new StringBuffer();

		for (int i = 0; i < codeLen; i++) {
			int k = r.nextInt();
			resultBuff.append(String.valueOf(strArray[Math.abs(k % 10)]));
		}
		return Long.parseLong(resultBuff.toString());

	}

	public String doPostJSON(String requestUrl, String jsonData) {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		if (requestUrl.contains("https://")) {
			httpClient = createSSLClient();
		}

		String resultText = null;

		try {

			StringBuffer urlBuffer = new StringBuffer();
			urlBuffer.append(requestUrl);
			// log.info(urlBuffer.toString());

			HttpPost httpPost = new HttpPost(urlBuffer.toString());

			StringEntity jsonStringEntity = new StringEntity(jsonData, "UTF-8");
			jsonStringEntity.setContentEncoding(new BasicHeader(
					HTTP.CONTENT_TYPE, "application/json"));
			httpPost.setEntity(jsonStringEntity);

			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				resultText = EntityUtils.toString(response.getEntity());

			} finally {
				response.close();
			}
		} catch (IOException ex) {
			// log.error(ex.getMessage());
		} finally {

		}

		return resultText;
	}

	public static CloseableHttpClient createSSLClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}

		return HttpClients.createDefault();
	}
}
