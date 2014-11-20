package com.wi360.mobile.wallet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: http utils
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author LIJUNJIE
 * @version 1.0
 */
public class HttpUtils {

	private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static final int SIZE = 1024 * 1024;

	private HttpUtils() {
	}

	/**
	 * GET METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static String URLGet(String strUrl, Map map) throws IOException {
		String strTotalURL = "";
		StringBuffer result = new StringBuffer();
		String params = getUrl(map);
		if (strUrl.indexOf("?") == -1) {
			strTotalURL = StringUtils.isBlank(params) ? strUrl : (strUrl + "?" + params);
		} else {
			strTotalURL = StringUtils.isBlank(params) ? strUrl : (strUrl + "&" + params);
		}
		URL url = new URL(strTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setFollowRedirects(true);
		InputStream is = con.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"), SIZE);
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else {
				result.append(line);
			}
		}
		if (in != null) {
			in.close();
		}
		if (is != null) {
			is.close();
		}
		if (con != null) {
			con.disconnect();
		}
		return result.toString();
	}

	/**
	 * POST METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param content
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String URLPost(String strUrl, Map map) throws IOException {
		// strUrl="http://localhost:8080/front/ccr/t.do";
		String params = "";
		params = getUrl(map);
		// System.out.println();
		// System.out.println("params   : " + params);
		String totalURL = strUrl;
		StringBuffer sbuffer = new StringBuffer();

		// if (strUrl.indexOf("?") == -1) {
		// totalURL = strUrl + (StringUtils.isBlank(params)?"":"?" + params);
		// } else {
		// totalURL = strUrl + (StringUtils.isBlank(params)?"":"&" + params);
		// }
		// log.debug("Post Url:" + totalURL);
		// URL url = new URL(totalURL);
		// HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// con.setDoInput(true);
		// con.setDoOutput(true);
		// con.setAllowUserInteraction(false);
		// con.setUseCaches(false);
		// con.setRequestMethod("POST");
		// con.setConnectTimeout(30000);//设置连接主机超时 三十秒等待时间
		// con.setReadTimeout(30000);//设置从主机读取数据超时 三十秒等待时间
		// con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		// con.getOutputStream().write(params.getBytes());
		// con.getOutputStream().flush();
		// con.getOutputStream().close();
		// InputStream is=con.getInputStream();
		// BufferedReader bin = new BufferedReader(new
		// InputStreamReader(is,"UTF-8"), SIZE);
		// while (true) {
		// String line = bin.readLine();
		// if (line == null) {
		// break;
		// } else {
		// sbuffer.append(line);
		// }
		// }
		// if (bin!=null) {
		// bin.close();
		// }
		// if (is!=null) {
		// is.close();
		// }
		// if (con!=null) {
		// con.disconnect();
		// }
		String s = sendPost(totalURL, params);
		sbuffer.append(s);
		return sbuffer.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @author yangxiaoyong
	 * @version 创建时间：2014年8月26日 下午5:04:37 参考 www.sql8.net
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 
	 * @param map
	 *            Map
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	private static String getUrl(Map map) {
		if (null == map || map.keySet().size() == 0) {
			return "";
		}
		StringBuffer url = new StringBuffer();
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = String.valueOf(i.next());
			if (map.containsKey(key)) {
				Object val = map.get(key);
				String str = val != null ? val.toString() : "";
				try {
					str = URLEncoder.encode(str, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// System.out.print(key+":"+val+"\t");
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		// strURL =
		// "sign=HD2mb01aLbzH3DPVudGc6v92xmw%3D&appNo=ZJ206180&service=trade.queryBarCodeList&ver=1.0&msg=%7B%22content%22%3A%7B%22userToken%22%3A%7B%22appId%22%3A%22dianziquan%22%2C%22timeStamp%22%3A%221415608012094%22%2C%22token%22%3A%22784Fl1HRIarDGYeZX92GBbyoZc228tPB%22%2C%22nonce%22%3A%22909459952602%22%2C%22signature%22%3A%22fa47986a0b1b1224ba4dbdc274b03e71%22%7D%2C%22pageSize%22%3A10%2C%22requestType%22%3A%22chinamobile_nfc%22%2C%22pageIndex%22%3A2%7D%2C%22appNo%22%3A%22ZJ206180%22%2C%22service%22%3A%22trade.queryBarCodeList%22%2C%22ver%22%3A%221.0%22%7D";
		if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}

}
