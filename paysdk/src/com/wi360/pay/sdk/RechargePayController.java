package com.wi360.pay.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wi360.pay.sdk.base.QidaDialog;
import com.wi360.pay.sdk.bean.PayOrderBean;
import com.wi360.pay.sdk.interfaces.ResponseCallback;
import com.wi360.pay.sdk.util.CommonUtil;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;

/**
 * 立即充值
 * 
 * @author Administrator
 * 
 */
public class RechargePayController {
	private View view;
	private Activity context;

	private TextView tv_name;

	private TextView tv_momey;

	private TextView tv_desc;

	private Button bt_submit;

	private QidaDialog dialog;
	private PayOrderBean orderBean;

	private com.wi360.pay.sdk.bean.PayOrderBean.Pay payBean;
	private ResponseCallback responseCallback;
	// webViewRechargeActivity回调回到支付dialog
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			com.wi360.pay.sdk.interfaces.Pay payIn = PayFactory.getInstance(context);
			payIn.creditPay(payBean.productName, payBean.sum, payBean.alias, payBean.sellerUserId,
					PayController.responseCallback);
		};
	};

	public RechargePayController(Activity mContext, final Dialog dialog, View view,
			com.wi360.pay.sdk.bean.PayOrderBean.Pay payBean, ResponseCallback responseCallback) {
		this.context = mContext;
		this.dialog = (QidaDialog) dialog;
		this.view = view;
		this.payBean = payBean;
		this.responseCallback = responseCallback;
		if (dialog != null && !dialog.isShowing()) {
			dialog.show();
		}

		TextView tv_title = (TextView) this.view.findViewById(R.id.tv_title);
		tv_title.setText("博升信用支付");

		tv_name = (TextView) this.view.findViewById(R.id.tv_name);
		tv_momey = (TextView) this.view.findViewById(R.id.tv_momey);
		tv_desc = (TextView) this.view.findViewById(R.id.tv_desc);
		bt_submit = (Button) this.view.findViewById(R.id.bt_submit);

		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 如果没有网络,跳转到选择网络页面
				if (CommonUtil.isNetworkAvailable(context) == 0) {// 没有网络
					isNotNetWork();
				} else {
					// 有网络直接进入支付页面
					Intent intent = new Intent(RechargePayController.this.context, WebViewRechargeActivity.class);
					intent.putExtra("url", "http://183.232.65.119:8881/paycredit/login.action");
					intent.putExtra("messenger", new Messenger(handler));
					RechargePayController.this.context.startActivity(intent);
					dialog.dismiss();
				}
			}
		});
		this.view.findViewById(R.id.bt_cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				((QidaDialog) dialog).onClickCancleBut();
				dialog.dismiss();
			}
		});
	}

	public void setData(PayOrderBean payOrderBean) {
		this.orderBean = payOrderBean;
		tv_name.setText(payOrderBean.pay.productName);
		tv_momey.setText(String.format("%.2f", payOrderBean.pay.sum * 0.01) + "元");
		int momey = SharedPreferencesUtils.getInt(RechargePayController.this.context, Constants.creditLimit, 0)
				- SharedPreferencesUtils.getInt(RechargePayController.this.context, Constants.usedCredit, 0);
//		User2 user = DBUtils.getUser(context);
//		int momey = user.getCreditLimit() - user.getUsedCredit();
		tv_desc.setText(Html.fromHtml("你的信用额度不足,当前可用额度<font color=#EA7914>" + String.format("%.2f", momey * 0.01)
				+ "</font>元"));
	}

	/**
	 * 点击立即充值的时候,如果没有网络
	 */
	private void isNotNetWork() {
		final QidaDialog dialog_temp = new QidaDialog(context, R.layout.dialog_confirmation, R.style.QidaDialog,
				RechargePayController.this.responseCallback);
		View view = dialog_temp.view;
		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		tv_desc.setText("立即充值需要手机上网，请打开网络");
		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				dialog_temp.dismiss();
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						RechargePayController.this.dialog.show();
					}
				}.sendEmptyMessageDelayed(0, 1000);

			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RechargePayController.this.dialog.onClickCancleBut();
				dialog_temp.dismiss();
			}
		});
		RechargePayController.this.dialog.dismiss();
		dialog_temp.show();
	}

}
