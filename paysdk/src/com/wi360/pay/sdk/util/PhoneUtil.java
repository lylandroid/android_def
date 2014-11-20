/**
 * File Name:PhoneUtil.java
 * Package Name:com.zzkko.util
 * author:yangxiongjie
 * Date:2014-1-17下午5:31:18
 * Copyright (c) 2014, zzkko All Rights Reserved.
 */
package com.wi360.pay.sdk.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 获取手机设备信息类
 */
public class PhoneUtil {

	/**
	 * 获取手机厂商
	 * 
	 * @author yangxiongjie
	 * @return
	 * @since JDK 1.6
	 */
	public static String getVendor() {
		return android.os.Build.MANUFACTURER;
	}

	/**
	 * 获取设备android，ios
	 * 
	 * @author yangxiongjie
	 * @return
	 * @since JDK 1.6
	 */
	public static String getOs() {
		return "android";
	}

	/**
	 * 获取系统版本号
	 * 
	 * @author yangxiongjie
	 * @return
	 * @since JDK 1.6
	 */
	public static String getOsver() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取设备型号
	 * 
	 * @author yangxiongjie
	 * @return
	 * @since JDK 1.6
	 */
	public static String getDevice() {
		return android.os.Build.MODEL;
	}

	public static String getImsi(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();
		return imsi;
	}

	public static String getDeviceId(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getDeviceId();
	}

	public static String getPackageName(Context context) {
		return context.getApplicationInfo().packageName;
	}

	public static int getProvidersName(Context c) {
		int Providers = -1;
		String imsi = getImsi(c);
		if (imsi == null || "".equals(imsi)) {
			return Providers;
		}
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
			Providers = 1;
		} else if (imsi.startsWith("46001")) {
			Providers = 2;
		} else if (imsi.startsWith("46003")) {
			Providers = 3;
		}
		return Providers;
	}

	public static String getMacAddress(Context c) {
		WifiManager wifiMgr = (WifiManager) c
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			return info.getMacAddress();
		} else {
			return "";
		}
	}

	public static String getMobile(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}
}
