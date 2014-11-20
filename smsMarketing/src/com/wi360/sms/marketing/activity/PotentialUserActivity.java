package com.wi360.sms.marketing.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.sms.marketing.R;
import com.wi360.sms.marketing.base.BaseActivity;
import com.wi360.sms.marketing.base.MyAsyncTask;
import com.wi360.sms.marketing.base.MyBaseAdapter;
import com.wi360.sms.marketing.bean.ResBean;
import com.wi360.sms.marketing.utils.ActivityAnimationUtils;

/**
 * 潜在客户页面
 * 
 * @author Administrator
 * 
 */
public class PotentialUserActivity extends BaseActivity {

	@ViewInject(R.id.txt_title)
	protected TextView txt_title;

	@ViewInject(R.id.lv_content)
	private ListView lvContent;

	@Override
	public void initView() {
		view = View.inflate(context, R.layout.layout_potential_user, null);
		ViewUtils.inject(this, view);
		txt_title.setText("潜在客户");
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
				convertView = View.inflate(context, R.layout.item_layout_potential_user_list, null);
			}
			// 拨打电话
			convertView.findViewById(R.id.bt_dial_phone).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + "13048868272"));
					// 根据意图过滤器参数激活电话拨号功能
					startActivity(intent);
				}
			});
			// 记录通话情况button
			convertView.findViewById(R.id.bt_record_phone).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityAnimationUtils.rightToLeftInAnimation(context, RecordSpeakDescActivity.class);
				}
			});
			// item添加监听事件
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityAnimationUtils.rightToLeftInAnimation(context, UserAccessDescActivity.class);
				}
			});
			return convertView;
		}

	}

}
