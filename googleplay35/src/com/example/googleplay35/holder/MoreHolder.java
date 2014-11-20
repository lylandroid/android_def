package com.example.googleplay35.holder;

import com.example.googleplay35.R;
import com.example.googleplay35.adapter.MyAdapter;
import com.example.googleplay35.utils.UIUtils;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MoreHolder extends BaseHolder<Integer> implements OnClickListener {
	public static final int HAS_MORE = 0;
	public static final int NO_MORE = 1;
	public static final int ERROR = 2;

	private RelativeLayout mLoading, mError;
	private MyAdapter mAdapter;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_more_loading);
		mLoading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
		mError = (RelativeLayout) view.findViewById(R.id.rl_more_error);
		mError.setOnClickListener(this);
		return view;
	}
	public MoreHolder(MyAdapter adapter, boolean hasMore) {
		setData(hasMore ? HAS_MORE : NO_MORE);
		mAdapter = adapter;
	}
	@Override
	public void refreshView() {
		Integer data = getData();
		mLoading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
		mError.setVisibility(data == ERROR ? View.VISIBLE : View.GONE);
		
	}

	@Override
	public View getRootView() {
		if(HAS_MORE  == getData()){
			loadMore();
		}
		return super.getRootView();
	}
	
	private void loadMore() {
		mAdapter.loadMore();
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
