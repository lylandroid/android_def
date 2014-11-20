package com.wi360.mobile.wallet;

import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.NewsCardActivateBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.bean.SMSBean2;
import com.wi360.mobile.wallet.bean.SmsNewsBean;
import com.wi360.mobile.wallet.home.CardManagerPager;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.CheckUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.EditTextChar;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;
import com.wi360.mobile.wallet.utils.StringUtils;
import com.wi360.mobile.wallet.utils.UIUtils;

/**
 * 新卡激活activity
 * 
 * @author Administrator
 * 
 */
public class NewCardActivateActivity extends BaseActivity {

	@ViewInject(R.id.et_card_number)
	private EditText et_card_number;

	@ViewInject(R.id.et_pwd)
	private EditText et_pwd;

	@ViewInject(R.id.et_validate_code)
	private EditText et_validate_code;

	@ViewInject(R.id.bt_get_code)
	private Button bt_get_code;

	private EditTextChar editTextUtils;

	private long currentTime;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 验证码发送时间
			long smsCodeTime = SharedPreferencesUtils.getLong(NewCardActivateActivity.this,
					Constants.SEND_SMS_CODE_TIEM_NEWS);
			currentTime = new Date().getTime();
			if ((currentTime - smsCodeTime) < 60 * 1000) {
				// failureDialog(NewCardActivateActivity.this, "60秒类不能重复发送验证码");
				if (bt_get_code.isEnabled()) {
					bt_get_code.setEnabled(false);
				}
				this.sendEmptyMessageDelayed(0, 5000);
				return;
			}
			bt_get_code.setEnabled(true);
			handler.removeCallbacksAndMessages(null);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	@Override
	protected void onStart() {
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
			handler.sendEmptyMessageDelayed(0, 2000);
		}
		super.onStart();
	}

	@Override
	public void initView() {
		View view = View.inflate(this, R.layout.layout_new_card_activate, null);
		setContentView(view);
		ViewUtils.inject(this, view);

		editTextUtils = new EditTextChar(et_card_number);

		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("新卡激活");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);

		// 返回imageButton
		ib_back.setVisibility(View.VISIBLE);

		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 销毁当前activity,并且执行动画
				myOnKeyDown();
			}
		});

	}

	@Override
	public boolean initData() {
		sendHandler();
		return false;
	}

	@OnClick({ R.id.ll_card_activate, R.id.ib_clear_pwd, R.id.ib_clear_number, R.id.bt_get_code, R.id.bt_submit })
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.ib_clear_number:// 清空卡号
			et_card_number.setText("");
			break;
		case R.id.ib_clear_pwd:// 清空密码
			et_pwd.setText("");
			break;
		case R.id.bt_get_code:// 获取验证码
			getCode();
			break;
		case R.id.bt_submit:// 激活
			submit();
			break;
		}
	}

	/**
	 * 激活,提交
	 */
	private void submit() {
		// 判断是否登录
		if (!isLogin()) {
			return;
		}

		boolean is_send_sms_code = SharedPreferencesUtils.getBoolean(context, Constants.IS_CODE_SEND_SUCCESS_NEWS,
				false);
		if (!is_send_sms_code) {
			failureDialog(context, "请先获取短信验证码");
			return;
		}
		String cardNum = et_card_number.getText().toString().trim();
		String pwd = et_pwd.getText().toString().trim();
		String code = et_validate_code.getText().toString().trim();
		if (StringUtils.isEmpty(cardNum)) {
			failureDialog(context, "请填写卡号");
			return;
		} else if (StringUtils.isEmpty(pwd)) {
			failureDialog(context, "请填写密码");
			return;
		} else if (StringUtils.isEmpty(code)) {
			failureDialog(context, "请填写验证码");
			return;
		}
		cardNum = EditTextChar.getEditTextReplace(cardNum);
		NewsCardActivateBean newsCardActivateBean = new NewsCardActivateBean(context, cardNum, pwd, code);
		String json = GsonTools.createGsonString(newsCardActivateBean);

		new MyAsyncTask<NewsCardActivateBean>(context, "激活中") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				// 访问网络成功
				if (responseInfo.statusCode == 200) {
					String resStr = responseInfo.result;
					if (resStr != null) {
						resBean = GsonTools.changeGsonToBean(resStr, ResultBean.class);
						if (resBean.errcode == 0) {
							CardManagerPager.isRepeat = true;
							// 保存发送验证码成功状态
							SharedPreferencesUtils.saveBoolean(context, Constants.IS_CODE_SEND_SUCCESS_NEWS, false);
							SharedPreferencesUtils.saveLong(context, Constants.SEND_SMS_CODE_TIEM_NEWS, 0);
						}
					}
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				if (resBean != null) {
					if (resBean.errcode == 0) {
						UIUtils.showToast(context, resBean.errmsg);
						super.onPostExecute(null);
					} else {
						super.onPostExecute(resBean.errmsg);
					}
				} else {
					super.onPostExecute("服务器忙,请稍后再试");
				}

			};
		}.execute(new String[] { Constants.ACTIVATE_CARD_URL, json });
		// 发送验证码成功后,把状态改为初始状态
	}

	/**
	 * 获取验证码
	 */
	public void getCode() {
		//
		sendHandler();

		String pan = et_card_number.getText().toString().trim();
		String pwd = et_pwd.getText().toString().trim();

		if (StringUtils.isEmpty(pan)) {
			failureDialog(NewCardActivateActivity.this, "请填写汇通卡号");
			return;
		} else if (StringUtils.isEmpty(pwd)) {
			failureDialog(NewCardActivateActivity.this, "请填写密码");
			return;
		}
		// 获取上一次发送验证码时间,60秒内不能再重复发送验证码
		{
			long smsCodeTime = SharedPreferencesUtils.getLong(NewCardActivateActivity.this,
					Constants.SEND_SMS_CODE_TIEM_NEWS);
			currentTime = new Date().getTime();
			if ((currentTime - smsCodeTime) < 60 * 1000) {
				failureDialog(NewCardActivateActivity.this, "60秒类不能重复发送验证码");
				return;
			}

		}
		pan = editTextUtils.getEditTextReplace(pan);
		SmsNewsBean smsBean = new SmsNewsBean(context, pan, pwd);
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
						// 保存当前发送验证码时间
						SharedPreferencesUtils.saveLong(context, Constants.SEND_SMS_CODE_TIEM_NEWS, currentTime);
						SharedPreferencesUtils.saveBoolean(context, Constants.IS_CODE_SEND_SUCCESS_NEWS, true);
					} else {
						SharedPreferencesUtils.saveBoolean(context, Constants.IS_CODE_SEND_SUCCESS_NEWS, false);
					}
					return null;
				} else {
					return "短信验证码发送失败,请重新发送";
				}
			}

			protected void onPostExecute(String msg) {
				// 控制验证码按钮
				sendHandler();
				// 模拟验证 码发送成功
				// SharedPreferencesUtils.saveLong(context,
				// Constants.SEND_SMS_CODE_TIEM_NEWS, currentTime);
				if (resBean != null) {
					if (resBean.errcode == 0) {
						UIUtils.showToast(context, resBean.errmsg);
					} else {
						super.onPostExecute(resBean.errmsg);
					}
				}
				super.onPostExecute(msg);
			};
		}.execute(new String[] { Constants.ACTIVATE_CARD_SMS_URL, json });
	}

	/**
	 * 用于发送延迟消息
	 */
	private void sendHandler() {
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
			handler.sendEmptyMessageDelayed(0, 1000);
		}
	}

	/**
	 * 判断是否登录,若果没有登录,跳转到登录页面
	 * 
	 * @return,false没有登录
	 */
	private boolean isLogin() {
		// 如果没有登录
		boolean isLogin = SharedPreferencesUtils.getBoolean(context, Constants.IS_LOGIN, false);
		if (!isLogin) {
			ActivityAnimationUtils.rightToLeftInAnimation(this, LoginActivity.class);
			return false;
		}
		return true;
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(NewCardActivateActivity.this, MainActivity.class);
		return true;
	}

	@Override
	protected void onDestroy() {
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
}
