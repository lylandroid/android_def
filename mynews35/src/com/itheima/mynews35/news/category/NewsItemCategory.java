package com.itheima.mynews35.news.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.itheima.mynews35.DetailAct;
import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.R;
import com.itheima.mynews35.adapter.MyNewsAdapter;
import com.itheima.mynews35.base.BasePager;
import com.itheima.mynews35.domain.News;
import com.itheima.mynews35.domain.News.NewsItem;
import com.itheima.mynews35.domain.News.TopNews;
import com.itheima.mynews35.utils.HMApi;
import com.itheima.mynews35.utils.SharedPreferencesUtils;
import com.itheima.mynews35.view.RollViewPager;
import com.itheima.mynews35.view.pullrefreshview.PullToRefreshBase;
import com.itheima.mynews35.view.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.itheima.mynews35.view.pullrefreshview.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 展示listView数据
 * 
 * @author Administrator
 * 
 */
public class NewsItemCategory extends BasePager {
	/**
	 * 每一类新闻资源的url
	 */
	private String url;
	/**
	 * 滚动ViewPager的view
	 */
	private View rollView;
	/**
	 * 滚动viewPager的资源文件
	 */
	@ViewInject(R.id.top_news_viewpager)
	private LinearLayout top_news_viewpager;
	/**
	 * 点的资源文件
	 */
	@ViewInject(R.id.dots_ll)
	private LinearLayout dots_ll;

	/**
	 * 滚动ViewPager的文字描述id
	 */
	@ViewInject(R.id.top_news_title)
	private TextView top_news_title;

	/**
	 * 新闻内容的listView
	 */
	@ViewInject(R.id.lv_item_news)
	private PullToRefreshListView pullListView;
	// 加载跟多数据的url地址
	private String moreUrl;

	private List<NewsItem> newsItems;
	private MyNewsAdapter newsAdapter;

	public NewsItemCategory(Context context, String url) {
		super(context);
		this.url = url;
		System.out.println(HMApi.BASE_URL + url);
		// 初始化数据
		// initData();
		newsItems = new ArrayList<NewsItem>();

	}

	@Override
	public View initView() {
		rollView = View.inflate(context, R.layout.layout_roll_view, null);
		// 展现内容的listView
		view = View.inflate(context, R.layout.frag_item_news, null);

		ViewUtils.inject(this, rollView);
		ViewUtils.inject(this, view);
		// --------------------------------------------------------
		// 设置下拉加载不可用
		pullListView.setPullLoadEnabled(false);
		// 设置滚动加载
		pullListView.setScrollLoadEnabled(true);
		// 给listview设置刷新监听
		pullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadNetworkData(url, true);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadNetworkData(moreUrl, false);
			}
		});

		pullListView.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					private NewsItem item;

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (pullListView.getRefreshableView()
								.getHeaderViewsCount() > 0) {
							item = newsItems.get(position-1);
						}else{
							item = newsItems.get(position);
						}
						item.is_read = true;
						
						newsAdapter.notifyDataSetChanged();
						
						Intent intent = new Intent(context,DetailAct.class);
						intent.putExtra("url", item.url);
						((MainActivity)context).startActivity(intent);
						
						
					}
				});

		return view;

	}

	@Override
	public void initData() {
		// 获取缓存数据
		String jsonStr = SharedPreferencesUtils.getString(context,
				HMApi.BASE_URL + url, null);
		if (!TextUtils.isEmpty(jsonStr)) {
			parseJsonData(jsonStr, true);
		}
		loadNetworkData(HMApi.BASE_URL + url, true);
	}

	/**
	 * 获取网络数据
	 */
	private void loadNetworkData(final String url, final boolean is_refresh) {
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				System.out.println("NewsItemCategory onFailure访问网络数据失败!!!");
			}

			@Override
			public void onSuccess(ResponseInfo<String> response) {
				//System.out.println(response.result);

				Gson gson = new Gson();
				News news = gson.fromJson(response.result, News.class);
				// 如果网络获取数据成功
				if (news.retcode == 200) {
					// 缓存网络数据
					SharedPreferencesUtils.saveString(context, url,
							response.result);
					moreUrl = news.data.more;
					// 解析json数据
					parseJsonData(response.result, is_refresh, news);
					is_load = true;

				}
			}
		});

	}

	/**
	 * 解析json数据
	 * 
	 * @param jsonStr
	 */
	public void parseJsonData(String jsonStr, boolean is_refresh, News... newss) {
		News news = null;
		if (newss == null || newss.length < 1) {
			Gson gson = new Gson();
			news = gson.fromJson(jsonStr, News.class);
		} else {
			news = newss[0];
		}
		if (is_refresh) {
			if (news.retcode == 200) {
				// 如果没有加载过网络数据
				is_load = true;
				List<String> logoImageUrls = new ArrayList<String>();
				List<String> logoTitles = new ArrayList<String>();
				// 在初始化数据之前清空集合
				logoImageUrls.clear();
				logoTitles.clear();
				// 把logo展示的数据分装到对象中
				for (TopNews topNews : news.data.topnews) {
					logoImageUrls.add(topNews.topimage);
					logoTitles.add(topNews.title);
				}
				// 把滚动的ViewPager初始化,并把相应的资源id穿进去进行相应的处理
				RollViewPager rollViewPager = new RollViewPager(context,
						dots_ll, top_news_title, logoImageUrls, logoTitles);
				top_news_viewpager.removeAllViews();
				top_news_viewpager.addView(rollViewPager);
				if (pullListView.getRefreshableView().getHeaderViewsCount() < 1) {
					// 给listView添加头
					pullListView.getRefreshableView().addHeaderView(rollView);
				}
			}
			if (is_refresh) {
				newsItems.clear();
			}
			newsItems.addAll(news.data.news);

			if (newsAdapter == null) {
				newsAdapter = new MyNewsAdapter(context, newsItems);
				// 解析数据成功后,给listView添加适配器,添加数据
				pullListView.getRefreshableView().setAdapter(newsAdapter);
			} else {
				newsAdapter.notifyDataSetChanged();
			}

			if (!TextUtils.isEmpty(moreUrl)) {
				// 设置是否有分页数据
				pullListView.setHasMoreData(true);
			} else {
				pullListView.setHasMoreData(false);
			}
			// 设置下拉刷新,滚动加载完成的方法
			pullListView.onPullDownRefreshComplete();
			pullListView.onPullUpRefreshComplete();
		}

	}

}
