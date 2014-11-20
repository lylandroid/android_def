package com.example.googleplay35;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.example.googleplay35.fragment.BaseFragment;
import com.example.googleplay35.fragment.FragmentFactory;
import com.example.googleplay35.utils.UIUtils;
import com.mwqi.ui.widget.PagerTab;

public class MainActivity extends BaseActivity {

	private PagerTab tabs;
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initActionBar() {

	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_main);
		tabs = (PagerTab) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(
				getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		tabs.setViewPager(pager);
		tabs.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {
			BaseFragment fragment = FragmentFactory.createFragment(position);
			fragment.show();
		}

	}

	// viewpager里面需要放入fragment，所以实现FragmentStatePagerAdapter比实现pageradaptre
	public class ViewPagerAdapter extends FragmentStatePagerAdapter {

		private String[] tab_names;

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			tab_names = UIUtils.getStringArray(R.array.tab_names);
		}
		

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return FragmentFactory.createFragment(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return tab_names[position];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return tab_names.length;
		}

	}

}
