package com.wi360.mobile.wallet.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.BitmapUtils;
import com.wi360.mobile.wallet.base.MyBaseAdapter;
import com.wi360.mobile.wallet.bean.NewsItemCategoryBean.News;

public class NewsAdapter extends MyBaseAdapter<News, ListView> {

	private BitmapUtils bitmapUtils;

	public NewsAdapter(Context ct, List<News> news) {
		super(ct, news);
		bitmapUtils = new BitmapUtils(ct);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// if(convertView == null){
		// convertView = View.inflate(context, R.layout.layout_news_item, null);
		// }
		//
		// TextView tv_title = (TextView)
		// convertView.findViewById(R.id.tv_title);
		//
		//
		// if(lists.get(position).is_read){
		// tv_title.setTextColor(context.getResources().getColor(R.color.news_item_has_read_textcolor));
		// }else{
		// tv_title.setTextColor(context.getResources().getColor(R.color.news_item_no_read_textcolor));
		// }
		// tv_title.setText(lists.get(position).title);
		//
		// TextView tv_pub_date = (TextView)
		// convertView.findViewById(R.id.tv_pub_date);
		// tv_pub_date.setText(lists.get(position).pubdate);
		//
		//
		// ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
		// bitmapUtils.display(iv_img, lists.get(position).listimage);
		return convertView;
	}

}
