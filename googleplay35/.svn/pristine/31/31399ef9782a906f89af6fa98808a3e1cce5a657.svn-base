package com.example.googleplay35.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

public class BaseApplication extends Application {
	//获取到主线程上下文
   private static BaseApplication mContext;
   //获取到主线程的looper
   private static Looper mMainLooper;	
   //获取到主线程的handler
   private static Handler mMainHandler;
   //获取到主线程的id
   private static int mMainThreadId;
   //获取到主线程的Thread
   private static Thread mMainThread;
	@Override
	public void onCreate() {
		super.onCreate();
		this.mContext = this;
		this.mMainLooper = getMainLooper();
		this.mMainHandler = new Handler();
		this.mMainThreadId = android.os.Process.myTid();
		this.mMainThread = Thread.currentThread();
	}
	
	public static BaseApplication getApplication(){
		return mContext;
	}
	
	public static Looper getMainThreadLooper(){
		return mMainLooper;
	}
	
	public static int getMainThreadId(){
		return mMainThreadId;
	}
	
	public static Handler getMainThreadHandler(){
		return mMainHandler;
	}
	
	public static Thread getMainThread(){
		return mMainThread;
	}
}
