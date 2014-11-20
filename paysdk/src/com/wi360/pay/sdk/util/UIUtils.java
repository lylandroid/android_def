package com.wi360.pay.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wi360.pay.sdk.R;

public class UIUtils {
	/**
	 * 显示自己的toast,定义自己的布局
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 * @return
	 */
	public static Toast myMakeText(Context context, CharSequence text,
			int duration) {
		Toast result = new Toast(context);

		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.toast_showaddress, null);
		TextView tv = (TextView) v.findViewById(R.id.tv_toast_address);
		tv.setText(text);
		result.setView(v);
		result.setDuration(duration);
		result.setGravity(Gravity.CENTER, 0, 50);

		return result;
	}

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




}
