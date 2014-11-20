package com.example.googleplay35.fragment;

import java.util.List;

import com.example.googleplay35.adapter.MyAdapter;
import com.example.googleplay35.bean.AppInfo;
import com.example.googleplay35.holder.AppHolder;
import com.example.googleplay35.holder.BaseHolder;
import com.example.googleplay35.protocol.AppProtocol;
import com.example.googleplay35.utils.UIUtils;
import com.mwqi.ui.widget.BaseListView;
import com.mwqi.ui.widget.LoadingPage.LoadResult;

import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;


public class AppFragment extends BaseFragment {

	private List<AppInfo> mDatas;

	@Override
	protected LoadResult load() {
		AppProtocol protocol = new AppProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	public View loadView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		AppAdapter appAdapter = new AppAdapter(listView, mDatas);
		listView.setAdapter(appAdapter);
		return listView;
	}

	public class AppAdapter extends MyAdapter<AppInfo>{

		public AppAdapter(AbsListView mAbsListView, List<AppInfo> mDatas) {
			super(mAbsListView, mDatas);
		}

		@Override
		protected BaseHolder getHolder() {
			return new AppHolder();
		}

		@Override
		public List onLoadMore() {
			AppProtocol protocol = new AppProtocol();
			return protocol.load(getData().size());
		}
		
	}
	
	
	
}
