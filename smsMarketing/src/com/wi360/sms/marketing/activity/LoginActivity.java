package com.wi360.sms.marketing.activity;

import java.util.Date;

import android.app.Dialog;
import android.os.Handler;
import android.view.KeyEvent;
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
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.base.BaseActivity;
import com.wi360.sms.marketing.base.MyAsyncTask;
import com.wi360.sms.marketing.base.MyHttpUtils;
import com.wi360.sms.marketing.bean.LoginBean;
import com.wi360.sms.marketing.bean.ResBean;
import com.wi360.sms.marketing.bean.SMSBean;
import com.wi360.sms.marketing.dialog.LoadDialog;
import com.wi360.sms.marketing.interfaces.MyRequestCallBack;
import com.wi360.sms.marketing.utils.ActivityAnimationUtils;
import com.wi360.sms.marketing.utils.CheckUtils;
import com.wi360.sms.marketing.utils.Constants;
import com.wi360.sms.marketing.utils.GsonTools;
import com.wi360.sms.marketing.utils.SharedPreferencesUtils;
import com.wi360.sms.marketing.utils.StringUtils;
import com.wi360.sms.marketing.utils.UIUtils;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends BaseActivity {
	@ViewInject(R.id.et_phone_number)
	private EditText et_phone_number;

	@ViewInject(R.id.et_sms_code)
	private EditText et_sms_code;

	@ViewInject(R.id.bt_get_code)
	private Button bt_get_code;
	
	@ViewInject(R.id.txt_title)
	private TextView txt_title;

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
	public void initView() {
		view = View.inflate(context, R.layout.layout_login, null);
		ViewUtils.inject(this, view);
		txt_title.setText("手机号码登录");

	}

	@Override
	public void initValue() {
		handler.removeCallbacksAndMessages(null);
		handler.sendEmptyMessageDelayed(2, 2000);
	}

	/**
	 * 点击清除手机号码
	 * 
	 * @param v
	 */
	@OnClick({ R.id.bt_get_code, R.id.bt_submit })
	public void myOnClick(View v) {
		switch (v.getId()) {
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
		if (StringUtils.isEmpty(smsCode)) {
			failureDialog(context, "请输入验证码");
			return;
		}
		// 测试需要先注释掉
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
		SMSBean smsBean = new SMSBean(context, phone);
		String json = GsonTools.createGsonString(smsBean);
		// sendSmsCode(json);

	}

	@Override
	public void initListener() {

	}

	@Override
	public boolean myOnKeyDown(int keyCode, KeyEvent event, Class<?>... clazz) {
		return super.myOnKeyDown(keyCode, event, MyActivity.class);
	}

	@Override
	protected void onDestroy() {
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
}
