package com.wi360.mobile.wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.base.MyBaseAdapter;
import com.wi360.mobile.wallet.bean.MyVolumeDetailBean;
import com.wi360.mobile.wallet.bean.MyVolumeListBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.HttpUtils;

/**
 * 我的电子卷
 * 
 * @author Administrator
 * 
 */
public class MyVolumeActivity extends BaseActivity {
	String TAG = "MyVolumeActivity";
	@ViewInject(R.id.lv_content)
	private ListView lvContent;
	private ResultBean resultBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();

	}

	@Override
	public void initView() {
		View view = View.inflate(context, R.layout.layout_my_volume, null);
		ViewUtils.inject(context, view);
		setContentView(view);
		//
		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("我的电子卷");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myOnKeyDown();
			}
		});
		//
		// List<MyVolumeListBean> lists = new ArrayList<MyVolumeListBean>();
		// lists.add(new MyVolumeListBean("广州农商银行1元换购15元KFC现金卷", "15772586",
		// "有效期到：2014-06-06", 1));
		// lists.add(new MyVolumeListBean("广州农商银行1元换购15元KFC现金卷", "15772586",
		// "有效期到：2014-06-06", 1));
		// lists.add(new MyVolumeListBean("广州农商银行1元换购15元KFC现金卷", "15772586",
		// "有效期到：2014-06-06", 1));
		// lists.add(new MyVolumeListBean("广州农商银行1元换购15元KFC现金卷", "15772586",
		// "有效期到：2014-06-06", 1));
		// lists.add(new MyVolumeListBean("广州农商银行1元换购15元KFC现金卷", "15772586",
		// "有效期到：2014-06-06", 1));
		// lvContent.setAdapter(new MyAdapter(context, lists));
		// lvContent.setDivider(context.getResources().getDrawable(R.drawable.news_list_line));
		// lvContent.setSelector(R.drawable.bt_presssed);
		// lvContent.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// ActivityAnimationUtils.rightToLeftInAnimation(MyVolumeActivity.this,
		// VolumeDescActivity.class);
		// }
		// });
	}

	@Override
	public boolean initData() {
		Intent intent = getIntent();
		// 获取数据
		resultBean = (ResultBean) intent.getExtras().getSerializable("bean");
		// 如果查询出来有记录
		if (resultBean != null && resultBean.content != null && resultBean.content.records != null
				&& resultBean.content.records.size() > 0) {
			lvContent.setAdapter(new MyAdapter(context, resultBean.content.records));
			lvContent.setDivider(context.getResources().getDrawable(R.drawable.news_list_line));
			lvContent.setSelector(R.drawable.item_selector);
			// listView点击item时间,查询电子劵详情
			lvContent.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					MyVolumeDetailBean detailBean = new MyVolumeDetailBean(context, resultBean.content.records
							.get(position).assisCode);
					String json = GsonTools.createGsonString(detailBean);
					// json =
					// "{\"content\":{\"userToken\":{\"timeStamp\":\"1415935747783\",\"nonce\":\"637757405637\",\"token\":\"Vl1Vktrun8MA4698fytG84qaYhxIXnLQ\",\"appCode\":\"zjhtwallet\",\"signature\":\"99c8bc7e0ace4be49f00e9efbc4e5f3a\"},\"assisCode\":\"961384116\",\"requestType\":\"chinamobile_nfc\"},\"appNo\":\"ZJ206180\",\"service\":\"trade.queryBarCodeDetail\",\"ver\":\"1.0\"}";
					final Map<String, String> map = detailBean.getJson(context, json);
					// 连接网络
					new MyAsyncTask<MyVolumeDetailBean>(context, "查询中") {
						@Override
						public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
							String result = null;
							try {

								Log.i(TAG, map.toString());
								result = HttpUtils.URLPost(Constants.MY_VOLUME_DETAIL, map);
								resBean = GsonTools.changeGsonToBean(result, ResultBean.class);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return null;
						}

						protected void onPostExecute(String msg) {
							// 连接成功
							if (resBean != null && resBean.content != null) {
								if (resBean.content.respCode == null || resBean.content.respCode.length() > 0) {
									ActivityAnimationUtils.rightToLeftInAnimation(MyVolumeActivity.this,
											VolumeDescActivity.class, resBean);
									msg = null;
								} else {
									msg = resBean.errmsg;
								}
							}
							super.onPostExecute(msg);

						};
					}.execute(new String[] {});
				}
			});

		}

		return false;
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(MyVolumeActivity.this, MainActivity.class);
		return true;
	}

	class MyAdapter extends MyBaseAdapter<ResultBean.Content.Records, View> {

		public MyAdapter(Context context, List<ResultBean.Content.Records> lists) {
			super(context, lists);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.item_layout_my_volume, null);
				holder.title = (TextView) convertView.findViewById(R.id.tv_title);
				holder.number = (TextView) convertView.findViewById(R.id.tv_number);
				holder.date = (TextView) convertView.findViewById(R.id.tv_date);
				holder.momey = (TextView) convertView.findViewById(R.id.tv_momey);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(lists.get(position).couponName);
			holder.number.setText(lists.get(position).assisCode);
			holder.date.setText("有效期到：" + lists.get(position).endTime);
			holder.momey.setText(lists.get(position).status + " ");

			return convertView;
		}

	}

	static class ViewHolder {
		public TextView title;
		public TextView number;
		public TextView date;
		public TextView momey;
	}

}
