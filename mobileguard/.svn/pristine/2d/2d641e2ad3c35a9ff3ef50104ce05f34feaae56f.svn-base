package com.itheima.mobileguard.services;

import java.util.TimerTask;

import java.util.Timer;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.receivers.MyWidget;
import com.itheima.mobileguard.utils.SystemInfoUtils;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {
	protected static final String TAG = "UpdateWidgetService";
	private Timer timer;
	private TimerTask task;
	private AppWidgetManager awm;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		awm = AppWidgetManager.getInstance(this);
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				Log.i(TAG,"更新widget");
				//让桌面更新widget  由另外一个进程 更新ui
				ComponentName provider = new ComponentName(getApplicationContext(), MyWidget.class);
				//远程view的描述信息， 并不是一个真实的view对象， 由远程的桌面应用根据描述信息把view对象创建出来。
				RemoteViews views = new RemoteViews(getPackageName(), R.layout.process_widget);
				views.setTextViewText(R.id.process_count, "运行中的进程："+SystemInfoUtils.getRunningPocessCount(getApplicationContext()));
				String availsize = Formatter.formatFileSize(getApplicationContext(), SystemInfoUtils.getAvailMem(getApplicationContext()));
				views.setTextViewText(R.id.process_memory, "可用内存："+availsize);
				//由另外一个进程执行的动作,由桌面发送一个广播
				Intent intent = new Intent();//自定义的广播
				intent.setAction("com.itheima.killall");
				PendingIntent pendingIntent= PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
				views.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
				awm.updateAppWidget(provider, views);
			}
		};
		timer.schedule(task, 0, 5000);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer != null && task != null) {
			timer.cancel();
			task.cancel();
			timer = null;
			task = null;
		}
	}

}
