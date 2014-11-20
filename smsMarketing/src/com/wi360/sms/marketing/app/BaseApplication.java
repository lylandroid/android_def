package com.wi360.sms.marketing.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.wi360.sms.marketing.receiver.BootStartUpBroadcastReceiver;

public class BaseApplication extends Application {

	private static final boolean DEVELOPER_MODE = false;
	private static final String TAG = "BaseApplication";
	// 获取到主线程上下文
	private static BaseApplication mContext;
	// 获取到主线程的looper
	private static Looper mMainLooper;
	// 获取到主线程的handler
	private static Handler mMainHandler;
	// 获取到主线程的id
	private static int mMainThreadId;
	// 获取到主线程的Thread
	private static Thread mMainThread;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		super.onCreate();
		//
		this.mContext = this;
		this.mMainLooper = getMainLooper();
		this.mMainHandler = new Handler();
		this.mMainThreadId = android.os.Process.myTid();
		this.mMainThread = Thread.currentThread();

		// 再次动态注册开机广播
		 IntentFilter localIntentFilter = new
		 IntentFilter("android.intent.action.USER_PRESENT");
		 localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
		 BootStartUpBroadcastReceiver searchReceiver = new
		 BootStartUpBroadcastReceiver();
		 registerReceiver(searchReceiver, localIntentFilter);
	}

	public static BaseApplication getApplication() {
		return mContext;
	}

	public static Looper getMainThreadLooper() {
		return mMainLooper;
	}

	public static int getMainThreadId() {
		return mMainThreadId;
	}

	public static Handler getMainThreadHandler() {
		return mMainHandler;
	}

	public static Thread getMainThread() {
		return mMainThread;
	}

}
