package com.example.googleplay35.fragment;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.example.googleplay35.adapter.MyAdapter;
import com.example.googleplay35.bean.CategoryInfo;
import com.example.googleplay35.holder.BaseHolder;
import com.example.googleplay35.holder.CategoryHolder;
import com.example.googleplay35.holder.CategoryTitleHolder;
import com.example.googleplay35.protocol.CategoryProtocol;
import com.example.googleplay35.utils.UIUtils;
import com.mwqi.ui.widget.BaseListView;
import com.mwqi.ui.widget.LoadingPage.LoadResult;


public class CategoryFragment extends BaseFragment {

	private List<CategoryInfo> mDatas;
	BaseListView mListView;
	CategoryAdapter mAdapter;
	@Override
	protected LoadResult load() {
		CategoryProtocol protocol = new CategoryProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	public View loadView() {
		mListView = new BaseListView(UIUtils.getContext());
		mAdapter = new CategoryAdapter(mListView, mDatas);
		mListView.setAdapter(mAdapter);
		return mListView;
	}

	public int mCurrentPosition = 0;
	public class CategoryAdapter extends MyAdapter<CategoryInfo>{
		private int mCurrentPosition;

		public CategoryAdapter(AbsListView listView, List<CategoryInfo> datas) {
			super(listView, datas);
		}

		@Override
		public boolean hasMore() {
			return false;
		}

		//是告诉listView总共有几种样式的item
		@Override
		public int getViewTypeCount() {
			return super.getViewTypeCount() + 1;
		}

		//告诉ListView每个位置是哪种样式的item
		@Override
		public int getItemViewTypeInner(int position) {
			CategoryInfo groupInfo = getData().get(position);
			if (groupInfo.isTitle()) {
				return super.getItemViewTypeInner(position) + 1;
			} else {
				return super.getItemViewTypeInner(position);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			mCurrentPosition = position;
			return super.getView(position, convertView, parent);
		}

		public BaseHolder getHolder() {
			CategoryInfo groupInfo = getData().get(mCurrentPosition);
			if (groupInfo.isTitle()) {
				return new CategoryTitleHolder();
			} else {
				return new CategoryHolder();
			}
		}

		@Override
		public List onLoadMore() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	
}
