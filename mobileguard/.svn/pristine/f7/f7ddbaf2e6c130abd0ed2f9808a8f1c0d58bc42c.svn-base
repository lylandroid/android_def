package com.itheima.mobileguard.activities;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.fragments.LockedFragment;
import com.itheima.mobileguard.fragments.UnlockFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AppLockActivity extends FragmentActivity implements OnClickListener {
	private static final String TAG = "AppLockActivity";
	private TextView tv_locked;
	private TextView tv_unlock;
	private FragmentManager fm;
	private UnlockFragment unlockFragment;
	private LockedFragment lockedFragment;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_lock);
		tv_unlock = (TextView) findViewById(R.id.tv_unlock);
		tv_locked = (TextView) findViewById(R.id.tv_locked);
		tv_locked.setOnClickListener(this);
		tv_unlock.setOnClickListener(this);
		//初始化帧管理器。
		fm = getSupportFragmentManager();
		unlockFragment = new UnlockFragment();
		lockedFragment = new LockedFragment();
		//开启界面变化的事务
		FragmentTransaction ft  = fm.beginTransaction();
		ft.replace(R.id.fl_container, unlockFragment);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		//开启界面变化的事务
		FragmentTransaction ft  = fm.beginTransaction();
		switch (v.getId()) {
		case R.id.tv_locked:
			tv_locked.setBackgroundResource(R.drawable.tab_right_pressed);
			tv_unlock.setBackgroundResource(R.drawable.tab_left_default);
			Log.i(TAG,"替换fragment的界面");
			ft.replace(R.id.fl_container, lockedFragment);
			break;
		case R.id.tv_unlock:
			tv_locked.setBackgroundResource(R.drawable.tab_right_default);
			tv_unlock.setBackgroundResource(R.drawable.tab_left_pressed);
			Log.i(TAG,"替换fragment的界面");
			ft.replace(R.id.fl_container, unlockFragment);
			break;
		}
		ft.commit();//提交事务。
	}
}
