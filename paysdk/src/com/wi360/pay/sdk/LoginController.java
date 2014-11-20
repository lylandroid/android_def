package com.wi360.pay.sdk;

import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wi360.pay.sdk.base.MyAsyncTask;
import com.wi360.pay.sdk.base.QidaDialog;
import com.wi360.pay.sdk.bean.PayOrderBean.Pay;
import com.wi360.pay.sdk.bean.ResultBean;
import com.wi360.pay.sdk.bean.SMSBean;
import com.wi360.pay.sdk.bean.phoneLoginBean;
import com.wi360.pay.sdk.interfaces.ResponseCallback;
import com.wi360.pay.sdk.util.CheckUtils;
import com.wi360.pay.sdk.util.CommonUtil;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.GsonTools;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;
import com.wi360.pay.sdk.util.StringUtils;
import com.wi360.pay.sdk.util.UIUtils;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class LoginController {
	private View view;
	private Activity context;

	private EditText et_phone_number;

	private Button bt_get_code;

	private EditText et_sms_code;

	private Button bt_submit;
	private QidaDialog dialog;

	private ResponseCallback responseCallback;

	private Pay pay;

	public LoginController(Activity context, final Dialog dialog, View view, Pay pay, ResponseCallback responseCallback) {
		this.context = context;
		this.view = view;
		this.dialog = (QidaDialog) dialog;
		this.pay = pay;
		this.responseCallback = responseCallback;
		et_phone_number = (EditText) this.view.findViewById(R.id.et_phone_number);
		bt_get_code = (Button) this.view.findViewById(R.id.bt_get_code);
		et_sms_code = (EditText) this.view.findViewById(R.id.et_sms_code);
		bt_submit = (Button) this.view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) this.view.findViewById(R.id.bt_cancel);

		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (CommonUtil.isNetworkAvailable((Context) LoginController.this.context) == 0) {
					UIUtils.showToast(LoginController.this.context, "请打开网络");
				} else {
					login();
				}

			}

		});
		bt_get_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// getCode();
				String phone = et_phone_number.getText().toString().trim();
				// UIUtils.showToast(LoginController.this.context, phone);
				if (!CheckUtils.checkMobileNO(LoginController.this.context, phone)) {
					// 如果电话号码无效
					return;
				}
				SMSBean smsBean = new SMSBean(LoginController.this.context, phone);
				String json = GsonTools.createGsonString(smsBean);
				// 发送验证码前检查网络
				if (CommonUtil.isNetworkAvailable((Context) LoginController.this.context) == 0) {
					// UIUtils.showToast(LoginController.this.context, "请打开网络");
					isNotNetWork();
				} else {
					sendSmsCode(Constants.SEND_SMS_URL, json);
				}
			}

		});

		// 点击取消
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				LoginController.this.dialog.onClickCancleBut();
			}
		});
	}

	/**
	 * 登陆
	 * 
	 * @param lOGIN_URL
	 * @param json
	 */
	private void login() {
		boolean isSendSmsCode = SharedPreferencesUtils.getBoolean(context, Constants.IS_SEND_SMS_CODE_SUCCESS, false);
		// 如果没有发送短信验证码,提示先发送短信验证码
		if (!isSendSmsCode) {
			UIUtils.showToast(context, "请获取短信验证码");
			return;
		}
		String smsCode = et_sms_code.getText().toString().trim();
		// 检测电话号码
		String phone = et_phone_number.getText().toString().trim();
		UIUtils.showToast(LoginController.this.context, phone);
		if (!CheckUtils.checkMobileNO(LoginController.this.context, phone)) {
			// 如果电话号码无效
			return;
		}
		// 判断验证码是否填写
		if (StringUtils.isEmpty(smsCode)) {
			UIUtils.showToast(context, "请填写验证码");
			return;
		}

		phoneLoginBean loginBean = new phoneLoginBean(context, phone, smsCode);
		String json = GsonTools.createGsonString(loginBean);

		new MyAsyncTask<ResultBean>(context, null, null, responseCallback) {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					resBean = GsonTools.changeGsonToBean(responseInfo.result, ResultBean.class);
					if (resBean.errcode == 0) {
						 SharedPreferencesUtils.saveInt(context,
						 Constants.creditLimit, resBean.credit.creditLimit);
						 SharedPreferencesUtils.saveInt(context,
						 Constants.usedCredit, resBean.credit.usedCredit);
						 SharedPreferencesUtils.saveString(context,
						 Constants.token, resBean.token);
						 SharedPreferencesUtils.saveString(context,
						 Constants.mobileNum, resBean.credit.mobileNum);
//						User2 user = new User2(0, resBean.token, resBean.credit.mobileNum, resBean.credit.creditLimit,
//								resBean.credit.usedCredit);
//						DBUtils.saveUser(context, user);

						SharedPreferencesUtils.saveBoolean(context, Constants.IS_SEND_SMS_CODE_SUCCESS, false);
						UIUtils.showToast(context, resBean.errmsg);
						LoginController.this.dialog.dismiss();
						// 登陆成功后跳转到,跳转到支付页面
						com.wi360.pay.sdk.interfaces.Pay payin = PayFactory.getInstance(context);
						payin.creditPay(pay.productName, pay.sum, pay.alias, pay.sellerUserId, responseCallback);
					} else {
						UIUtils.showToast(context, resBean.errmsg);
					}
				}
				return null;
			}

			protected void onPostExecute(String msg) {
			};
		}.execute(new String[] { Constants.LOGIN_URL, json });

	}

	/**
	 * 发送短信验证码
	 * 
	 * @param url
	 * @param json
	 */
	private void sendSmsCode(String url, String json) {
		long timeDate = new Date().getTime() - SharedPreferencesUtils.getLong(context, Constants.end_sms_code_time);
		if (timeDate < 60 * 1000) {
			UIUtils.showToast(context, "60秒内不能重复发送验证码");
			return;
		}
		new MyAsyncTask<ResultBean>(context, null, null, responseCallback) {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					resBean = GsonTools.changeGsonToBean(responseInfo.result, ResultBean.class);
					if (resBean.errcode == 0) {
						// SharedPreferencesUtils.saveLong(context,
						// Constants.SEND_SMS_CODE_TIEM, new Date().getTime());
						// 保存验证码发送成功状态
						SharedPreferencesUtils.saveBoolean(context, Constants.IS_SEND_SMS_CODE_SUCCESS, true);
						SharedPreferencesUtils.saveLong(context, Constants.end_sms_code_time, new Date().getTime());
						// 提示验证码发送成功
						UIUtils.showToast(context, resBean.errmsg);
					} else {
						UIUtils.showToast(context, resBean.errmsg);
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(String msg) {
				super.onPostExecute(msg);
			}
		}.execute(new String[] { url, json });

	}

	/**
	 * 点击立即充值的时候,如果没有网络
	 */
	private void isNotNetWork() {
		final QidaDialog dialog_temp = new QidaDialog(context, R.layout.dialog_confirmation, R.style.QidaDialog,
				LoginController.this.responseCallback);
		View view = dialog_temp.view;
		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		tv_desc.setText("登录需要手机上网，请打开网络");
		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
				dialog_temp.dismiss();
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						LoginController.this.dialog.show();
					}
				}.sendEmptyMessageDelayed(0, 1000);

			}
		});
		bt_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginController.this.dialog.onClickCancleBut();
				dialog_temp.dismiss();
			}
		});
		LoginController.this.dialog.dismiss();
		dialog_temp.show();
	}

}
