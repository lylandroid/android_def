package com.creditpay.util;

import android.util.Log;


public class Logger{
	private final static String TAG="CreditPayManager";
	private final static boolean onLogger=true;
	public static void d(String content){
		if(onLogger){
			Log.d(TAG, content);
		}
	}
}
