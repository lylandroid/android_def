package com.wi360.mobile.wallet.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.application.BaseApplication;

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

	/**
	 * 弹出圆形进度条,
	 * 
	 * @param context
	 * @param title
	 *            ,进度条title
	 * @param msg
	 *            ,进度条显示内容
	 * @return 用于销毁进度条 pd.dismiss();
	 */
	public static ProgressDialog showProgressBar(Context context, String title,
			String msg) {
		ProgressDialog pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setProgressStyle(R.style.NoBorderDialog);
		// pd.setCustomTitle(null);
		// View view = View.inflate(context, R.layout.pb_layout_round, null);
		// TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
		// tv_msg.setText(msg);
		// pd.setContentView(view);
		// pd.setTitle(title);
		// pd.setTitle("提示对话框");
		pd.setMessage(msg);
		// pd.setMax(100);
		pd.show();
		// pd.dismiss();
		return pd;
	}

	/** 获取文字 */
	public static String getString(int resId) {
		return getResources().getString(resId);
	}
	
	/** 获取资源 */
	public static Resources getResources() {
		return getContext().getResources();
	}
	
	public static Context getContext() {
		return BaseApplication.getApplication();
	}

}
