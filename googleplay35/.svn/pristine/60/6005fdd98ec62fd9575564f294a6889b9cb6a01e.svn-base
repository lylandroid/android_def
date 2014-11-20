package com.mwqi.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.example.googleplay35.R;
import com.example.googleplay35.manager.ThreadManager;
import com.example.googleplay35.utils.UIUtils;

public abstract class LoadingPage extends FrameLayout {
	private static final int STATE_UNLOADED = 0;//未知状态
	private static final int STATE_LOADING = 1;//加载状态
	private static final int STATE_ERROR = 3;//加载完毕，但是出错状态
	private static final int STATE_EMPTY = 4;//加载完毕，但是没有数据状态
	private static final int STATE_SUCCEED = 5;//加载成功

	private View mLoadingView;//加载时显示的View
	private View mErrorView;//加载出错显示的View
	private View mEmptyView;//加载没有数据显示的View
	private View mSucceedView;//加载成功显示的View

	private int mState;

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	private void init() {
		setBackgroundColor(UIUtils.getColor(R.color.bg_page));//设置背景
		mState = STATE_UNLOADED;//初始化状态

		mLoadingView = createLoadingView();
		if (null != mLoadingView) {
			addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		mErrorView = createErrorView();
		if (null != mErrorView) {
			addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		mEmptyView = createEmptyView();
		if (null != mEmptyView) {
			addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		//显示对应的View
		showPageSafe();
	}

	private void showPageSafe() {
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				showPage();
			}
		});
	}

	private void showPage() {
		if (null != mLoadingView) {
			mLoadingView.setVisibility(mState == STATE_UNLOADED || mState == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (null != mErrorView) {
			mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
		}
		if (null != mEmptyView) {
			mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
		}

		if (mState == STATE_SUCCEED && mSucceedView == null) {
			mSucceedView = createLoadedView();
			addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		if (null != mSucceedView) {
			mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE : View.INVISIBLE);
		}
	}

	public void reset() {
		mState = STATE_UNLOADED;
		showPageSafe();
	}

	protected boolean needReset() {
		return mState == STATE_ERROR || mState == STATE_EMPTY;
	}

	public synchronized void show() {
		if (needReset()) {
			mState = STATE_UNLOADED;
		}
		if (mState == STATE_UNLOADED) {
			mState = STATE_LOADING;
			LoadingTask task = new LoadingTask();
			ThreadManager.getLongPool().execute(task);
		}
		showPageSafe();
	}

	protected View createLoadingView() {
		return UIUtils.inflate(R.layout.loading_page_loading);
	}

	protected View createEmptyView() {
		return UIUtils.inflate(R.layout.loading_page_empty);
	}

	protected View createErrorView() {
		View view = UIUtils.inflate(R.layout.loading_page_error);
		view.findViewById(R.id.page_bt).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	public abstract View createLoadedView();

	public abstract LoadResult loaded();

	class LoadingTask implements Runnable {
		@Override
		public void run() {
			final LoadResult loadResult = loaded();
		    UIUtils.runInMainThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mState = loadResult.getValue();
					showPage();
				}
			});
		}
	}

	public enum LoadResult {
		ERROR(3), EMPTY(4), SUCCEED(5);
		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
