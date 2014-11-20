package com.example.googleplay35.holder;

import android.view.View;

public abstract class BaseHolder<T> {
    public View mRootView;
    public T mData;
	public BaseHolder() {
		mRootView = initView();     
		mRootView.setTag(this);
	}
	public void setData(T mData){
		this.mData = mData;
		refreshView();
	}
	public T getData(){
		return mData;
	}
	public View getRootView(){
		return mRootView;
	}
	
	public abstract View initView();
	public abstract void refreshView() ;
	
	
	
}
