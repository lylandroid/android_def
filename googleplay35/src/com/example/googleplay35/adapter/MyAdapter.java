package com.example.googleplay35.adapter;

import java.util.List;

import com.example.googleplay35.holder.BaseHolder;
import com.example.googleplay35.holder.MoreHolder;
import com.example.googleplay35.manager.ThreadManager;
import com.example.googleplay35.utils.UIUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class MyAdapter<T> extends BaseAdapter {
    private AbsListView mAbsListView;
    private List<T> mDatas;
	public MyAdapter(AbsListView mAbsListView,List<T> mDatas) {
		this.mAbsListView = mAbsListView;
		setDatas(mDatas);
	}

	public void setDatas(List<T> mDatas){
		this.mDatas = mDatas;
	}
	
	public List<T> getData(){
		return mDatas;
	}
	
	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1) {
			return MORE_VIEW_TYPE;// 加载更多的布局
		} else {
			return getItemViewTypeInner(position);
		}
	}
	public int getItemViewTypeInner(int position) {
		// TODO Auto-generated method stub
		return ITEM_VIEW_TYPE;// 普通item的布局
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return super.getViewTypeCount() + 1;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public static final int MORE_VIEW_TYPE = 0;
	public static final int ITEM_VIEW_TYPE = 1;
	private MoreHolder moreHolder;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder<T> holder;
		if (convertView != null&& convertView.getTag() instanceof BaseHolder) {
			holder = (BaseHolder<T>) convertView.getTag();
		} else {
			if (getItemViewType(position) == MORE_VIEW_TYPE) {
				holder = getMoreHolder();
			} else {
				holder = getHolder();
			}
		}
		if (getItemViewType(position) == ITEM_VIEW_TYPE) {
			holder.setData(mDatas.get(position));
		}
		return holder.getRootView();
	}

	private BaseHolder getMoreHolder() {
		if(moreHolder == null){
			moreHolder = new MoreHolder(this, hasMore());
		}
	
		return moreHolder;
	}

	public boolean hasMore() {
		// TODO Auto-generated method stub
		return true;
	}

	protected abstract BaseHolder getHolder();

	public boolean isLoading;
	
	public void loadMore() {
		
		if(!isLoading){
			isLoading = true;
			ThreadManager.getLongPool().execute(new Runnable() {
				
				@Override
				public void run() {
					final List datas = onLoadMore();
					UIUtils.runInMainThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							if(datas == null){
								getMoreHolder().setData(MoreHolder.ERROR);
							}else if(datas.size() < 20){
								getMoreHolder().setData(MoreHolder.NO_MORE);
							}else{
								getMoreHolder().setData(MoreHolder.HAS_MORE);
							}
							
							if(datas != null){
								if(getData() != null){
									getData().addAll(datas);
								}else{
									setDatas(datas);
								}
							}
							
							notifyDataSetChanged();
							isLoading = false;
						}
					});
				}
			});
			
		}
		
		
		
		
	}

	public abstract List onLoadMore();

}
