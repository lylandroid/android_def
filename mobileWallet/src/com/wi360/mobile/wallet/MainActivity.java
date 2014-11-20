package com.wi360.mobile.wallet;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import com.wi360.mobile.wallet.fragment.HomeFragment;

/**
 * ============================================================ 
 * 作 者 : 罗一蓝
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2014-8-27 上午11:16:20
 * 
 * 描 述 ： 初始化首页 修订历史 ：
 * 
 * ============================================================
 **/
public class MainActivity extends FragmentActivity {

	// private SlidingMenu sm;
	// private MenuFragment2 menu;
	private HomeFragment home;

	/**
	 * 当前时间
	 */
	private long currentTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置滑动菜单的页面
		// setBehindContentView(R.layout.menu_frame);
		// 设置主页面
		setContentView(R.layout.content_frame);

		// Fragment1 fragment1 = new Fragment1();
		// getSupportFragmentManager().beginTransaction().replace(R.id.content,
		// fragment1).commit();

		// 获得滑动菜单的对象
		// sm = getSlidingMenu();
		// 设置滑动菜单的模式
		/**
		 * SlidingMenu.LEFT 设置滑动菜单 在左边 SlidingMenu.RIGHT 设置滑动菜单在右边
		 * SlidingMenu.LEFT_RIGHT 设置滑动菜单左右都有
		 */
		// sm.setMode(SlidingMenu.LEFT);
		// 当滑动菜单出来之后，内容页面的剩余宽度
		// sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置滑动菜touch的模式
		/**
		 * SlidingMenu.TOUCHMODE_FULLSCREEN 设置滑动菜单全屏展示
		 * SlidingMenu.TOUCHMODE_MARGIN 设置滑动菜单只能在边沿滑动 SlidingMenu.TOUCHMODE_NONE
		 * 设置滑动菜单不能滑动
		 */
		// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置滑动菜单的阴影
		// sm.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单阴影的宽度
		// sm.setShadowWidthRes(R.dimen.shadow_width);

		home = new HomeFragment();
		// menu = new MenuFragment2();
		/**
		 * 第一个参数是需要进行替换的id 第二个参数是需要进行替换的fragment 第三个参数是标签的意思tag(别名)
		 */
		
//		FrameLayout content = (FrameLayout) findViewById(R.id.content);
//		content.add
		
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content, home, "HOME").commit();
		// getSupportFragmentManager().beginTransaction().replace(R.id.menu,
		// menu, "MENU").commit();

		/**
		 * 如果大家以后开发需要左右的滑动菜单，请打开注释，滑动菜单就做完了
		 */
		// RightMenuFragment rightMenuFragment = new RightMenuFragment();
		//
		// sm.setSecondaryMenu(R.layout.right_menu_frame);
		// sm.setSecondaryShadowDrawable(R.drawable.shadow);
		// getSupportFragmentManager().beginTransaction().replace(R.id.right_menu,
		// rightMenuFragment).commit();
	}

	// public void switchFragment(Fragment fragment) {
	// // TODO Auto-generated method stub
	// getSupportFragmentManager().beginTransaction().replace(R.id.content,
	// fragment).commit();
	// sm.toggle();
	// }
	// 返回menu的对象
	// public MenuFragment2 getMenuFragment2(){
	// menu = (MenuFragment2)
	// getSupportFragmentManager().findFragmentByTag("MENU");
	// return menu;
	// }

//	public HomeFragment getHomeFragment() {
//		home = (HomeFragment) getSupportFragmentManager().findFragmentByTag(
//				"HOME");
//		return home;
//	}

	/**
	 * 连续按2次返回键,推出应用
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (currentTime > 0) {
				return super.onKeyDown(keyCode, event);
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
			Toast.makeText(this, "再点一次返回手机钱包", 0).show();
		}
		return true;
	}

}
