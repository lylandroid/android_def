package com.wi360.mobile.wallet.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemSelected;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;
import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.base.BaseFragment;
import com.wi360.mobile.wallet.base.BasePager;
import com.wi360.mobile.wallet.home.CardManagerPager;
import com.wi360.mobile.wallet.home.HomePager;
import com.wi360.mobile.wallet.home.MomeyRechargePager;
import com.wi360.mobile.wallet.home.MyPager;
import com.wi360.mobile.wallet.view.LazyViewPager.OnPageChangeListener;
import com.wi360.mobile.wallet.view.MyViewPager;

/**
 * ============================================================ 作 者 : 罗一蓝
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2014-9-23
 * 
 * 描 述 ：钱包账户
 * 
 * 首页的fragment 修订历史 ：
 * 
 * ============================================================
 **/
public class HomeFragment extends BaseFragment {

	private View view;
	private List<BasePager> lists = new ArrayList<BasePager>();
	@ViewInject(R.id.view_pager)
	private MyViewPager view_pager;
	@ViewInject(R.id.main_radio)
	private RadioGroup main_radio;
	private int mCurrentId = R.id.rb_function;
	private ViewPagerAdapter pagerAdapter;

	private class ViewPagerAdapter extends PagerAdapter {
		private List<BasePager> pages;

		public ViewPagerAdapter(List<BasePager> lists) {
			this.pages = lists;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((MyViewPager) container).removeView(pages.get(position)
					.getRootView());
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((MyViewPager) container).addView(
					pages.get(position).getRootView(), 0);
			
			return pages.get(position).getRootView();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	/**
	 * 初始化布局
	 */
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.frag_home, null);
		ViewUtils.inject(this, view);
		return view;
	}

//	@OnCheckedChange(R.id.main_radio)
	@OnRadioGroupCheckedChange(R.id.main_radio)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_function:
			view_pager.setCurrentItem(0, false);
			// 设置不允许滑动
			// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			break;

		case R.id.rb_news_center:
			view_pager.setCurrentItem(1, false);
			// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			// NewsCenterPager newsCenterPager = new NewsCenterPager(ct);
			// newsCenterPager.initData();
			break;
		case R.id.rb_smart_service:
			view_pager.setCurrentItem(2, false);
			// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		case R.id.rb_gov_affairs:
			view_pager.setCurrentItem(3, false);
			// 设置不允许滑动
			// sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			break;
		}
	}

	public CardManagerPager getNewsCenterPager() {
		return (CardManagerPager) lists.get(1);
	}

	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		lists.clear();
		// 定义好底部的tab页面 Wallet
		lists.add(new HomePager(context));
		lists.add(new CardManagerPager(context));
		lists.add(new MomeyRechargePager(context));
		lists.add(new MyPager(context));

		if (pagerAdapter == null) {
			pagerAdapter = new ViewPagerAdapter(lists);
			view_pager.setAdapter(pagerAdapter);
		} else {
			pagerAdapter.notifyDataSetChanged();
		}

		// 设置单选按钮组默认的首页
		main_radio.check(mCurrentId);

		view_pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				BasePager basePager = lists.get(position);
				if (!is_load) {
					is_load = true;
					basePager.initData();
				}

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
	}

}
