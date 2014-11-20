package com.wi360.sms.marketing.utils;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;

import com.wi360.sms.marketing.R;

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
		// context.overridePendingTransition(0, R.anim.alpha_in);
		context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	/**
	 * 销毁activity, 左到右 出去动画
	 * 
	 * @param context
	 * @param intent
	 */
	public static void leftToRightOutAnimation(final Activity context, Class<?> toClazz, Serializable bean,final boolean isFinish) {
		Intent intent = new Intent(context, toClazz);
		context.startActivity(intent);
		// context.overridePendingTransition(0, R.anim.alpha_out);
		context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					if(isFinish){
						context.finish();
					}
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
		leftToRightOutAnimation(context, toClazz, null,true);
	}
	public static void leftToRightOutAnimation(final Activity context, Class<?> toClazz,boolean isFinish) {
		leftToRightOutAnimation(context, toClazz, null,isFinish);
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
