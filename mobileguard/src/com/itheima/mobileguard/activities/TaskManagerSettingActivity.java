package com.itheima.mobileguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.services.AutoKillService;
import com.itheima.mobileguard.utils.SystemInfoUtils;

public class TaskManagerSettingActivity extends Activity {
	private CheckBox cb_show_system;
	private SharedPreferences sp;
	
	private CheckBox cb_lock_autokill;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager_setting);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		cb_show_system = (CheckBox) findViewById(R.id.cb_show_system);
		cb_show_system.setChecked(sp.getBoolean("showsystem", false));
		cb_show_system.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Editor editor = sp.edit();
				editor.putBoolean("showsystem", isChecked);
				editor.commit();
			}
		});
		final Intent intent = new Intent(this,AutoKillService.class);
		cb_lock_autokill = (CheckBox) findViewById(R.id.cb_lock_autokill);
		cb_lock_autokill.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					startService(intent);
				}else{
					stopService(intent);
				}
			}
		});
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		if(SystemInfoUtils.isServiceRunning(this, "com.itheima.mobileguard.services.AutoKillService")){
			cb_lock_autokill .setChecked(true);
		}else{
			cb_lock_autokill .setChecked(false);
		}
	}
}
