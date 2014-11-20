package com.huitong.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huitong.huitongbao.R;
import com.huitong.huitongbao.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private HomeFragment homeFragment;
	// private HelperFragment helperFragment;
	private PurseFragment purseFragment;
	private ContactFragment contactFragment;
	private int currentItem = -1;
	private FrameLayout[] bottomMenus = new FrameLayout[4];
	private TextView[] bottomMenuNames = new TextView[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);

		bottomMenuNames[0] = (TextView) findViewById(R.id.bottom_menu1_name);
		bottomMenuNames[1] = (TextView) findViewById(R.id.bottom_menu2_name);
		bottomMenuNames[2] = (TextView) findViewById(R.id.bottom_menu3_name);
		bottomMenuNames[3] = (TextView) findViewById(R.id.bottom_menu4_name);
		bottomMenuNames[0].setTextColor(Color.GRAY);
		bottomMenuNames[1].setTextColor(Color.GRAY);
		bottomMenuNames[2].setTextColor(Color.GRAY);
		bottomMenuNames[3].setTextColor(Color.GRAY);
		bottomMenus[0] = (FrameLayout) findViewById(R.id.bottom_menu1);
		bottomMenus[1] = (FrameLayout) findViewById(R.id.bottom_menu2);
		bottomMenus[2] = (FrameLayout) findViewById(R.id.bottom_menu3);
		bottomMenus[3] = (FrameLayout) findViewById(R.id.bottom_menu4);
		bottomMenus[0].setOnClickListener(this);
		bottomMenus[1].setOnClickListener(this);
		bottomMenus[2].setOnClickListener(this);
		bottomMenus[3].setOnClickListener(this);

		// ��ʼ�����е�fagment
		homeFragment = new HomeFragment(this);
		// helperFragment = new HelperFragment(this);
		purseFragment = new PurseFragment(this);
		contactFragment = new ContactFragment(this);
		Bundle bundle = getIntent().getExtras();
		homeFragment.setArguments(bundle);
		// helperFragment.setArguments(bundle);
		purseFragment.setArguments(bundle);
		contactFragment.setArguments(bundle);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.main_content, homeFragment);
		// ft.add(R.id.main_content, helperFragment);
		ft.add(R.id.main_content, purseFragment);
		ft.add(R.id.main_content, contactFragment);
		ft.commit();
		selectItem(0);
	}

	private void selectItem(int item) {
		if (currentItem == item) {
			return;
		}
		currentItem = item;
		for (int i = 0; i < bottomMenuNames.length; i++) {
			if (i == item) {
				bottomMenuNames[i].setTextColor(Color.BLUE);
			} else {
				bottomMenuNames[i].setTextColor(Color.GRAY);
			}
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (currentItem) {
		case 0:
			ft.show(homeFragment);
			// ft.hide(helperFragment);
			ft.hide(purseFragment);
			ft.hide(contactFragment);
			break;
		case 1:
			ft.hide(homeFragment);
			// ft.show(helperFragment);
			ft.hide(purseFragment);
			ft.hide(contactFragment);
			break;
		case 2:
			ft.hide(homeFragment);
			// ft.hide(helperFragment);
			ft.show(purseFragment);
			ft.hide(contactFragment);
			break;
		case 3:
			ft.hide(homeFragment);
			// ft.hide(helperFragment);
			ft.hide(purseFragment);
			ft.show(contactFragment);
			break;
		default:
			break;
		}
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bottom_menu1:
			selectItem(0);
			break;
		case R.id.bottom_menu2:
			selectItem(1);
			break;
		case R.id.bottom_menu3:
			selectItem(2);
			break;
		case R.id.bottom_menu4:
			selectItem(3);
			break;

		default:
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && homeFragment.getWebView().canGoBack()) {
			 homeFragment.getWebView().goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
