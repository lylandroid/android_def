package com.itheima.mobileguard.services;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class AutoKillService extends Service {
	public static final String TAG = "AutoKillService";
	private Timer timer;
	private TimerTask task;
	private ScreenLockReceiver receiver;
	
	private class ScreenLockReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			Log.i(TAG,"屏幕锁屏了。");
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			for(RunningAppProcessInfo info: am.getRunningAppProcesses()){
				String packname = info.processName;
				am.killBackgroundProcesses(packname);
			}
		}
	}
	@Override
	public void onCreate() {
		super.onCreate();
		receiver = new ScreenLockReceiver();
		registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
		/*timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				//自动清理内存
			}
		};
		timer.schedule(task, 0, 1000*60*60*2);
		
		CountDownTimer countDownTimer =  new CountDownTimer(1000*60*60*2, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				
			}
			
			@Override
			public void onFinish() {
//				countDownTimer.start();
			}
		};
		countDownTimer.start();*/
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
		receiver = null;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
