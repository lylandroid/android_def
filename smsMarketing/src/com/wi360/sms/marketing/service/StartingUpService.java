package com.wi360.sms.marketing.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.wi360.sms.marketing.receiver.Alarmreceiver;

public class StartingUpService extends Service {

	private String TAG = "StartingUpService";
	private boolean isSendNetWorkRequest = false;

	@Override
	public void onCreate() {
		// Notification notification = new Notification();
		// startForeground(1, notification);
		Log.i(TAG, "onCreate");
		Intent intent = new Intent(this, Alarmreceiver.class);
		intent.setAction("repeating");
		PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
		// 开始时间
		long firstime = SystemClock.elapsedRealtime();
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// 5秒一个周期，不停的发送广播
		// am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime, (60 *
		// 1000) * 22, sender);
		long waitTime = (10 * 1000);
		am.setRepeating(AlarmManager.RTC_WAKEUP, firstime + waitTime, waitTime,
				sender);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		isSendNetWorkRequest = false;
		if (intent != null) {
			isSendNetWorkRequest = intent.getBooleanExtra(
					"isSendNetWorkRequest", false);
		}
		if (isSendNetWorkRequest) {
			accessNetwork();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		// Intent localIntent = new Intent();
		// localIntent.setClass(this, StartingUpService.class); //
		// // 销毁时重新启动Service
		// this.startService(localIntent);
		super.onDestroy();
	}

	public void accessNetwork() {
		Toast.makeText(getApplicationContext(), "发送网络请求", 0).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
