package com.wi360.pay.sdk;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wi360.pay.sdk.base.QidaDialog;
import com.wi360.pay.sdk.bean.PayOrderBean.Pay;
import com.wi360.pay.sdk.bean.ResultBean;
import com.wi360.pay.sdk.bean.ResultBean.Order;
import com.wi360.pay.sdk.bean.SendPaySmsBean;
import com.wi360.pay.sdk.interfaces.ResponseCallback;
import com.wi360.pay.sdk.util.CommonUtil;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;

/**
 * 发送短信
 * 
 * @author Administrator
 * 
 */
public class SendSmsController {
	private View view;
	private Activity context;

	private String url;
	private String json;
	private Button bt_submit;
	private Button bt_cancel;
	private SendPaySmsBean paySmsBean;

	private Pay payBean;
	private ResponseCallback responseCallback;
	// 是否发送过短信
	private boolean is_send_sms;

	private final static String ACTION_SMS_SEND = "com.jifei.sendAction";

	public SendSmsController(Activity context, final Dialog dialog, View view, Pay payBean,
			ResponseCallback responseCallback) {
		this.context = context;
		this.view = view;
		this.payBean = payBean;
		this.responseCallback = responseCallback;
		TextView tv_title = (TextView) this.view.findViewById(R.id.tv_title);
		tv_title.setText("短信模式");
		bt_submit = (Button) this.view.findViewById(R.id.bt_submit);
		bt_cancel = (Button) this.view.findViewById(R.id.bt_cancel);
		TextView tv_desc = (TextView) this.view.findViewById(R.id.tv_desc);
		// tv_desc.setText(Html
		// .fromHtml("我们将发送1条短信（运营商收费0.1元）系统将弹出是否允许应用发送短信的界面，请选择“<font color=#EA7914 >允许</font>”，注意如果选择“禁止”，该游戏后续无法采用短信进行信用支付。"));
		tv_desc.setText(Html
				.fromHtml("您选择“短信模式”完成本次信用支付，系统将提示您选择“是否允许此应用发送短信”，请选择“<font color=#EA7914 >允许</font>”，如选择“禁止”，此应用后续将无法采用短信完成信用支付，请正确操作，避免对您后续使用产生不便！感谢您对信用支付的信赖！"));
		// 用户使用短信方式支付
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SmsManager smsManager = SmsManager.getDefault();
				PendingIntent sentIntent = PendingIntent.getBroadcast(SendSmsController.this.context, 0, new Intent(
						ACTION_SMS_SEND), 0);
				paySmsBean = new SendPaySmsBean(SendSmsController.this.context, PayController.orderBaen.pay.sum,
						PayController.orderBaen.pay.alias, PayController.orderBaen.pay.productName,
						PayController.orderBaen.pay.sellerUserId);
				if (!paySmsBean.check()) {
					Toast.makeText(SendSmsController.this.context, "请检查短信内容", 0).show();
					return;
				}
				String phone = "10657563219310";
				// String phone = "13048868272";
				// smsManager.sendTextMessage(phone, null, "测试", sentPI(),
				// deliverPI());
				is_send_sms = true;
				smsManager.sendTextMessage(phone, null, paySmsBean.toString(), sentPI(), deliverPI());
				// Toast.makeText(SendSmsController.this.context, "等待实现",
				// 0).show();
				dialog.dismiss();
			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 取消发送短信
				((QidaDialog) dialog).onClickCancleBut();
				dialog.dismiss();
			}

		});
	}

	public PendingIntent sentPI() {
		String SENT_SMS_ACTION = "SENT_SMS_ACTION";
		Intent sentIntent = new Intent(SENT_SMS_ACTION);
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent, 0);
		// register the Broadcast Receivers
		context.registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context _context, Intent _intent) {
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					// Toast.makeText(context, "短信发送成功",
					// Toast.LENGTH_SHORT).show();

					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					if (responseCallback != null) {
						responseCallback.responseStateCode(Constants.pay_code_failure);
					}
					Toast.makeText(context, "短信发送失败", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					if (responseCallback != null) {
						responseCallback.responseStateCode(Constants.pay_code_failure);
					}
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					if (responseCallback != null) {
						responseCallback.responseStateCode(Constants.pay_code_failure);
					}
					// Toast.makeText(context, "短信发送失败",
					// Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}, new IntentFilter(SENT_SMS_ACTION));

		return sentPI;
	}

	private PendingIntent deliverPI() {
		String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
		// create the deilverIntent parameter
		Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
		PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, deliverIntent, 0);
		context.registerReceiver(new BroadcastReceiver() {
			// 对方已经成功接收到短信
			@Override
			public void onReceive(Context _context, Intent _intent) {
				if (is_send_sms) {
					int sum = paySmsBean.sum;
//					User2 user = DBUtils.getUser(_context);
//					user.setUsedCredit(user.getUsedCredit() + sum);
//					DBUtils.updateUser(_context, user);
					int usedCredit = SharedPreferencesUtils.getInt(context, Constants.usedCredit, 0);
					SharedPreferencesUtils.saveInt(_context, Constants.usedCredit, usedCredit + sum);
					if (responseCallback != null) {
						responseCallback.responseStateCode(Constants.pay_code_success);
					}
					// Toast.makeText(context, "短信发送成功",
					// Toast.LENGTH_SHORT).show();
					// 弹出支付成功对话框
					QidaDialog dialog = new QidaDialog(context, R.layout.dialog_pay_success, R.style.QidaDialog,
							responseCallback);
					PaySuccessController paySuccessController = new PaySuccessController(context, dialog, dialog.view,
							responseCallback);

					ResultBean resBean = new ResultBean();
					Order order = resBean.new Order();
					order.productName = payBean.productName;
					order.sum = payBean.sum;
					order.buyerId = null;
					order.payTime = CommonUtil.getStringDate();
					order.status = "支付成功";
					resBean.order = order;

					paySuccessController.setData(resBean);

					is_send_sms = false;
				}
			}
		}, new IntentFilter(DELIVERED_SMS_ACTION));
		return deliverPI;

	}

	/**
	 * 发送短信
	 * 
	 * @param billingInfo
	 */
	private void sendSms(String phone, String content) {
		new Thread() {
			public void run() {
				try {
					String content = "1111111111111111111111111";// 短信内容
					String phone = "15666666666666666";// 电话号码
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(phone, null, content, null, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
