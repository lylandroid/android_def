package com.wi360.mobile.wallet;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.base.MyBaseAdapter;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.bean.ResultBean.Content.Records;
import com.wi360.mobile.wallet.bean.TransactionsDetailsBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.UIUtils;

/**
 * 交易明细
 * 
 * @author Administrator
 * 
 */
public class TransactionDetailActivity extends BaseActivity {

	private ListView listView;
	private ResultBean resultBean;
	private MyAdapter myAdapter;
	private boolean isLoadData = false;
	private List<Records> lists;
	private View loadView;

	/**
	 * 总共多少页数据
	 */
	private int totalPage;
	/**
	 * 当前页码,默认为1
	 */
	private int currentPageIndex = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		setContentView(R.layout.layout_transactions_desc);
		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("交易明细");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);
		//
		listView = (ListView) findViewById(R.id.ll_transactions_list);
		listView.setDivider(null);
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myOnKeyDown();
			}
		});
	}

	@Override
	public boolean initData() {
		Intent intent = getIntent();
		resultBean = (ResultBean) intent.getSerializableExtra("bean");
		if (resultBean != null && resultBean.content != null && resultBean.content.records != null) {
			lists = resultBean.content.records;
			// 记录条数
			int totalRecords = resultBean.content.totalRecords;
			// 总共多少页数据
			totalPage = (totalRecords % resultBean.content.pageSize) > 0 ? totalRecords / lists.size() + 1
					: totalRecords / lists.size();
			myAdapter = new MyAdapter(context, listView, lists);
			listView.setAdapter(myAdapter);
			listView.setOnScrollListener(new OnScrollListener() {
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// 当不滚动时
					if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
						System.out.println(view.getLastVisiblePosition() + "===" + view.getCount());
						// 判断滚动到底部
						if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
							// 然后 经行一些业务操作
							if (lists != null && lists.size() > 0) {
								loadMore();
							}
						}
					}
				}

				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub

				}

			});
		}

		return false;
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(TransactionDetailActivity.this, CardDetailActivity.class);
		return false;
	}

	class MyAdapter extends MyBaseAdapter<Records, View> {

		private Records records;
		private ListView listView;

		public MyAdapter(Context context, ListView listView, List<Records> lists) {
			super(context, lists);
			this.listView = listView;
			loadView = View.inflate(context, R.layout.list_more_loading, null);
			listView.addFooterView(loadView);
			loadView.setVisibility(View.GONE);

		}

		private HolderView holder = null;
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				holder = new HolderView();
				convertView = View.inflate(context, R.layout.item_layout_detail_list, null);
				holder.number = (TextView) convertView.findViewById(R.id.tv_number);
				holder.date = (TextView) convertView.findViewById(R.id.tv_date);
				holder.name = (TextView) convertView.findViewById(R.id.tv_name);
				holder.momey = (TextView) convertView.findViewById(R.id.tv_momey);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			records = lists.get(position);
			holder.number.setText(records.cardNo);
			holder.date.setText(records.transDate);
			holder.name.setText(records.shopName);
			holder.momey.setText(records.amount + "元");
			return convertView;
		}

		class HolderView {
			public TextView number;
			public TextView date;
			public TextView name;
			public TextView momey;
		}

	}

	/**
	 * 加载更多网络数据
	 */
	public void loadMore() {
		// 是否在加载数据中
		if (isLoadData) {
			return;
		}
		isLoadData = true;
		int totalRecords = resultBean.content.totalRecords;
		// 如果没有跟多数据
		if (totalRecords <= lists.size()) {
			return;
		}
		// 当前页面码是否为最大页码
		if (++currentPageIndex > totalPage) {
			UIUtils.showToast(context, "没有更多数据了");
			return;
		}
		loadView.setVisibility(View.VISIBLE);
		TransactionsDetailsBean detailBean = new TransactionsDetailsBean(context, lists.get(0).cardNo, currentPageIndex
				+ "");
		String json = GsonTools.createGsonString(detailBean);
		new MyAsyncTask<TransactionsDetailsBean>(TransactionDetailActivity.this, null) {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					String resStr = responseInfo.result;
					resBean = GsonTools.changeGsonToBean(resStr, ResultBean.class);
					if (resBean != null && resBean.errcode == 0 && resBean.content != null
							&& resBean.content.records != null && resBean.content.records.size() > 0) {
						myAdapter.lists.addAll(resBean.content.records);
					}
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				loadView.setVisibility(View.GONE);
				myAdapter.notifyDataSetChanged();
				isLoadData = false;
			};
		}.execute(new String[] { Constants.GET_DETAIL_URL, json });
	}

	// * * class ShowDataBean { public ShowDataBean(String transDate, String
	// * cardNo, String shopName, float amount) { this.transDate = transDate;
	// * this.cardNo = cardNo; this.shopName = shopName; this.amount = amount; }
	// *
	// * // 日期 public String transDate; public String cardNo; // 店面 public
	// String
	// * shopName; public float amount; }

}
