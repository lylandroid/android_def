package com.wi360.sms.marketing.base;

import java.util.List;

import com.wi360.sms.marketing.utils.ActivityAnimationUtils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T, V> extends BaseAdapter {

	public Activity context;

	public List<T> lists; 

	public View V;
	

	public MyBaseAdapter(Activity context, List<T> lists) {
		super();
		this.context = context;
		this.lists = lists;
	}

	public MyBaseAdapter(Activity context, List<T> lists, View v) {
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
