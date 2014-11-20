package com.itheima.mobileguard.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.db.dao.ApplockDao;
import com.itheima.mobileguard.domain.AppInfo;
import com.itheima.mobileguard.engine.AppInfoParser;

public class UnlockFragment extends Fragment {
	private TextView tv_status;
	private ListView lv_unlock;
	/**
	 * 未加锁的程序集合
	 */
	private List<AppInfo> unlockappInfos;
	private UnlockAdapter adapter;
	private ApplockDao dao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_unlock, null);
		tv_status = (TextView) view.findViewById(R.id.tv_status);
		lv_unlock = (ListView) view.findViewById(R.id.lv_unlock);
		return view;
	}

	@Override
	public void onStart() {
		System.out.println("onstart");
		dao = new ApplockDao(getActivity());
		//过滤掉已经锁定的应用程序
		unlockappInfos = new ArrayList<AppInfo>();
		List<AppInfo> appInfos = AppInfoParser.getAppInfos(getActivity());
		for(AppInfo info: appInfos){
			if(dao.find(info.getPackname())){
				//已经锁定。
			}else{
				unlockappInfos.add(info);
			}
		}
		adapter = new UnlockAdapter();
		lv_unlock.setAdapter(adapter);
		super.onStart();
	}

	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
		ImageView iv_lock;
	}

	private class UnlockAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			tv_status.setText("未加锁("+unlockappInfos.size()+")个");
			return unlockappInfos.size();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final View view;
			ViewHolder holder;
			if (convertView != null && convertView instanceof LinearLayout) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getActivity(), R.layout.item_unlock, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) view
						.findViewById(R.id.iv_app_icon);
				holder.iv_lock = (ImageView) view
						.findViewById(R.id.iv_app_lock);
				holder.tv_name = (TextView) view.findViewById(R.id.tv_app_name);
				view.setTag(holder);
				
			}
			AppInfo info = unlockappInfos.get(position);
			holder.iv_icon.setImageDrawable(info.getIcon());
			holder.tv_name.setText(unlockappInfos.get(position).getName());
			//点击图标加锁应用程序
			holder.iv_lock.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//播放一个动画效果
					TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1.0f,
							Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
					ta.setDuration(300);
					view.startAnimation(ta);
					new Thread(){
						public void run() {
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									//程序锁信息被加入到数据库了
									dao.add(unlockappInfos.get(position).getPackname());
									unlockappInfos.remove(position);
									adapter.notifyDataSetChanged();//通知界面更新
								}
							});
						};
					}.start();
				}
			});
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
}
