package com.wi360.mobile.wallet.news.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.adapter.NewsAdapter;
import com.wi360.mobile.wallet.base.BasePager;
import com.wi360.mobile.wallet.bean.NewsItemCategoryBean;
import com.wi360.mobile.wallet.bean.NewsItemCategoryBean.News;
import com.wi360.mobile.wallet.bean.NewsItemCategoryBean.TopNews;
import com.wi360.mobile.wallet.utils.CommonUtil;
import com.wi360.mobile.wallet.utils.HMApi;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;
import com.wi360.mobile.wallet.view.RollViewPager;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2014
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2014-8-29 下午3:28:41
 * 
 * 描 述 ：
 * 
 * 展示listview的数据 修订历史 ：
 * 
 * ============================================================
 **/
public class NewsItemCategory extends BasePager {
	private String url;
	@ViewInject(R.id.top_news_title)
	private TextView top_news_title;
	@ViewInject(R.id.dots_ll)
	private LinearLayout dots_ll;
	private NewsItemCategoryBean newsItemCategoryBean;
	@ViewInject(R.id.top_news_viewpager)
	private LinearLayout top_news_viewpager;
//	@ViewInject(R.id.lv_item_news)
//	private PullToRefreshListView ptrlv;

	public NewsItemCategory(Context ct, String url) {
		super(ct);
		this.url = url;
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.frag_item_news, null);
		top_view = View.inflate(context, R.layout.layout_roll_view, null);
		ViewUtils.inject(this, view);
		ViewUtils.inject(this, top_view);
		// 设置上拉加载不可用
//		ptrlv.setPullLoadEnabled(false);
//		// 设置滚动加载
//		ptrlv.setScrollLoadEnabled(true);
//
//		ptrlv.setOnRefreshListener(new OnRefreshListener<ListView>() {
//
//			@Override
//			public void onPullDownToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				getNewsData(url, true);
//			}
//
//			@Override
//			public void onPullUpToRefresh(
//					PullToRefreshBase<ListView> refreshView) {
//				// TODO Auto-generated method stub
//				getNewsData(moreUrl, false);
//			}
//
//		});
//		
//		
//
//		ptrlv.getRefreshableView().setOnItemClickListener(
//				new OnItemClickListener() {
//					News newsItem = null;
//
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view,
//							int position, long id) {
//						if (ptrlv.getRefreshableView().getHeaderViewsCount() > 0) {
//							newsItem = news.get(position - 1);
//						} else {
//							newsItem = news.get(position);
//						}
//
//						if (!newsItem.is_read) {
//							newsItem.is_read = true;
//						}
//						adapter.notifyDataSetChanged();
//						
////						Intent intent = new Intent(context,DetailAct.class);
////						intent.putExtra("url", newsItem.url);
////						context.startActivity(intent);
//						
//					}
//
//				});
//		
//		ptrlv.setLastUpdatedLabel(CommonUtil.getStringDate());
		return view;
	}

	@Override
	public void initData() {
		String result = SharedPreferencesUtils.getString(context, HMApi.BASE_URL
				+ url, "");

		if (!TextUtils.isEmpty(result)) {
			processData(result, true);
		}

		getNewsData(url, true);
	}

	private void getNewsData(final String url, final boolean is_refresh) {
		// TODO Auto-generated method stub
		loadData(HttpMethod.GET, HMApi.BASE_URL + url, null,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						LogUtils.d(responseInfo.result);
						SharedPreferencesUtils.saveString(context, HMApi.BASE_URL
								+ url, responseInfo.result);
						processData(responseInfo.result, is_refresh);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});
	}

	private List<String> mTitleLists;
	private List<String> mImageUrlLists;
	private List<View> dotLists;
	private View top_view;
	private List<News> news = new ArrayList<News>();
	private NewsAdapter adapter;
	private String moreUrl;

	protected void processData(String result, boolean is_refresh) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		newsItemCategoryBean = gson
				.fromJson(result, NewsItemCategoryBean.class);

		if (is_refresh) {
			// 如果等于200的话，表示跟服务器交互成功
			if (newsItemCategoryBean.retcode == 200) {
				is_load = true;
				// NewsItem data = newsItemCategoryBean.data;
				mTitleLists = new ArrayList<String>();
				mImageUrlLists = new ArrayList<String>();
				for (TopNews item : newsItemCategoryBean.data.topnews) {
					mTitleLists.add(item.title);
					mImageUrlLists.add(item.topimage);
				}

				initDot(newsItemCategoryBean.data.topnews.size());

				RollViewPager mRollViewPager = new RollViewPager(context, dotLists);
				// 把 描述信息传过去
				mRollViewPager.setTitleLists(top_news_title, mTitleLists);
				// 把图片的url地址传过去
				mRollViewPager.setImageUrlLists(mImageUrlLists);
				// 让viewpagr自己跳动
				mRollViewPager.startRoll();

				top_news_viewpager.removeAllViews();
				top_news_viewpager.addView(mRollViewPager);

//				// 判断当前的这个listview是否有头
//				if (ptrlv.getRefreshableView().getHeaderViewsCount() < 1) {
//					ptrlv.getRefreshableView().addHeaderView(top_view);
//				}

			}
		}

		moreUrl = newsItemCategoryBean.data.more;
		if (is_refresh) {
			news.clear();
			news.addAll(newsItemCategoryBean.data.news);
		} else {
			news.addAll(newsItemCategoryBean.data.news);
		}
//
//		if (adapter == null) {
//			adapter = new NewsAdapter(context, news);
//			ptrlv.getRefreshableView().setAdapter(adapter);
//		} else {
//			adapter.notifyDataSetChanged();
//		}

//		if (!TextUtils.isEmpty(moreUrl)) {
//			// 设置是否有分页数据
//			ptrlv.setHasMoreData(true);
//		} else {
//			ptrlv.setHasMoreData(false);
//		}
//		//设置下拉刷新和滚动加载完成的方法
//		ptrlv.onPullDownRefreshComplete();
//		ptrlv.onPullUpRefreshComplete();
	}

	/**
	 * 初始化点
	 * 
	 * @param size
	 */
	private void initDot(int size) {
		dotLists = new ArrayList<View>();
		dots_ll.removeAllViews();

		for (int i = 0; i < size; i++) {

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					CommonUtil.dip2px(context, 6), CommonUtil.dip2px(context, 6));
			// 初始化点
			View m = new View(context);
			// 设置点的左右的间距
			params.setMargins(5, 0, 5, 0);
			// 把点的宽高添加进来
			m.setLayoutParams(params);

			if (i == 0) {
				m.setBackgroundResource(R.drawable.dot_focus);
			} else {
				m.setBackgroundResource(R.drawable.dot_normal);
			}

			dotLists.add(m);

			dots_ll.addView(m);
		}

	}

}
