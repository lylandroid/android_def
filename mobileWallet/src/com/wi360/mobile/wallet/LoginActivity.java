package com.wi360.mobile.wallet;

import java.util.Date;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.LoginBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.bean.SMSBean2;
import com.wi360.mobile.wallet.home.MyPager;
import com.wi360.mobile.wallet.interfaces.MyRequestCallBack;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.CheckUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.MyHttpUtils;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;
import com.wi360.mobile.wallet.utils.StringUtils;
import com.wi360.mobile.wallet.utils.UIUtils;
import com.wi360.mobile.wallet.view.LoadDialog;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseActivity {
	private LinearLayout ib_back;

	@ViewInject(R.id.et_phone_number)
	private EditText et_phone_number;

	@ViewInject(R.id.et_sms_code)
	private EditText et_sms_code;

	@ViewInject(R.id.ib_clear)
	private ImageButton ib_clear;

	@ViewInject(R.id.bt_get_code)
	private Button bt_get_code;

	private long currentTime = 0;

	private Dialog loadDialog;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 验证码发送时间
			long smsCodeTime = SharedPreferencesUtils.getLong(LoginActivity.this, Constants.SEND_SMS_CODE_TIEM);
			currentTime = new Date().getTime();
			if ((currentTime - smsCodeTime) < 60 * 1000) {
				// failureDialog(NewCardActivateActivity.this, "60秒类不能重复发送验证码");
				if (bt_get_code.isEnabled()) {
					bt_get_code.setEnabled(false);
				}
				this.sendEmptyMessageDelayed(1, 5000);
				return;
			}
			bt_get_code.setEnabled(true);
			handler.removeCallbacksAndMessages(null);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		View view = View.inflate(context, R.layout.layout_login, null);
		ViewUtils.inject(this, view);
		setContentView(view);
		//
		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("手机号码登录");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);

		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myOnKeyDown();
			}
		});

	}

	@Override
	public boolean initData() {
		handler.removeCallbacksAndMessages(null);
		handler.sendEmptyMessageDelayed(2, 2000);
		return false;
	}

	/**
	 * 点击清除手机号码
	 * 
	 * @param v
	 */
	@OnClick({ R.id.ib_clear, R.id.bt_get_code, R.id.bt_submit })
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.ib_clear:
			et_phone_number.setText("");
			break;
		case R.id.bt_get_code:
			getCode();
			break;
		case R.id.bt_submit:
			login();
			// login();
			break;

		}

	}

	private void login() {
		// UIUtils.showToast(context, "登录");

		String phone = et_phone_number.getText().toString().trim();
		String smsCode = et_sms_code.getText().toString().trim();
		if (!CheckUtils.checkMobileNO(this, phone)) {
			return;
		}
		if (com.wi360.mobile.wallet.utils.StringUtils.isEmpty(smsCode)) {
			failureDialog(context, "请输入验证码");
			return;
		}
		//测试需要先注释掉
		if (!SharedPreferencesUtils.getBoolean(context, Constants.IS_CODE_SEND_SUCCESS, false)) {
			failureDialog(context, "请先发送验证码");
			return;
		}
		{
			long smsCodeTime = SharedPreferencesUtils.getLong(LoginActivity.this, Constants.SEND_SMS_CODE_TIEM);
			long currentTime = new Date().getTime();
			if ((currentTime - smsCodeTime) > 90 * 1000) {
				failureDialog(context, "发送验证码后,90秒内不能重复登录");
				return;
			}
		}

		SharedPreferencesUtils.saveBoolean(context, Constants.IS_CODE_SEND_SUCCESS, false);
		final String tempPhone = phone;
		// 修改
		LoginBean loginBean = new LoginBean(context,phone, smsCode);
		String json = GsonTools.createGsonString(loginBean);

		loadDialog = LoadDialog.createLoadingDialog(this, "登录中...");
		loadDialog.show();

		// 发送post请求
		MyHttpUtils.sendPost(Constants.LOGIN_URL, json, null, new MyRequestCallBack() {
			// 登录成功后默认是在主线程中
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				loadDialog.dismiss();
				// 访问网络成功
				if (responseInfo.statusCode == 200) {
					// {"errmsg":"输入的身份信息(手机或电邮)不存在","errcode":1201}
					String resJson = responseInfo.result;
					ResultBean resBean = GsonTools.changeGsonToBean(resJson, ResultBean.class);
					// 验证码发送成功
					if (resBean.errcode == 0) {
						SharedPreferencesUtils.saveString(context, Constants.PHONE_NUMBER, tempPhone);
						SharedPreferencesUtils.saveString(context, Constants.SHOW_NICK_NAME, tempPhone);
						SharedPreferencesUtils.saveBoolean(context, Constants.IS_LOGIN, true);

						// 调用handler
						// TODO

						SharedPreferencesUtils.saveString(context, Constants.token, resBean.token);
						UIUtils.showToast(context, "登录成功");
						// 登录成后跟新UI
						{
							View view = MyPager.view;
							TextView tv_login = (TextView) view.findViewById(R.id.tv_login);
							tv_login.setText("退出");
							tv_login.setVisibility(View.VISIBLE);
							TextView user_name = (TextView) view.findViewById(R.id.tv_show_nick_name);
							user_name.setText(tempPhone);
						}
						myOnKeyDown();
					} else {
						failureDialog(LoginActivity.this, resBean.errmsg);
					}

				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				loadDialog.dismiss();
				failureDialog(context, "访问人数过多,请稍后再试");
				error.printStackTrace();
			}
		});
	}

	/**
	 * 获取验证码
	 */
	public void getCode() {
		String phone = et_phone_number.getText().toString().trim();
		// 如果没有登录,跳转到登录页面
		if (StringUtils.isEmpty(phone)) {
			ActivityAnimationUtils.rightToLeftInAnimation(this, LoginActivity.class);
		}
		if (!CheckUtils.checkMobileNO(this, phone)) {
			return;
		}

		// 获取上一次发送验证码时间,60秒内不能再重复发送验证码
		{
			long smsCodeTime = SharedPreferencesUtils.getLong(LoginActivity.this, Constants.SEND_SMS_CODE_TIEM);
			currentTime = new Date().getTime();
			if ((currentTime - smsCodeTime) < 60 * 1000) {
				failureDialog(LoginActivity.this, "60秒类不能重复发送验证码");
				return;
			}

		}
		SMSBean2 smsBean = new SMSBean2(context,phone);
		String json = GsonTools.createGsonString(smsBean);
		sendSmsCode(json);

	}

	public void sendSmsCode(String json) {
		new MyAsyncTask<SMSBean2>(context, "正在发送验证码") {

			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				// 访问网络成功
				if (responseInfo.statusCode == 200) {

					String resJson = responseInfo.result;
					resBean = GsonTools.changeGsonToBean(resJson, ResultBean.class);
					// 验证码发送成功
					if (resBean.errcode == 0) {
						// 保存验证码发送成功状态,用于登录
						SharedPreferencesUtils.saveBoolean(context, Constants.IS_CODE_SEND_SUCCESS, true);
						// 保存当前发送验证码时间
						SharedPreferencesUtils.saveLong(context, Constants.SEND_SMS_CODE_TIEM, currentTime);
						UIUtils.showToast(context, resBean.errmsg);
					} else {
						failureDialog(LoginActivity.this, resBean.errmsg);
					}
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				super.onPostExecute(msg);
				handler.removeCallbacksAndMessages(null);
				handler.sendEmptyMessageDelayed(2, 2000);
			};
		}.execute(new String[] { Constants.SEND_SMS_URL, json });
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(LoginActivity.this, MainActivity.class);
		return true;
	}
	
	@Override
	protected void onDestroy() {
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

}
