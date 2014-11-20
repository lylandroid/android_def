package com.itheima.mobileguard.activities;

import com.itheima.mobileguard.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Setup2Activity extends BaseSetupActivity {
	private TelephonyManager tm;
	private ImageView iv_setup2_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		setContentView(R.layout.activity_setup2);
		iv_setup2_status = (ImageView) findViewById(R.id.iv_setup2_status);
		// 判断是否绑定过，
		String savedSim = sp.getString("sim", null);
		if (TextUtils.isEmpty(savedSim)) {
			iv_setup2_status.setImageResource(R.drawable.unlock);
		} else {
			iv_setup2_status.setImageResource(R.drawable.lock);
		}
	}

	/**
	 * 绑定或者解绑sim卡
	 * 
	 * @param view
	 */
	public void bindUnbindSim(View view) {
		// 判断是否绑定过，
		String savedSim = sp.getString("sim", null);
		if (TextUtils.isEmpty(savedSim)) {
			// 唯一的标识
			String simserial = tm.getSimSerialNumber();
			Editor editor = sp.edit();
			editor.putString("sim", simserial);
			editor.commit();
			Toast.makeText(this, "绑定sim卡成功", 0).show();
			iv_setup2_status.setImageResource(R.drawable.lock);
		} else {
			Editor editor = sp.edit();
			editor.putString("sim", null);
			editor.commit();
			Toast.makeText(this, "解除绑定sim卡成功", 0).show();
			iv_setup2_status.setImageResource(R.drawable.unlock);
		}
	}

	@Override
	public void showNext() {
		// 判断是否绑定过，
		String savedSim = sp.getString("sim", null);
		if (TextUtils.isEmpty(savedSim)) {
			Toast.makeText(this, "请先绑定sim卡", 0).show();
			return;
		}
		startActivityAndFinishSelf(Setup3Activity.class);
	}

	@Override
	public void showPre() {
		startActivityAndFinishSelf(Setup1Activity.class);
	}

}
