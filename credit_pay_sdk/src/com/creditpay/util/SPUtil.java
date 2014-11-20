/**
 * File Name:SPUtil.java
 * Package Name:com.zzkko.util
 * author:yangxiongjie
 * Date:2014-1-17下午4:38:42
 * Copyright (c) 2014, zzkko All Rights Reserved.
 */
package com.creditpay.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * simple desrciption
 */
public class SPUtil {
	private final static String SHAREDPREFERENCENAME = "creditpay";

	// 保存token和imsi
	public static void saveTokenAndImsi(Context context, String token,
			String imsi, int creditLimit, int usedCredit,String mobileNum) {
		SharedPreferences sp = context.getSharedPreferences(
				SHAREDPREFERENCENAME, 0);
		Editor editor = sp.edit();
		editor.putString("token", token);
		editor.putString("imsi", imsi);
		editor.putInt("creditLimit", creditLimit);
		editor.putInt("usedCredit", usedCredit);
		editor.putString("mobileNum", mobileNum);
		editor.commit();
	}
	
	public static void saveCredit(Context context,int creditLimit, int usedCredit){
		SharedPreferences sp = context.getSharedPreferences(
				SHAREDPREFERENCENAME, 0);
		Editor editor = sp.edit();
		editor.putInt("creditLimit", creditLimit);
		editor.putInt("usedCredit", usedCredit);
		editor.commit();
	}

	// 获取token
	public static String getToken(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				SHAREDPREFERENCENAME, 0);
		return sp.getString("token", null);
	}
	
	public static String getMobileNum(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				SHAREDPREFERENCENAME, 0);
		return sp.getString("mobileNum", null);
	}

	// 获取历史的imsi
	public static String getOldImsi(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				SHAREDPREFERENCENAME, 0);
		return sp.getString("imsi", null);
	}

	// 获取历史的imsi
	public static int getCreditLimit(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				SHAREDPREFERENCENAME, 0);
		return sp.getInt("creditLimit", 0);
	}

	// 获取历史的imsi
	public static int getUsedCredit(Context context) {
		SharedPreferences sp = context.getSharedPreferences(
				SHAREDPREFERENCENAME, 0);
		return sp.getInt("usedCredit", 0);
	}
}
