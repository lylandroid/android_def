package com.wi360.sms.marketing.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.base.BaseActivity;

/**
 * 弹出对话框 Intent intent = new Intent(context, ButtomDialogActivity.class);
 * Messenger messenger = new Messenger(new Handler());
 * intent.putExtra("messenger", messenger); intent.putExtra("msg", msg);
 * context.startActivity(intent);
 * -----------------------------------------------------------
 * 
 * messenger = (Messenger) intent.getExtras().get("messenger");
 * 
 * @author Administrator
 * 
 */
public class ButtomConfirmDialogActivity extends BaseActivity {

	@ViewInject(R.id.tv_msg)
	private TextView tv_msg;
	private Messenger messenger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = View.inflate(this, R.layout.dialog_layout_buttom_confirm, null);
		ViewUtils.inject(this, view);
		setContentView(view);
		// 获取activity传递过来的数据
		Intent intent = getIntent();
		String msg = intent.getStringExtra("msg");
		if (intent.getExtras().get("messenger") != null) {
			messenger = (Messenger) intent.getExtras().get("messenger");
		}

		tv_msg.setText(msg);

	}

	@OnClick({ R.id.tv_dialog_transparent, R.id.bt_submit, R.id.bt_cancel })
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.tv_dialog_transparent:// 点击灰色区域销毁activity
			myOnKeyDown(0,null);
			break;
		case R.id.bt_submit:// 点击灰色区域销毁activity
			myOnKeyDown(0,null);
			btConfirm();
			break;
		case R.id.bt_cancel:// 点击灰色区域销毁activity
			myOnKeyDown(0,null);
			break;
		}
	}

	private void btConfirm() {
		try {
			if (messenger != null) {
				messenger.send(new Message());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initView() {
	}

	@Override
	public void initValue() {
	};

	@Override
	public boolean myOnKeyDown(int keyCode, KeyEvent event,Class<?>... clazz) {
		return super.myOnKeyDown(keyCode, event);
	}

	@Override
	public void initListener() {

	}

}
