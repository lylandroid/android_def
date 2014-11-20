package com.itheima.mobileguard.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.db.dao.NumberAddressDao;

public class ShowLocationService extends Service {
	// 系统提供的电话管理器，电话管理的服务
	private TelephonyManager tm;
	private MyPhoneListener listener;
	private OutCallReceiver receiver;
	private SharedPreferences sp;
	protected static final String TAG = "ShowLocationService";
	/**
	 * 窗体管理的服务。
	 */
	private WindowManager windowManager;

	/**
	 * 显示在界面上的view对象。
	 */
	private View view;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * 外拨电话的广播接受者
	 * 
	 * @author Administrator
	 * 
	 */
	private class OutCallReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String number = getResultData();
			String address = NumberAddressDao.getLocation(number);
			// Toast.makeText(getApplicationContext(), address, 1).show();
			showMyToast(address);
		}
	}

	@Override
	public void onCreate() {
		Notification notification = new Notification(R.drawable.main_icon_36, "时刻保护您的安全", System.currentTimeMillis());
		Intent intent = new Intent();
		intent.setAction("ooo.aaa.bbb");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		PendingIntent contentIntnet = PendingIntent.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this, "保护您的安全", "不开启，不安全", contentIntnet);
		startForeground(0, notification);
		
		receiver = new OutCallReceiver();
		sp  = getSharedPreferences("config", MODE_PRIVATE);
		// 注册外拨电话的广播接受者
		registerReceiver(receiver, new IntentFilter(
				Intent.ACTION_NEW_OUTGOING_CALL));
		// 注册电话状态的监听器
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		// 得到窗体管理器
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		listener = new MyPhoneListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		receiver = null;
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		super.onDestroy();
	}

	private static final int[] bgs = { R.drawable.call_locate_white,
			R.drawable.call_locate_orange, R.drawable.call_locate_blue,
			R.drawable.call_locate_gray, R.drawable.call_locate_green };
	/**
	 * view对象在窗体上的参数。
	 */
	private WindowManager.LayoutParams mParams;

	/**
	 * 自定义的土司，把归属地显示在界面上。
	 * 
	 * @param address
	 */
	public void showMyToast(String address) {
		view = View.inflate(this, R.layout.toast_showaddress, null);
		
		int which = sp.getInt("which", 0);
		// "半透明","活力橙","卫士蓝","金属灰","苹果绿"
		view.setBackgroundResource(bgs[which]);
		TextView tv_address = (TextView) view
				.findViewById(R.id.tv_toast_address);
		tv_address.setText(address);
		mParams = new WindowManager.LayoutParams();
		mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		//修改完左上角对其
		mParams.gravity = Gravity.LEFT+Gravity.TOP;
		mParams.x = sp.getInt("lastx", 0);
		mParams.y = sp.getInt("lasty", 0);
		mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE   自定义的土司需要用户触摸
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		mParams.format = PixelFormat.TRANSLUCENT;
//		mParams.type = WindowManager.LayoutParams.TYPE_TOAST; 土司窗体天生不响应触摸事件
		mParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		// 把view添加到手机窗体上。
		windowManager.addView(view, mParams);

		// 给view对象设置一个触摸事件。
		view.setOnTouchListener(new OnTouchListener() {
			int startX  ;
			int startY  ;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.i(TAG,"手指按到了控件上");
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					Log.i(TAG,"开始位置："+startX+","+startY);
					break;
				case MotionEvent.ACTION_MOVE:
					Log.i(TAG,"手指在控件上移动");
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					Log.i(TAG,"新的位置："+newX+","+newY);
					int dx = newX - startX;
					int dy = newY - startY;
					Log.i(TAG,"偏移量："+dx+","+dy);
					Log.i(TAG,"更新控件在屏幕上的位置");
					mParams.x +=dx;
					mParams.y +=dy;
					if(mParams.x<0){
						mParams.x = 0;
					}
					if(mParams.y<0){
						mParams.y = 0;
					}
					if(mParams.x>(windowManager.getDefaultDisplay().getWidth()-view.getWidth())){
						mParams.x=(windowManager.getDefaultDisplay().getWidth()-view.getWidth());
					}
					if(mParams.y>(windowManager.getDefaultDisplay().getHeight()-view.getHeight())){
						mParams.y=(windowManager.getDefaultDisplay().getHeight()-view.getHeight());
					}
					windowManager.updateViewLayout(view, mParams);
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
					
				case MotionEvent.ACTION_UP:
					Log.i(TAG,"手指离开屏幕");
					Editor editor = sp.edit();
					editor.putInt("lastx", mParams.x);
					editor.putInt("lasty", mParams.y);
					editor.commit();
					break;
				}
				return true;
			}
		});
	}

	private class MyPhoneListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:// 空闲状态
				if (view != null) {
					// 空闲状态把手机窗体上的view对象给删除掉。
					windowManager.removeView(view);
					view = null;
				}
				break;
			case TelephonyManager.CALL_STATE_RINGING:// 响铃状态
				String address = NumberAddressDao.getLocation(incomingNumber);
				// Toast.makeText(getApplicationContext(), address, 1).show();
				showMyToast(address);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:// 接通状态

				break;
			}
		}
	}
}
