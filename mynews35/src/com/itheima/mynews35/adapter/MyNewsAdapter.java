package com.itheima.mynews35.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mynews35.R;
import com.itheima.mynews35.base.MyBaseAdapter;
import com.itheima.mynews35.domain.News.NewsItem;
import com.lidroid.xutils.BitmapUtils;

public class MyNewsAdapter extends MyBaseAdapter<NewsItem, ListView> {

	/**
	 * 加载图片的对象
	 */
	private BitmapUtils bitmapUtils;
	public MyNewsAdapter(Context context,List<NewsItem> newsItems) {
		super(context, newsItems);
		
		
		bitmapUtils = new BitmapUtils(context);
		//让加载的图片减小一半
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
	}
	ViewHolder holdrer = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			holdrer = new ViewHolder();
			convertView = View.inflate(context, R.layout.layout_news_item, null);
			holdrer.iv_iamge = (ImageView) convertView.findViewById(R.id.iv_img);
			holdrer.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			holdrer.tv_pub_date = (TextView) convertView.findViewById(R.id.tv_pub_date);
			holdrer.tv_comment_count = (TextView) convertView.findViewById(R.id.tv_comment_count);
			convertView.setTag(holdrer);
		}else{
			holdrer = (ViewHolder) convertView.getTag();
		}
		
		bitmapUtils.display(holdrer.iv_iamge, lists.get(position).listimage);
		holdrer.tv_title.setText(lists.get(position).title);
		holdrer.tv_pub_date.setText(lists.get(position).pubdate);
		
		if(lists.get(position).is_read){
			holdrer.tv_title.setTextColor(0x66FF0000);
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		public ImageView iv_iamge;
		public TextView tv_title;
		public TextView tv_pub_date;
		public TextView tv_comment_count;
	}

}
