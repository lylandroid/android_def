package com.mwqi.ui.widget;

import com.example.googleplay35.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class BaseListView extends ListView {

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initListView();
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initListView();
	}



	public BaseListView(Context context) {
		super(context);
		initListView();
	}
	
	public void initListView() {
		setSelector(R.drawable.nothing);
	}

}
