package com.itheima.mobileguard.services;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.itheima.mobileguard.activities.EnterPwdActivity;
import com.itheima.mobileguard.db.dao.ApplockDao;

public class WatchDogService extends Service {
	private boolean flag = false;
	private ActivityManager am;
	private WatchDogReceiver receiver;
	private ApplockDao dao;
	/**
	 * 临时停止保护的应用程序包名
	 */
	private String tempStopProtectPackname;
	
	private List<RunningTaskInfo> taskInfos;
	
	private RunningTaskInfo taskInfo;
	
	private String packname ;
	
	/**
	 * 被锁定的包名的集合
	 */
	private  List<String> lockedPacknames;

	private Intent intent;
	
	private ApplockObserver observer;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private class WatchDogReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if ("com.itheima.mobileguard.stopprotect"
					.equals(intent.getAction())) {
				tempStopProtectPackname = intent.getStringExtra("packname");
			} else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
				tempStopProtectPackname = null;
				//停止看门狗
				flag = false;
			}else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
				//开启看门狗
				if(flag==false){
					startWatchDog();
				}
			}
		}
	}

	@Override
	public void onCreate() {
		observer = new ApplockObserver(new Handler());
		getContentResolver().registerContentObserver(Uri.parse("content://com.itheima.mobileguard.applock"), 
				true, observer);
		
		dao = new ApplockDao(this); 
		//只有在服务第一次创建的时候 才会获取数据。 所有在服务开启过程中，如果更改程序锁的配置信息 无效了。
		lockedPacknames = dao.findAll();
		intent = new Intent(WatchDogService.this,
				EnterPwdActivity.class);
		// 注册一个自定义的广播接受者
		receiver = new WatchDogReceiver();
		IntentFilter filter = new IntentFilter(
				"com.itheima.mobileguard.stopprotect");
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(receiver, filter);
		am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		startWatchDog();
		super.onCreate();
	}


	private void startWatchDog() {
		new Thread() {
			public void run() {
				flag = true;
				while (flag) {
					// 监视任务栈的情况。 最近使用的打开的任务栈在集合的最前面
					taskInfos = am.getRunningTasks(1);
					// 最近使用的任务栈
					taskInfo = taskInfos.get(0);
					packname = taskInfo.topActivity.getPackageName();
					System.out.println(packname);
					// 判断这个包名是否需要被保护。
					//if (dao.find(packname)) {//查询数据库 效率低 ，内存开销大
					if(lockedPacknames.contains(packname)){//查询内存集合 速度快 内存开销小
						// 判断当前应用程序是否需要临时停止保护（输入了正确的密码）
						if (packname.equals(tempStopProtectPackname)) {

						} else {
							// 需要保护
							// 弹出一个输入密码的界面。
							intent.putExtra("packname", packname);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}
					} else {
						// 不需要保护
					}
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	private class ApplockObserver extends ContentObserver{

		public ApplockObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Log.i("ApplockObserver","数据库的内容变化了，重新获取 被锁定的包名");
			//重新获取 被锁定的包名
			lockedPacknames = dao.findAll();
		}
		
	}
	
	
	@Override
	public void onDestroy() {
		flag = false;
		unregisterReceiver(receiver);
		receiver = null;
		getContentResolver().unregisterContentObserver(observer);
		observer = null;
		super.onDestroy();
	}

}
