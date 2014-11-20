package com.example.googleplay35.holder;

import com.example.googleplay35.R;
import com.example.googleplay35.bean.SubjectInfo;
import com.example.googleplay35.image.ImageLoader;
import com.example.googleplay35.utils.UIUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private ImageView item_icon;
	private TextView item_txt;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.subject_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_txt = (TextView) view.findViewById(R.id.item_txt);
		return view;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		SubjectInfo subjectInfo = getData();
		item_txt.setText(subjectInfo.getDes());
		ImageLoader.load(item_icon, subjectInfo.getUrl());
	}

}
