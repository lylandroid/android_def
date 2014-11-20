package com.example.googleplay35.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;

import com.example.googleplay35.adapter.MyAdapter;
import com.example.googleplay35.bean.SubjectInfo;
import com.example.googleplay35.holder.BaseHolder;
import com.example.googleplay35.holder.SubjectHolder;
import com.example.googleplay35.protocol.SubjectProtocol;
import com.example.googleplay35.utils.UIUtils;
import com.mwqi.ui.widget.BaseListView;
import com.mwqi.ui.widget.LoadingPage.LoadResult;;

public class SubjectFragment extends BaseFragment {

	private List<SubjectInfo> mDatas;

	@Override
	protected LoadResult load() {
		SubjectProtocol protocol = new SubjectProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	public View loadView() {
		BaseListView listView = new BaseListView(UIUtils.getContext());
		Subjectadapter adapter = new Subjectadapter(listView,mDatas);
		listView.setAdapter(adapter);
		return listView;
	}

	public class Subjectadapter extends MyAdapter<SubjectInfo>{

		public Subjectadapter(AbsListView mAbsListView, List<SubjectInfo> mDatas) {
			super(mAbsListView, mDatas);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected BaseHolder getHolder() {
			// TODO Auto-generated method stub
			return new SubjectHolder();
		}

		@Override
		public List onLoadMore() {
			SubjectProtocol protocol = new SubjectProtocol();
			return protocol.load(getData().size());
		}
		
	}
	
}
