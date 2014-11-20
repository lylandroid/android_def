package com.example.googleplay35.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googleplay35.R;
import com.example.googleplay35.bean.CategoryInfo;
import com.example.googleplay35.image.ImageLoader;
import com.example.googleplay35.utils.StringUtils;
import com.example.googleplay35.utils.UIUtils;

public class CategoryHolder extends BaseHolder<CategoryInfo> {

	private ImageView iv1, iv2, iv3;
	private TextView tv1, tv2, tv3;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.category_item);
		iv1 = (ImageView) view.findViewById(R.id.iv_1);
		iv2 = (ImageView) view.findViewById(R.id.iv_2);
		iv3 = (ImageView) view.findViewById(R.id.iv_3);

		tv1 = (TextView) view.findViewById(R.id.tv_1);
		tv2 = (TextView) view.findViewById(R.id.tv_2);
		tv3 = (TextView) view.findViewById(R.id.tv_3);
		return view;
	}

	@Override
	public void refreshView() {
		CategoryInfo data = getData();
		String name1 = data.getName1();
		String name2 = data.getName2();
		String name3 = data.getName3();

		String key1 = data.getImageUrl1();
		String key2 = data.getImageUrl2();
		String key3 = data.getImageUrl3();
		
		if (StringUtils.isEmpty(key1)) {
			tv1.setText("");
			iv1.setImageDrawable(null);
		} else {
			tv1.setText(name1);
			iv1.setTag(key1);
			ImageLoader.load(iv1, key1);
		}
		if (StringUtils.isEmpty(key2)) {
			tv2.setText("");
			iv2.setImageDrawable(null);
		} else {
			tv2.setText(name2);
			iv2.setTag(key2);
			ImageLoader.load(iv2, key2);
		}
		if (StringUtils.isEmpty(key3)) {
			tv3.setText("");
			iv3.setImageDrawable(null);
		} else {
			tv3.setText(name3);
			iv3.setTag(key3);
			ImageLoader.load(iv3, key3);
		}
	}

}
