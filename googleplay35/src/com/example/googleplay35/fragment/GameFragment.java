package com.example.googleplay35.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import com.example.googleplay35.adapter.MyAdapter;
import com.example.googleplay35.bean.AppInfo;
import com.example.googleplay35.holder.BaseHolder;
import com.example.googleplay35.holder.GameHolder;
import com.example.googleplay35.protocol.GameProtocol;
import com.example.googleplay35.utils.UIUtils;
import com.mwqi.ui.widget.BaseListView;
import com.mwqi.ui.widget.LoadingPage.LoadResult;;

public class GameFragment extends BaseFragment {

	private List<AppInfo> mDatas;

	@Override
	protected LoadResult load() {
		GameProtocol protocol = new GameProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	public View loadView() {
		BaseListView mListView = new BaseListView(UIUtils.getContext());
        GameAdapter adapter = new GameAdapter(mListView, mDatas);
        mListView.setAdapter(adapter);
		return mListView;
	}
	
	
	public class GameAdapter extends MyAdapter<AppInfo>{

		public GameAdapter(AbsListView mAbsListView, List<AppInfo> mDatas) {
			super(mAbsListView, mDatas);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected BaseHolder getHolder() {
			// TODO Auto-generated method stub
			return new GameHolder();
		}

		@Override
		public List onLoadMore() {
			GameProtocol protocol = new GameProtocol();
			return protocol.load(getData().size());
		}
		
	}
	
	

}
