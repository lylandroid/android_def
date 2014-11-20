package com.wi360.mobile.wallet.utils;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.wi360.mobile.wallet.R;

public class ActivityAnimationUtils {
	/**
	 * activity跳转, 右到左 进入动画
	 * 
	 * @param context
	 * @param intent
	 *            ,activity之间传递数据,null表示不需要传递数据
	 */
	public static void rightToLeftInAnimation(Activity context, Class<?> toClazz, Serializable bean) {
		Intent intent = new Intent(context, toClazz);
		if (bean != null) {
			intent.putExtra("bean", bean);
		}
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/**
	 * 销毁activity, 左到右 出去动画
	 * 
	 * @param context
	 * @param intent
	 */
	public static void leftToRightOutAnimation(final Activity context, Class<?> toClazz, Serializable bean) {
		Intent intent = new Intent(context, toClazz);

		context.startActivity(intent);
		context.overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					context.finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}

	/**
	 * 销毁activity, 左到右 出去动画
	 * 
	 * @param context
	 */
	public static void leftToRightOutAnimation(final Activity context, Class<?> toClazz) {
		leftToRightOutAnimation(context, toClazz, null);
	}

	/**
	 * activity跳转, 右到左 进入动画
	 * 
	 * @param context
	 * @param toClazz
	 */
	public static void rightToLeftInAnimation(Activity context, Class<?> toClazz) {
		rightToLeftInAnimation(context, toClazz, null);
	}
}
