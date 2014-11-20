package com.itheima.mobileguard.activities;

import com.itheima.mobileguard.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class Setup4Activity extends BaseSetupActivity {
	private TextView tv_setup4_status;
	private CheckBox cb_setup4_protect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 得到系统的配置
		setContentView(R.layout.activity_setup4);
		tv_setup4_status = (TextView) findViewById(R.id.tv_setup4_status);
		cb_setup4_protect = (CheckBox) findViewById(R.id.cb_setup4_protect);
		
		cb_setup4_protect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					tv_setup4_status.setText("防盗保护已经开启");
				}else{
					tv_setup4_status.setText("防盗保护没有开启");
				}
				Editor editor = sp.edit();
				editor.putBoolean("protecting", isChecked);
				editor.commit();
			}
		});
		
		boolean protecting = sp.getBoolean("protecting", false);
		if(protecting){
			tv_setup4_status.setText("防盗保护已经开启");
			cb_setup4_protect.setChecked(true);
		}else{
			tv_setup4_status.setText("防盗保护没有开启");
			cb_setup4_protect.setChecked(false);
		}
	}

	public void showNext() {
		// 写一个配置信息
		Editor editor = sp.edit();
		editor.putBoolean("finishsetup", true);
		editor.commit();
		startActivityAndFinishSelf(LostFindActivity.class);
	}

	public void showPre() {
		startActivityAndFinishSelf(Setup3Activity.class);
	}
}
