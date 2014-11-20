package com.wi360.sms.marketing.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wi360.sms.marketing.service.StartingUpService;
import com.wi360.sms.marketing.utils.AppUtils;
import com.wi360.sms.marketing.utils.CheckUtils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 定时,时钟广播
 * 
 * @author Administrator
 * 
 */
public class Alarmreceiver extends BroadcastReceiver {
	private static final String TAG = "Alarmreceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("short")) {
			// NO
		} else {
			// 不能判断服务是否处于运行状态,必须重新启动
			// if (!AppUtils.isServiceRunning(context,
			// StartingUpService.class.getName())) {
			if (CheckUtils.isSendNetWorkRequest()) {
				Intent myIntent = new Intent(context, StartingUpService.class);
				myIntent.putExtra("isSendNetWorkRequest", true);
				context.startService(myIntent);
			}
			// }
			// Toast.makeText(context, "repeating alarm222", Toast.LENGTH_LONG)
			// .show();
		}
	}
}