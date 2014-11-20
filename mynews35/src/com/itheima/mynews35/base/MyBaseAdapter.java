package com.itheima.mynews35.base;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class MyBaseAdapter<T, Q> extends android.widget.BaseAdapter {

	protected Context context;
	protected List<T> lists;
	protected View Q;

	public MyBaseAdapter(Context context, List<T> lists) {
		this.context = context;
		this.lists = lists;
	}

	public MyBaseAdapter(Context context, List<T> lists, View q) {
		this.context = context;
		this.lists = lists;
		this.Q = q;
	}
//
//	public MyBaseAdapter() {
//		super();
//	}

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

	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
