package com.itheima.mobileguard.activities;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.utils.SmsUtils;
import com.itheima.mobileguard.utils.SmsUtils.BackupStatusCallback;
import com.itheima.mobileguard.utils.UIUtils;

public class AtoolsActivity extends Activity {
	
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	}
	
	
	public void numberAddressQuery(View view){
		Intent intent = new Intent(this,NumberAddressQueryActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 短信的备份
	 * @param view
	 */
	public void smsBackup(View view){
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setTitle("提醒");
		pd.setMessage("稍安勿躁，正在备份，你就等这吧");
		pd.show();
		new Thread(){
			public void run() {
				try {
					boolean result = SmsUtils.backUpSms(getApplicationContext(), new BackupStatusCallback() {
						@Override
						public void onSmsBackup(int process) {
							pd.setProgress(process);
						}
						
						@Override
						public void beforeSmsBackup(int size) {
								pd.setMax(size);
						}
					});
					if(result){
						UIUtils.showToast(AtoolsActivity.this, "备份成功");
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					UIUtils.showToast(AtoolsActivity.this, "文件生成失败");
				} catch (IllegalStateException e) {
					e.printStackTrace();
					UIUtils.showToast(AtoolsActivity.this, "sd卡不可用，或者存储空间不足");
				} catch (IOException e) {
					e.printStackTrace();
					UIUtils.showToast(AtoolsActivity.this, "读写错误");
				}finally{
					pd.dismiss();
				}
			};
		}.start();
	}
	/**
	 * 短信的还原
	 * @param view
	 */
	public void smsRestore(View view){
		
	}
	/**
	 * 打开程序锁
	 * @param view
	 */
	public void openAppLock(View view){
		Intent intent = new Intent(this,AppLockActivity.class);
		startActivity(intent);
	}
}
