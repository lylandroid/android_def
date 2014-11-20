package com.itheima.mynews35.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.R;
import com.itheima.mynews35.base.BasePager;
import com.itheima.mynews35.domain.NewCenter;
import com.itheima.mynews35.domain.NewCenter.NewsCenter;
import com.itheima.mynews35.menu.MutualPager;
import com.itheima.mynews35.menu.NewsPager;
import com.itheima.mynews35.menu.PicPager;
import com.itheima.mynews35.menu.TopicPager;
import com.itheima.mynews35.utils.HMApi;
import com.itheima.mynews35.utils.SharedPreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NewsCenterPager extends BasePager {
	/**
	 * 新闻中心->菜单内存
	 */
	private List<String> menuNewworkTitleData;
	/**
	 * 新闻中心->所有子viewPager的集合
	 */
	private List<BasePager> pages;

	/**
	 * 新闻中心->展现内容的标签
	 */
	@ViewInject(R.id.news_center_fl)
	private FrameLayout news_center_fl;

	public NewsCenterPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.news_center_frame, null);
		ViewUtils.inject(this, view);
		initTitleBar();
		return view;
	}

	@Override
	public void initData() {

		pages = new ArrayList<BasePager>();
		menuNewworkTitleData = new ArrayList<String>();
		// 获取菜单缓存数据
		String cacheJson = SharedPreferencesUtils.getString(context,
				HMApi.NEWS_CENTER_CATEGORIES, null);
		if (!TextUtils.isEmpty(cacheJson)) {
			parseJsonData(cacheJson);
		}

		// 加载网咯资源
		loadNetworkNewsData();
	}

	/**
	 * 加载网络数据
	 */
	private void loadNetworkNewsData() {
		// 如果刚刚加载过缓存数据
		// if (is_cache_data) {
		// return;
		// }
		// String url = "http://192.168.2.39:8080/mwq/categories.json";
		// String url = "http://192.168.1.3:8080/mwq/categories.json";
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, HMApi.NEWS_CENTER_CATEGORIES,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						System.out.println("onFailure");
					}

					@Override
					public void onSuccess(ResponseInfo<String> response) {

						// 解析网咯数据
						parseJsonData(response.result);
					}
				});
	}

	/**
	 * 解析json数据
	 * 
	 * @param result
	 */
	private void parseJsonData(String jsonStr) {
		Gson gson = new Gson();
		NewCenter newCenter = gson.fromJson(jsonStr, NewCenter.class);
		if (newCenter.retcode == 200) {
			// 把网咯资源缓存到本地
			SharedPreferencesUtils.saveString(context,
					HMApi.NEWS_CENTER_CATEGORIES, jsonStr);
			
			menuNewworkTitleData.clear();
			//加载过数据
			is_load = true;
			//初始化菜单数据
			for (NewsCenter news : newCenter.data) {
				menuNewworkTitleData.add(news.title);
			}
			// 初始化菜单数据
			((MainActivity) context).getMenuFragment().initMenu(
					menuNewworkTitleData);

			// 初始化新闻中心,新闻页面数据
			pages.add(new NewsPager(context, newCenter.data.get(0)));
			pages.add(new TopicPager(context));
			pages.add(new PicPager(context));
			pages.add(new MutualPager(context));

			showNewsItemIndex(0);

		}
	}

	/**
	 * 返回该新页面的对象
	 * 
	 * @param position
	 * @return
	 */
	public BasePager getNewsItem(int position) {
		return pages.get(position);
	}

	/**
	 * 根据下标显示(显示ViewPager中的页面)ViewPager
	 * 
	 * @param position
	 *            fragment索引
	 */
	public void showNewsItemIndex(int position) {
		BasePager page = pages.get(position);
		switch (position) {
		case 0:
			txt_title.setText("新闻");
			break;
		case 1:
			txt_title.setText("专题");
			break;
		case 2:
			txt_title.setText("组图");
			break;
		case 3:
			txt_title.setText("互动");
			break;
		}

		news_center_fl.removeAllViews();
		news_center_fl.addView(page.getRootView());
		page.initData();

	}
}
