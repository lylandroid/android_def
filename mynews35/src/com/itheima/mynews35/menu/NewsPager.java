package com.itheima.mynews35.menu;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.mynews35.R;
import com.itheima.mynews35.base.BasePager;
import com.itheima.mynews35.domain.NewCenter.ChildrenItem;
import com.itheima.mynews35.domain.NewCenter.NewsCenter;
import com.itheima.mynews35.news.category.NewsItemCategory;
import com.itheima.mynews35.pagerindicator.TabPageIndicator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 新闻内容页面 android:theme="@style/Theme.PageIndicatorDefaults" Activity需要添加这一个样式
 * 
 * @author Administrator
 * 
 */
public class NewsPager extends BasePager {

	/**
	 * 新闻数据内容
	 */
	private NewsCenter newsCenter;
	/**
	 * 新闻页面,水平菜单滚动条
	 */
	@ViewInject(R.id.indicator)
	private TabPageIndicator indicator;

	/**
	 * 菜单对应的滚动ViewPager
	 */
	@ViewInject(R.id.view_pager)
	private ViewPager viewPager;

	private List<NewsItemCategory> lists;

	/**
	 * 新闻水平滑动菜单中默认选中下标
	 */
	private int mCurrentIndex;

	public NewsPager(Context context, NewsCenter newsCenter) {
		super(context);
		this.newsCenter = newsCenter;

		lists = new ArrayList<NewsItemCategory>();
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.frag_news, null);
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData() {
		lists.clear();
		// 初始化新闻页面下边,listView的数据
		for (ChildrenItem item : newsCenter.children) {
			lists.add(new NewsItemCategory(context, item.url));
		}

		viewPager.setAdapter(new MyPagerAdapter(lists));
		indicator.setViewPager(viewPager);
		indicator.setCurrentItem(mCurrentIndex);
		
		lists.get(0).initData();

		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mCurrentIndex = position;
				
				
				//在滑动的时候判断是否是第一个条目
				if(position == 0){
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
				}else{
					slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				}
				
				BasePager basepager = lists.get(position);
				//如果没有加载过数据,初始化数据
				if(!basepager.is_load){
					basepager.initData();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	class MyPagerAdapter extends PagerAdapter {

		private List<NewsItemCategory> pages;

		MyPagerAdapter(List<NewsItemCategory> pages) {
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
			container.removeView((View) object);
		}

		@Override 
		public CharSequence getPageTitle(int position) {
			return newsCenter.children.get(position).title;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(lists.get(position).getRootView());
			return lists.get(position).getRootView();
		}

	}

}
