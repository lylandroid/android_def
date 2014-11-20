package com.wi360.pay.sdk;

import android.app.Activity;

import com.wi360.pay.sdk.base.QidaDialog;

/**
 * 支付工厂,通过PayFactory.getInstance(Activity context)
 * 
 * @author Administrator
 * 
 */
public class PayFactory {
	/**
	 * 
	 * @param context
	 *            :上下文环境(Activity context)
	 * @return PayController(支付控制器)
	 */
	public static PayController getInstance(Activity context) {
		QidaDialog dialog = new QidaDialog(context, R.layout.dialog_pay1, R.style.QidaDialog,null);
		PayController payController = new PayController(context, dialog, dialog.view);
		return payController;
	}
}
