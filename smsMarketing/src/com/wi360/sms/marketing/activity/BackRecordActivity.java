package com.wi360.sms.marketing.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.activity.PotentialUserActivity.MyAdapter;
import com.wi360.sms.marketing.base.BaseActivity;
import com.wi360.sms.marketing.base.MyAsyncTask;
import com.wi360.sms.marketing.base.MyBaseAdapter;
import com.wi360.sms.marketing.bean.ResBean;
import com.wi360.sms.marketing.utils.ActivityAnimationUtils;

/**
 * 回拨记录页面
 * 
 * @author Administrator
 * 
 */
public class BackRecordActivity extends BaseActivity {

	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	@ViewInject(R.id.lv_content)
	private ListView lvContent;

	@Override
	public void initView() {
		view = View.inflate(context, R.layout.layout_back_record, null);
		ViewUtils.inject(this, view);
		txt_title.setText("回拨记录");
	}

	@Override
	public void initListener() {

	}

	@Override
	public void initValue() {
		new MyAsyncTask<ResBean>(context, "加载中") {

			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				List<String> datas = new ArrayList<String>();
				datas.add("1");
				datas.add("1");
				MyAdapter myAdapter = new MyAdapter(context, datas);
				lvContent.setAdapter(myAdapter);
				return null;
			}

			@Override
			protected void onPostExecute(String msg) {
				super.onPostExecute(msg);
			}
		}.execute(new String[] {});
	}

	class MyAdapter extends MyBaseAdapter<String, View> {

		public MyAdapter(Activity context, List<String> lists) {
			super(context, lists);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.item_layout_back_record_list, null);
			}
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityAnimationUtils.rightToLeftInAnimation(context, BackRecordDescActivity.class);
				}
			});
			return convertView;
		}

	}

}
