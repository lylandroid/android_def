package com.wi360.pay.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.pay.sdk.base.QidaDialog;
import com.wi360.pay.sdk.interfaces.Pay;
import com.wi360.pay.sdk.interfaces.ResponseCallback;
import com.wi360.pay.sdk.util.StringUtils;
import com.wi360.pay.sdk.util.UIUtils;

public class MainActivity extends Activity {

	@ViewInject(R.id.et_input)
	private EditText et_input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(this, R.layout.activity_main, null);
		ViewUtils.inject(this, view);
		// String bean = new BaseBean().getKeyHash(this);
		setContentView(view);
	}

	@OnClick({ R.id.bt_1, R.id.bt_12, R.id.bt_13, R.id.bt_14, R.id.bt_15, R.id.bt_16, //
			R.id.bt_17, R.id.bt_18, R.id.bt_19, R.id.bt_10, R.id.bt_500,//
			R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.bt_submit_input })
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.bt_1:
			bt1(1);
			break;
		case R.id.bt_12:
			bt1(2);
			break;
		case R.id.bt_13:
			bt1(3);
			break;
		case R.id.bt_14:
			bt1(4);
			break;
		case R.id.bt_15:
			bt1(5);
			break;
		case R.id.bt_16:
			bt1(6);
			break;
		case R.id.bt_17:
			bt1(7);
			break;
		case R.id.bt_18:
			bt1(8);
			break;
		case R.id.bt_19:
			bt1(9);
			break;
		case R.id.bt_10:
			bt1(10);
			break;
		case R.id.bt_500:
			bt1(500);
			break;
		case R.id.bt_submit_input:
			String memoy = et_input.getText().toString().trim();
			if (StringUtils.isEmpty(memoy)) {
				memoy = "0";
			}
			int memoy2 = Integer.parseInt(memoy);
			bt1(memoy2);
			break;

		case R.id.bt_2:
			bt2();
			break;

		case R.id.bt_3:
			bt3();
			break;
		case R.id.bt_4:
			bt4();
			break;
		case R.id.bt_5:
			bt5();
			break;
		case R.id.bt_6:
			bt6();
			break;
		}
	}

	// 立即信用支付
	private void bt1(int momey) {
		// QidaDialog dialog = new QidaDialog(this, R.layout.dialog_pay1,
		// R.style.QidaDialog);
		// dialog.show();
		// PayController pay = new PayController(this, dialog, dialog.view);
		Pay pay = PayFactory.getInstance(this);
		pay.creditPay("商品名称", momey, "360market", "uc-zhangsan", new ResponseCallback() {
			@Override
			public void responseStateCode(int code) {
				UIUtils.showToast(MainActivity.this, code + "");
			}
		});
	}

	private void bt2() {
		QidaDialog dialog = new QidaDialog(this, R.layout.dialog_recharge, R.style.QidaDialog, null);
		dialog.show();
	}

	private void bt3() {
		QidaDialog dialog = new QidaDialog(this, R.layout.dialog_pay_success, R.style.QidaDialog, null);
		dialog.show();
	}

	private void bt4() {
		QidaDialog dialog = new QidaDialog(this, R.layout.dialog_login, R.style.QidaDialog, null);
		dialog.show();
		LoginController login = new LoginController(this, dialog, dialog.view, null, null);
	}

	private void bt5() {
		QidaDialog dialog = new QidaDialog(this, R.layout.dialog_select_model, R.style.QidaDialog, null);
		dialog.show();

	}

	private void bt6() {
		QidaDialog dialog = new QidaDialog(this, R.layout.dialog_confirmation, R.style.QidaDialog, null);
		dialog.show();
	}
}
