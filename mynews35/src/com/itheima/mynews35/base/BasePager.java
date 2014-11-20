package com.itheima.mynews35.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public abstract class BasePager {
	protected Context context;
	protected View view;
	/**
	 * 是否加载过数据
	 */
	public boolean is_load;
	protected TextView txt_title;
	
	protected SlidingMenu slidingMenu;

	public BasePager(Context context) {

		this.context = context;
		view = initView();
		
		slidingMenu = ((MainActivity)context).getResultSlidingMenu();

	}
	
	public View getRootView(){
		return view;
	}
	
	public void initTitleBar(){
		Button btn_left = (Button) view.findViewById(R.id.btn_left);
		ImageButton imgbtn_left = (ImageButton) view.findViewById(R.id.imgbtn_left);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		ImageButton btn_right = (ImageButton) view.findViewById(R.id.btn_right);
		
		btn_left.setVisibility(View.GONE);
		imgbtn_left.setImageResource(R.drawable.img_menu);
		btn_right.setVisibility(View.GONE);
	}

	/**
	 * 初始化view,在besePager中被调用
	 * @return
	 */
	public abstract View initView();

	/**
	 * 
	 */
	public abstract void initData();
}
