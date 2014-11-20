package com.wi360.sms.marketing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

public class UIUtils {
	/**
	 * 显示自己的toast
	 */
	public static void showToast(final Activity context, final String msg) {
		if ("main".equals(Thread.currentThread().getName())) {
			Toast.makeText(context, msg, 0).show();
		} else {
			context.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context, msg, 0).show();
				}
			});
		}
	}


//	/** 获取文字 */
//	public static String getString(int resId) {
//		return getResources().getString(resId);
//	}
	
//	/** 获取资源 */
//	public static Resources getResources() {
//		return getContext().getResources();
//	}
	
//	public static Context getContext() {
//		return BaseApplication.getApplication();
//	}

}
