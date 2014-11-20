package com.itheima.mobileguard.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {

	private static final String TAG = "BootCompleteReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "手机启动完毕了");
		// 检查sim卡是否发生变化
		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		// 获取防盗保护的状态
		boolean protecting = sp.getBoolean("protecting", false);
		if (protecting) {
			// 得到绑定的sim卡串号
			String bindsim = sp.getString("sim", "");
			// 得到手机现在的sim卡串号
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String realsim = tm.getSimSerialNumber() + "dafa";
			if (bindsim.equals(realsim)) {
				Log.i(TAG, "sim卡未发生变化，还是您的手机");
			} else {
				Log.i(TAG, "SIM卡变化了");
				String safenumber = sp.getString("safenumber", "");
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(safenumber, null, "sim changed!",
						null, null);
			}
		}else{
			Log.i(TAG, "防盗保护没哟u开启");
		}
	}

}
