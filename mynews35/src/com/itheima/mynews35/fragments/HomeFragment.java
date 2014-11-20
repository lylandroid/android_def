package com.itheima.mynews35.fragments;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.itheima.mynews35.R;
import com.itheima.mynews35.adapter.MyOnPageChangeListenerAdapter;
import com.itheima.mynews35.base.BaseFragment;
import com.itheima.mynews35.base.BasePager;
import com.itheima.mynews35.domain.NewCenter.NewsCenter;
import com.itheima.mynews35.home.FunctionPager;
import com.itheima.mynews35.home.GovAffairsPager;
import com.itheima.mynews35.home.NewsCenterPager;
import com.itheima.mynews35.home.SettingPager;
import com.itheima.mynews35.home.SmartServicePager;
import com.itheima.mynews35.view.MyViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnCheckedChange;

public class HomeFragment extends BaseFragment {
	@ViewInject(R.id.view_pager)
	private MyViewPager view_pager;

	@ViewInject(R.id.main_radio)
	private RadioGroup main_radio;

	private List<BasePager> pages;

	@Override
	public void initData() {

		pages = new ArrayList<BasePager>();

		pages.add(new FunctionPager(context));
		pages.add(new NewsCenterPager(context));
		pages.add(new SmartServicePager(context));
		pages.add(new GovAffairsPager(context));
		pages.add(new SettingPager(context));
		// 默认选中主页
		main_radio.check(R.id.rb_function);

		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(pages);
		view_pager.setAdapter(pagerAdapter);
		//
		view_pager.setOnPageChangeListener(new MyOnPageChangeListenerAdapter() {
			@Override
			public void onPageSelected(int position) {
				// view_pager.setCurrentItem(position, false);
				if (!isCacheData) {
					isCacheData = true;
					pages.get(position).initData();
				}
			}
		});
	}

	@Override
	public View initView(LayoutInflater inflater) {

		view = View.inflate(getActivity(), R.layout.frag_home, null);
		ViewUtils.inject(this, view);
		view.findViewById(R.id.bottom);
		return view;
	}

	@OnCheckedChange(R.id.main_radio)
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.rb_function:
			view_pager.setCurrentItem(0, false);
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			break;

		case R.id.rb_news_center:
			view_pager.setCurrentItem(1, false);
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		case R.id.rb_smart_service:
			view_pager.setCurrentItem(2, false);
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		case R.id.rb_gov_affairs:
			view_pager.setCurrentItem(3, false);
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		case R.id.rb_setting:
			view_pager.setCurrentItem(4, false);
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			break;
		}
	}

	/**
	 * 获取主页中的item
	 * 
	 * @param position
	 * @return
	 */
	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) pages.get(1);
	}

	/**
	 * 
	 * myViewPager的数据适配器
	 * 
	 * @author Administrator
	 * 
	 */
	class ViewPagerAdapter extends PagerAdapter {

		private List<BasePager> pages;

		public ViewPagerAdapter(List<BasePager> pages) {
			this.pages = pages;
		}

		@Override
		public int getCount() {
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//
			// container.removeView((View) object);
			((MyViewPager) container).removeView(pages.get(position)
					.getRootView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 把该view添加到viewPager
			((MyViewPager) container).addView(
					pages.get(position).getRootView(), 0);
			//
			return pages.get(position).getRootView();
		}

	}

}
