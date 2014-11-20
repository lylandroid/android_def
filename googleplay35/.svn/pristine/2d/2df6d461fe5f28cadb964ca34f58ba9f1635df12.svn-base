package com.example.googleplay35.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.googleplay35.R;
import com.example.googleplay35.bean.AppInfo;
import com.example.googleplay35.image.ImageLoader;
import com.example.googleplay35.utils.StringUtils;
import com.example.googleplay35.utils.UIUtils;

public class AppHolder extends BaseHolder<AppInfo> {
	private ImageView item_icon;
	private RatingBar item_rating;
	private TextView item_title;
	private TextView item_size;
	private TextView item_bottom;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item);
		item_icon = (ImageView) view.findViewById(R.id.item_icon);
		item_rating = (RatingBar) view.findViewById(R.id.item_rating);
		item_title = (TextView) view.findViewById(R.id.item_title);
		item_size = (TextView) view.findViewById(R.id.item_size);
		item_bottom = (TextView) view.findViewById(R.id.item_bottom);
		return view;
	}

	@Override
	public void refreshView() {
		AppInfo appInfo = getData();
		item_title.setText(appInfo.getName());
		item_size.setText(StringUtils.formatFileSize(appInfo.getSize()));
		item_bottom.setText(appInfo.getDes());
		item_rating.setRating(appInfo.getStars());
		ImageLoader.load(item_icon, appInfo.getIconUrl());
		
	}

}
