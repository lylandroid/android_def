package com.itheima.mobileguard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.domain.TaskInfo;
import com.itheima.mobileguard.engine.TaskInfoParser;
import com.itheima.mobileguard.utils.SystemInfoUtils;

public class TaskManagerActivity extends Activity {
	private TextView tv_running_prcesscount;
	private TextView tv_ram_info;
	private ListView lv_taskmanger;
	/**
	 * 所有进程信息的集合
	 */
	private List<TaskInfo> infos;

	private List<TaskInfo> userTaskInfos;
	private List<TaskInfo> systemTaskInfos;
	private LinearLayout ll_loading;
	/**
	 * 正在运行的进程数量
	 */
	private int runningProcessCount;

	/**
	 * 总的可用内存
	 */
	private long totalAvailMem;

	private TaskManagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager);
		lv_taskmanger = (ListView) findViewById(R.id.lv_taskmanger);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		tv_running_prcesscount = (TextView) findViewById(R.id.tv_running_prcesscount);
		tv_ram_info = (TextView) findViewById(R.id.tv_ram_info);
		// 设置内存空间的大小 和 正在运行的进程的数量
		totalAvailMem = SystemInfoUtils.getAvailMem(this);
		long totalMem = SystemInfoUtils.getTotalMem();
		tv_ram_info.setText("可用/总内存："
				+ Formatter.formatFileSize(this, totalAvailMem) + "/"
				+ Formatter.formatFileSize(this, totalMem));
		runningProcessCount = SystemInfoUtils.getRunningPocessCount(this);
		tv_running_prcesscount.setText("运行中进程：" + runningProcessCount + "个");

		fillData();

		lv_taskmanger.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				Object obj = lv_taskmanger.getItemAtPosition(position);
				if (obj != null && obj instanceof TaskInfo) {
					TaskInfo info = (TaskInfo) obj;
					if (info.getPackname().equals(getPackageName())) {
						// 就是我们自己。
						return;
					}
					ViewHolder holder = (ViewHolder) view.getTag();
					if (info.isChecked()) {
						holder.cb_status.setChecked(false);
						info.setChecked(false);
					} else {
						holder.cb_status.setChecked(true);
						info.setChecked(true);
					}
				}
			}
		});

	}

	private void fillData() {
		ll_loading.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				infos = TaskInfoParser
						.getRunningTaskInfos(TaskManagerActivity.this);
				userTaskInfos = new ArrayList<TaskInfo>();
				systemTaskInfos = new ArrayList<TaskInfo>();
				for (TaskInfo info : infos) {
					if (info.isUsertask()) {
						userTaskInfos.add(info);
					} else {
						systemTaskInfos.add(info);
					}
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ll_loading.setVisibility(View.INVISIBLE);
						adapter = new TaskManagerAdapter();
						lv_taskmanger.setAdapter(adapter);
					}
				});
			};
		}.start();

	}

	@Override
	protected void onStart() {
		if (adapter != null) {
			// 通知界面刷新
			adapter.notifyDataSetChanged();
		}
		super.onStart();
	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_size;
		CheckBox cb_status;
	}

	private class TaskManagerAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			boolean showsystem = sp.getBoolean("showsystem", true);
			if (showsystem) {
				return userTaskInfos.size() + systemTaskInfos.size() + 1 + 1;
			} else {
				return userTaskInfos.size() + 1;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final TaskInfo info;
			if (position == 0) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("用户进程：" + userTaskInfos.size() + "个");
				return tv;
			} else if (position == (userTaskInfos.size() + 1)) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("系统进程：" + systemTaskInfos.size() + "个");
				return tv;
			} else if (position <= userTaskInfos.size()) {
				info = userTaskInfos.get(position - 1);
			} else {
				info = systemTaskInfos.get(position - 1 - userTaskInfos.size()
						- 1);
			}
			View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof RelativeLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.item_task_manager, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_task_icon);
				holder.tv_name = (TextView) view
						.findViewById(R.id.tv_task_name);
				holder.tv_size = (TextView) view
						.findViewById(R.id.tv_task_size);
				holder.cb_status = (CheckBox) view
						.findViewById(R.id.cb_task_status);
				view.setTag(holder);
			}
			holder.iv_icon.setImageDrawable(info.getIcon());
			holder.tv_name.setText(info.getAppname());
			holder.tv_size.setText("占用内存："
					+ Formatter.formatFileSize(getApplicationContext(),
							info.getMemsize()));
			holder.cb_status.setChecked(info.isChecked());
			if (info.getPackname().equals(getPackageName())) {
				// 就是我们自己。
				holder.cb_status.setVisibility(View.INVISIBLE);
			} else {
				holder.cb_status.setVisibility(View.VISIBLE);
			}
			return view;
		}

		@Override
		public Object getItem(int position) {
			TaskInfo info;
			if (position == 0) {
				return null;
			} else if (position == (userTaskInfos.size() + 1)) {
				return null;
			} else if (position <= userTaskInfos.size()) {
				info = userTaskInfos.get(position - 1);
			} else {
				info = systemTaskInfos.get(position - 1 - userTaskInfos.size()
						- 1);
			}
			return info;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	/**
	 * 选择全部的item
	 * 
	 * @param view
	 */
	public void selectAll(View view) {
		for (TaskInfo info : userTaskInfos) {
			if (info.getPackname().equals(getPackageName())) {
				continue;
			}
			info.setChecked(true);
		}
		for (TaskInfo info : systemTaskInfos) {
			info.setChecked(true);
		}
		// 通知界面更新
		adapter.notifyDataSetChanged();
	}

	/**
	 * 反选全部的item
	 * 
	 * @param view
	 */
	public void selectOpposite(View view) {
		for (TaskInfo info : userTaskInfos) {
			if (info.getPackname().equals(getPackageName())) {
				continue;
			}
			info.setChecked(!info.isChecked());
		}
		for (TaskInfo info : systemTaskInfos) {
			info.setChecked(!info.isChecked());
		}
		// 通知界面更新
		adapter.notifyDataSetChanged();
	}

	/**
	 * 杀死后台进程
	 * 
	 * @param view
	 */
	public void killProcess(View view) {
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		int count = 0;
		long savemem = 0;
		List<TaskInfo> killedTaskInfos = new ArrayList<TaskInfo>();

		// 在遍历集合的时候 不可以修改集合的大小
		for (TaskInfo info : userTaskInfos) {
			if (info.isChecked()) {
				count++;
				savemem += info.getMemsize();
				am.killBackgroundProcesses(info.getPackname());
				killedTaskInfos.add(info);
			}
		}

		for (TaskInfo info : systemTaskInfos) {
			if (info.isChecked()) {
				count++;
				savemem += info.getMemsize();
				am.killBackgroundProcesses(info.getPackname());
				killedTaskInfos.add(info);
			}
		}
		for (TaskInfo info : killedTaskInfos) {
			if (info.isUsertask()) {
				userTaskInfos.remove(info);
			} else {
				systemTaskInfos.remove(info);
			}
		}
		runningProcessCount -= count;
		totalAvailMem += savemem;
		// 更新标题
		tv_running_prcesscount.setText("运行中进程：" + runningProcessCount + "个");
		tv_ram_info
				.setText("可用/总内存："
						+ Formatter.formatFileSize(this, totalAvailMem)
						+ "/"
						+ Formatter.formatFileSize(this,
								SystemInfoUtils.getTotalMem()));
		Toast.makeText(
				this,
				"杀死了" + count + "个进程,释放了"
						+ Formatter.formatFileSize(this, savemem) + "的内存", 1)
				.show();
		// 刷新界面
		// 老实 fillData();
		adapter.notifyDataSetChanged();
	}

	public void openSetting(View view) {
		Intent intent = new Intent(this, TaskManagerSettingActivity.class);
		startActivity(intent);
	}
}
