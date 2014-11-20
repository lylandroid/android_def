package com.wi360.mobile.wallet.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApplication extends Application {

	private static final boolean DEVELOPER_MODE = false;
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

	private static ImageLoader imageLoader;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {

		if (DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}
		super.onCreate();
		initImageLoader(getApplicationContext());
		//
		this.mContext = this;
		this.mMainLooper = getMainLooper();
		this.mMainHandler = new Handler();
		this.mMainThreadId = android.os.Process.myTid();
		this.mMainThread = Thread.currentThread();
		this.imageLoader = ImageLoader.getInstance();

		// imageLoader.dis
		// 初始化手机钱包
		 createSlave();
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

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 手机钱包
	 */
	// -----------------------------------------------------

	private static final String TAG = "SlaveApp";

	public static SlaveClient remoteClient;

	/**
	 * 初始化手机钱包
	 */
	public void createSlave() {
		Log.d(TAG, "UnionApp onCreate");

		remoteClient = new SlaveClient();
		remoteClient.ConnectService(this);

	}

	public static SlaveClient getRemoteClient() {
		if (remoteClient == null) {
			remoteClient = new SlaveClient();
		}
		return remoteClient;

	}

	public static void exitApp() {
		if (remoteClient != null) {
			remoteClient.shutDown();
			remoteClient = null;
		}

		System.exit(0);
	}

	@Override
	public void onTerminate() {
		exitApp();
		super.onTerminate();
	}

}
