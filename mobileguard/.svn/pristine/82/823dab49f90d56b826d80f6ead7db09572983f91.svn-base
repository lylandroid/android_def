package com.itheima.mobileguard.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.db.dao.BlackNumberDao;
import com.itheima.mobileguard.domain.BlackNumberInfo;

public class CallSmsSafeActivity extends Activity {
	private ListView lv_callsms_safe;
	private LinearLayout ll_add_number_tips;
	private LinearLayout ll_loading;

	private BlackNumberDao dao;
	private List<BlackNumberInfo> infos; // 代表就是当前界面的集合。

	private CallSmsSafeAdapter adapter;

	/**
	 * 开始获取数据的位置
	 */
	private int startIndex = 0;

	/**
	 * 一次最多获取几条数据
	 */
	private int maxCount = 20;

	private int totalCount = 0;

	/**
	 * 消息处理器
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ll_loading.setVisibility(View.INVISIBLE);
			if (infos.size() == 0) {
				// 没有数据，设置添加数据的提醒
				ll_add_number_tips.setVisibility(View.VISIBLE);
			} else {
				if (adapter == null) {
					adapter = new CallSmsSafeAdapter();
					lv_callsms_safe.setAdapter(adapter);
				} else {// 数据适配器是已经存在的。
						// 因为数据适配器里面的数据 已经变化。刷新界面。
					adapter.notifyDataSetChanged();
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
		fillData();
	}

	/**
	 * 填充数据
	 */
	private void fillData() {
		dao = new BlackNumberDao(this);
		totalCount = dao.getTotalNumber();
		// 数据库的总条目个数 / 每个页面最多显示多少条数据
		// 耗时的操作 逻辑应该放在子线程里面执行。
		ll_loading.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				if (infos == null) {
					infos = dao.findPart2(startIndex, maxCount);
				} else {
					// 集合里面原来有数据,新的数据应该放在旧的集合的后面。
					infos.addAll(dao.findPart2(startIndex, maxCount));
				}
				handler.sendEmptyMessage(0);
			};
		}.start();
	}

	/**
	 * 初始化ui的逻辑块
	 */
	private void initUI() {
		setContentView(R.layout.activity_callsms_safe);
		ll_add_number_tips = (LinearLayout) findViewById(R.id.ll_add_number_tips);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		lv_callsms_safe = (ListView) findViewById(R.id.lv_callsms_safe);

		lv_callsms_safe.setOnScrollListener(new OnScrollListener() {
			// 滚动状态发生变化调用的方法。
			// OnScrollListener.SCROLL_STATE_FLING 惯性滑动
			// OnScrollListener.SCROLL_STATE_TOUCH_SCROLL 触摸滑动
			// OnScrollListener.SCROLL_STATE_IDLE 静止
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE: // 静止状态
					// 判断是否是最后一个条目。
					int lastPosition = lv_callsms_safe.getLastVisiblePosition();
					System.out.println("最后一个可见条目的位置：" + lastPosition);
					if (lastPosition == infos.size() - 1) { // //20条数据
						// 加载更多的数据。 更改加载数据的开始位置
						startIndex += maxCount;
						if (startIndex >= totalCount) {
							Toast.makeText(getApplicationContext(),
									"没有更多的数据了。", 0).show();
							return;
						}
						fillData();
					}
					break;
				}
			}

			// 只要listview发生滚动 就会调用下面的方法
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});

	}

	private class CallSmsSafeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infos.size();
		}

		// 这个方法要被执行很多次， 有多个条目 就要执行多少次
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(CallSmsSafeActivity.this,
						R.layout.item_callsms, null);
				holder = new ViewHolder(); // 减少子孩子查询的次数
				holder.tv_phone = (TextView) view
						.findViewById(R.id.tv_item_phone);
				holder.tv_mode = (TextView) view
						.findViewById(R.id.tv_item_mode);
				holder.iv_delete = (ImageView) view
						.findViewById(R.id.iv_delete);
				// 把孩子id的引用 存放在holder里面，设置给父亲 view
				view.setTag(holder);
			} else {
				view = convertView; // 使用历史缓存view对象， 减少view对象被创建的次数
				holder = (ViewHolder) view.getTag();
			}

			final BlackNumberInfo info = infos.get(position);
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String number = info.getNumber();
					// 从数据库删除黑名单号码
					boolean result = dao.delete(number);
					if (result) {
						Toast.makeText(getApplicationContext(), "删除成功", 0)
								.show();
						// 从界面ui里面删除信息
						infos.remove(info);
						// 通知界面刷新
						adapter.notifyDataSetChanged();
					} else {
						Toast.makeText(getApplicationContext(), "删除失败", 0)
								.show();
					}
				}
			});
			holder.tv_phone.setText(info.getNumber());
			// 1 全部拦截 2 短信拦截 3 电话拦截
			String mode = info.getMode();
			if ("1".equals(mode)) {
				holder.tv_mode.setText("全部拦截");
			} else if ("2".equals(mode)) {
				holder.tv_mode.setText("短信拦截 ");
			} else if ("3".equals(mode)) {
				holder.tv_mode.setText("电话拦截 ");
			}
			return view;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	/**
	 * 家庭组 view对象的容器
	 * 
	 * @author Administrator
	 * 
	 */
	class ViewHolder {
		TextView tv_phone;
		TextView tv_mode;
		ImageView iv_delete;
	}

	/**
	 * 添加黑名单号码
	 * 
	 * @param view
	 */
	public void addBlackNumber(View view) {
		AlertDialog.Builder builder = new Builder(this);
		View dialogView = View.inflate(this, R.layout.dialog_add_blacknumber,
				null);
		final AlertDialog dialog = builder.create();
		final EditText et_black_number = (EditText) dialogView
				.findViewById(R.id.et_black_number);
		final CheckBox cb_phone = (CheckBox) dialogView
				.findViewById(R.id.cb_phone);
		final CheckBox cb_sms = (CheckBox) dialogView.findViewById(R.id.cb_sms);
		dialogView.findViewById(R.id.bt_cancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		dialogView.findViewById(R.id.bt_ok).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						String blackNumber = et_black_number.getText()
								.toString().trim();
						if (TextUtils.isEmpty(blackNumber)) {
							Toast.makeText(getApplicationContext(), "号码不能为空", 1)
									.show();
							return;
						}
						String mode = "0";
						// 1 全部拦截 2 短信拦截 3 电话拦截
						if (cb_phone.isChecked() && cb_sms.isChecked()) {
							mode = "1";
						} else if (cb_phone.isChecked()) {
							mode = "3";
						} else if (cb_sms.isChecked()) {
							mode = "2";
						} else {
							Toast.makeText(getApplicationContext(), "请选择拦截模式",
									1).show();
							return;
						}
						// 把数据添加到数据库
						boolean result = dao.add(blackNumber, mode);
						// 刷新界面。 把数据加入到infos集合里面。
						if (result) {
							BlackNumberInfo info = new BlackNumberInfo();
							info.setMode(mode);
							info.setNumber(blackNumber);
							infos.add(0, info);// 界面的数据集合发生了变化。
							// 通知界面刷新。
							if (adapter != null) {
								adapter.notifyDataSetChanged();
							} else {
								adapter = new CallSmsSafeAdapter();
								lv_callsms_safe.setAdapter(adapter);
							}
						}

						dialog.dismiss();
					}
				});

		dialog.setView(dialogView, 0, 0, 0, 0);
		dialog.show();
	}

}
