package com.wi360.mobile.wallet.base;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.LinearLayout;

import com.wi360.mobile.wallet.ButtomConfirmDialogActivity;
import com.wi360.mobile.wallet.utils.RoundHeadImageUtil;

public abstract class BaseActivity extends Activity {
	protected LinearLayout ib_back;
	protected AsyncTask asyncTask;
	protected Activity context;
	// 临时保存到本地的头像文件
	protected File tempHeadLockFile;
	// 本地文件
	protected File headLockFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tempHeadLockFile = RoundHeadImageUtil.getPhotoFilePath(context,false);
		headLockFile = RoundHeadImageUtil.getPhotoFilePath(context,true);
		initView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按返回键是执行动画,并且销毁activity
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			myOnKeyDown();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 登录失败页面
	 * 
	 * @param context
	 *            ,当前activity
	 * @param msg
	 *            失败消息
	 */
	/**
	 * 登录失败页面
	 * 
	 * @param msg
	 *            ,失败消息
	 */
	protected void failureDialog(Activity context, String msg) {
		Intent intent = new Intent(context, ButtomConfirmDialogActivity.class);
		Messenger messenger = new Messenger(new Handler());
		intent.putExtra("messenger", messenger);
		intent.putExtra("msg", msg);
		this.startActivity(intent);
	}

	public abstract void initView();

	public abstract boolean initData();

	/**
	 * 当按下返回键时,处理activity返回键事件
	 * 
	 * @return
	 */
	public abstract boolean myOnKeyDown();

}
