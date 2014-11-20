package com.wi360.sms.marketing.receiver;

import com.wi360.sms.marketing.service.StartingUpService;
import com.wi360.sms.marketing.utils.AppUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 开机启动广播
 * 
 * @author Administrator
 * 
 */
public class BootStartUpBroadcastReceiver extends BroadcastReceiver {
	private String TAG = "BootStartUpBroadcastReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive");
		if (!AppUtils.isServiceRunning(context,
				StartingUpService.class.getName())) {
			Intent myIntent = new Intent(context, StartingUpService.class);
			// myIntent.setAction("com.example.test_service.StartingUpService");
			context.startService(myIntent);
		}
	}

}
