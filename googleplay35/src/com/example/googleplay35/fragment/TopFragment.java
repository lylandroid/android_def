package com.example.googleplay35.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.googleplay35.utils.UIUtils;
import com.mwqi.ui.widget.LoadingPage.LoadResult;;

public class TopFragment extends BaseFragment {

	@Override
	protected LoadResult load() {
		// TODO Auto-generated method stub
		return LoadResult.SUCCEED;
	}

	@Override
	public View loadView() {
		TextView textView = new TextView(UIUtils.getContext());
		return textView;
	}

}
