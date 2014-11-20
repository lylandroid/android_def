package com.itheima.mynews35;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.itheima.mynews35.fragments.HomeFragment;
import com.itheima.mynews35.fragments.MenuFragment2;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

public class MainActivity extends SlidingFragmentActivity {

	private SlidingMenu slidingMenu;
	private MenuFragment2 menu;
	private HomeFragment home;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setBehindContentView(R.layout.menu_frame);
		setContentView(R.layout.content_frame);

		slidingMenu = getSlidingMenu();
		// 
		slidingMenu.setMode(SlidingMenu.LEFT);
		// 
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		// 
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		home = new HomeFragment();
		menu = new MenuFragment2();
		

		replace(R.id.content, home, "HOME");
		replace(R.id.menu, menu, "MENU");
		
		Activity a;
		
		// Rect rect = new Rect();
		// rect.contains(r)
		
		
	}

	public void replaceFragment(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, fragment).commit();
		slidingMenu.toggle();
	}

	/**
	 * 
	 * 
	 * @param id
	 * @param fragment
	 * @param flag
	 */
	private void replace(int id, Fragment fragment, String flag) {
		getSupportFragmentManager().beginTransaction()
				.replace(id, fragment, flag).commit();
	}
	
	/**
	 * 获取滑动菜单中的Fragment
	 * @return
	 */
	public MenuFragment2 getMenuFragment() {
		return menu;
	}
	/**
	 * 获取滑动菜单
	 */
	public SlidingMenu getResultSlidingMenu(){
		return slidingMenu;
	}
	
	public HomeFragment getHomeFragment(){
		return (HomeFragment) getSupportFragmentManager().findFragmentByTag("HOME");
	}

}
