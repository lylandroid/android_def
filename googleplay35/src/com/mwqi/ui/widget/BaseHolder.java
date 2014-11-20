package com.mwqi.ui.widget;

import android.view.View;

public abstract class BaseHolder<T> {
    public View mRootView;
    public T mData;
	public BaseHolder(){
		mRootView = initView();
		mRootView.setTag(this);
	}
	public View getRootView(){
		return mRootView;
	}
	public void setData(T data){
		this.mData = data;
		refreshView();
	}
	public T getData(){
		return mData;
	}
	public abstract void refreshView() ;
	public abstract View initView() ;
}
