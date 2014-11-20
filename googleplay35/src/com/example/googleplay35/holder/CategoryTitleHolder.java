package com.example.googleplay35.holder;

import android.view.View;
import android.widget.TextView;

import com.example.googleplay35.R;
import com.example.googleplay35.bean.CategoryInfo;
import com.example.googleplay35.utils.UIUtils;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

	private TextView mTextView;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.category_item_title);
		mTextView = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	public void refreshView() {
		mTextView.setText(getData().getTitle());
	}

}
