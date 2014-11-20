package com.wi360.sms.marketing.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.base.BaseActivity;
import com.wi360.sms.marketing.service.StartingUpService;
import com.wi360.sms.marketing.utils.ActivityAnimationUtils;
import com.wi360.sms.marketing.utils.AppUtils;

public class MainActivity extends BaseActivity {

	@ViewInject(R.id.main_radio)
	private RadioGroup radioGroup;

	@ViewInject(R.id.ib_refresh)
	private ImageView ib_refresh;

	@Override
	public void initView() {
		// 判断服务是否在运行状态
		if (!AppUtils.isServiceRunning(context,
				StartingUpService.class.getName())) {
			Intent myIntent = new Intent(context, StartingUpService.class);
			context.startService(myIntent);
		}
		view = View.inflate(context, R.layout.activity_main, null);
		ViewUtils.inject(this, view);
	}

	@Override
	public void initListener() {
		ib_refresh.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ScaleAnimation scaleAnimation = null;
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					scaleAnimation = new ScaleAnimation(1.0f, 1.06f, 1.0f,
							1.06f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					scaleAnimation.setDuration(100);
					scaleAnimation.setFillAfter(true);
					ib_refresh.startAnimation(scaleAnimation);
					break;
				case MotionEvent.ACTION_UP:
					scaleAnimation = new ScaleAnimation(1.0f, 0.96f, 1.0f,
							0.96f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					scaleAnimation.setDuration(100);
					scaleAnimation.setFillAfter(true);
					scaleAnimation
							.setAnimationListener(new AnimationListener() {
								@Override
								public void onAnimationStart(Animation animation) {
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
								}

								@Override
								public void onAnimationEnd(Animation animation) {
								}
							});
					ib_refresh.startAnimation(scaleAnimation);
					break;
				}

				return true;
			}
		});
	}

	@Override
	public void initValue() {

	}

	@OnClick({ R.id.rb_potential_user, R.id.rb_back_record, R.id.rb_my,
			R.id.ll_wait_content })
	public void myOnclick(View v) {
		if (v != null) {
			switch (v.getId()) {
			case R.id.rb_potential_user:// 潜在用户
				ActivityAnimationUtils.rightToLeftInAnimation(context,
						PotentialUserActivity.class);
				break;
			case R.id.rb_back_record:// 回拨记录
				ActivityAnimationUtils.rightToLeftInAnimation(context,
						BackRecordActivity.class);
				break;
			case R.id.rb_my:// 我的
				ActivityAnimationUtils.rightToLeftInAnimation(context,
						MyActivity.class);
				break;
			case R.id.ll_wait_content:// 我的
				ActivityAnimationUtils.rightToLeftInAnimation(context,
						WaitExtensionActivity.class);
				break;
			}
		}
	}

	private long currentTime = 0;

	@Override
	public boolean myOnKeyDown(int keyCode, KeyEvent event, Class<?>... clazz) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (currentTime > 0) {
				finish();
				return true;
			}
			currentTime = System.currentTimeMillis();
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						currentTime = 0;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
			Toast.makeText(this, "再点一次退出", 0).show();
		}
		return true;
	}

}
