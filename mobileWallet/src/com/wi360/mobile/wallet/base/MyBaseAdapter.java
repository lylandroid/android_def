package com.wi360.mobile.wallet.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T, V> extends BaseAdapter {

	public Context context;

	public List<T> lists; 

	public View V;
	

	public MyBaseAdapter(Context context, List<T> lists) {
		super();
		this.context = context;
		this.lists = lists;
	}

	public MyBaseAdapter(Context context, List<T> lists, View v) {
		super();
		this.context = context;
		this.lists = lists;
		this.V = v;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
