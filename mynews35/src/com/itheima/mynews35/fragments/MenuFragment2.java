package com.itheima.mynews35.fragments;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.R;
import com.itheima.mynews35.base.BaseFragment;
import com.itheima.mynews35.base.MyBaseAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MenuFragment2 extends BaseFragment {
	// private View menu;

	private List<String> menuDatas;

	@ViewInject(R.id.lv_menu_news_center)
	private ListView lv_menu_news_center;

	private int mCurrentIndex;

	private MenuAdapter menuAdapter;

	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.layout_left_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		menuDatas = new ArrayList<String>();

	}

	public void initMenu(List<String> menuDatas) {
		this.menuDatas.clear();
		this.menuDatas.addAll(menuDatas);
		menuAdapter = new MenuAdapter();
		lv_menu_news_center.setAdapter(menuAdapter);
		//给菜单item添加点击事件
		lv_menu_news_center.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("onItemClick");
				mCurrentIndex = position;
				menuAdapter.notifyDataSetChanged();
				slidingMenu.toggle();
				//获取新闻内容页面
				((MainActivity)context).getHomeFragment().getNewsCenterPager().showNewsItemIndex(position);
			}
		});
	}

	class MenuAdapter extends MyBaseAdapter<String, ListView> {

		private ImageView iv_menu_item;
		private TextView tv_menu_item;

		public MenuAdapter() {
			super(MenuFragment2.this.context, menuDatas);
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.layout_item_menu,
						null);
			}
			// 文本
			tv_menu_item = (TextView) convertView
					.findViewById(R.id.tv_menu_item);
			tv_menu_item.setText(menuDatas.get(position));
			// 图片
			iv_menu_item = (ImageView) convertView
					.findViewById(R.id.iv_menu_item);

			// 设置菜单没有点击的字体颜色
			tv_menu_item.setTextColor(context.getResources().getColor(
					R.color.white));
			// 设置菜单没有点击项的图片
			iv_menu_item.setImageResource(R.drawable.menu_arr_normal);
			convertView.setBackground(null);

			if (mCurrentIndex == position) {
				// 设置菜单没有点击的字体颜色
				tv_menu_item.setTextColor(context.getResources().getColor(
						R.color.red));
				// 设置菜单没有点击项的图片
				iv_menu_item.setImageResource(R.drawable.menu_arr_select);
				convertView.setBackgroundResource(R.drawable.menu_item_bg_select);
			}

			return convertView;
		}

	}


}
