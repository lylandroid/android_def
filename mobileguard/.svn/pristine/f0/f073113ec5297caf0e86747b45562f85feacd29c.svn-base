package com.itheima.mobileguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobileguard.R;

public class EnterPwdActivity extends Activity {
	private EditText et_password;
	private String packname;
	private ImageView iv_lock_appicon;
	private TextView tv_lock_appname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_pwd);
		et_password = (EditText) findViewById(R.id.et_password);
		packname = getIntent().getStringExtra("packname");
		tv_lock_appname = (TextView) findViewById(R.id.tv_lock_appname);
		iv_lock_appicon = (ImageView) findViewById(R.id.iv_lock_appicon);
		PackageManager pm = getPackageManager();
		try {
			iv_lock_appicon.setImageDrawable(pm.getApplicationInfo(packname, 0).loadIcon(pm));
			tv_lock_appname.setText(pm.getApplicationInfo(packname, 0).loadLabel(pm).toString());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void click(View view){
		String pwd = et_password.getText().toString().trim();
		if("123".equals(pwd)){
			//通知看门狗 ，这个是熟人， 停止保护。
			//发送自定义的广播消息。
			Intent intent = new Intent();
			intent.setAction("com.itheima.mobileguard.stopprotect");
			intent.putExtra("packname", packname);
			sendBroadcast(intent);
			finish();//关闭输入密码的界面。
		}else{
			Toast.makeText(this, "密码不正确", 0).show();
			Animation aa = AnimationUtils.loadAnimation(this, R.anim.shake);
			et_password.startAnimation(aa);
		}
	}
	
	
	@Override
	public void onBackPressed() {
		//回桌面。
		Intent intent = new Intent();
		intent.setAction("android.intent.action.MAIN" );
		intent.addCategory("android.intent.category.HOME");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addCategory("android.intent.category.MONKEY");
		startActivity(intent);
		finish();//关闭掉输入密码的界面。
	}
}
