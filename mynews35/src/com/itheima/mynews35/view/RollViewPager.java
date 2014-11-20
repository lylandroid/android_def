package com.itheima.mynews35.view;

import java.util.List;

import com.itheima.mynews35.R;
import com.itheima.mynews35.adapter.MyOnPageChangeListenerAdapter;
import com.itheima.mynews35.utils.CommonUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自动左右滚动的ViewPages
 * 
 * @author Administrator
 * 
 */
public class RollViewPager extends ViewPager {

	private Context context;
	private BitmapUtils bitmapUtils;
	private float downX;
	private float downY;
	/**
	 * 图片在切换过程中记住当前下标
	 */
	private int mCurrentIndex;
	/**
	 * 上一个显示viewPager的图片索引
	 */
	private int preDotIndex;
	/**
	 * 点的父控件
	 */
	private LinearLayout dots_ll;

	/**
	 * 图片描述信息
	 */
	private List<String> titles;

	/**
	 * 图片描述信息的textView
	 */
	private TextView tv_title;

	/**
	 * 自动切换Viewpager内容的Handler
	 */
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mCurrentIndex = (++mCurrentIndex) % dots_ll.getChildCount();
			RollViewPager.this.setCurrentItem(mCurrentIndex);
			handler.sendEmptyMessageDelayed(0, 3000);
		};
		
		ImageView iv;
	};

	public RollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文环境
	 * @param ll_dots
	 *            点的资源Id
	 * @param tv_title
	 *            文字描述的TextView
	 * @param imageUrls
	 *            图片资源的url集合
	 * @param titles
	 *            头的文字描述集合
	 */
	public RollViewPager(Context context, LinearLayout ll_dots,
			TextView tv_title,//
			List<String> imageUrls, List<String> titles) {
		super(context);
		this.context = context;
		dots_ll = ll_dots;
		this.titles = titles;
		this.tv_title = tv_title;

		// 资源部位null才进行相应的处理
		if (imageUrls != null && imageUrls.size() > 0) {

			bitmapUtils = new BitmapUtils(context);
			bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);

			// 添加适配器,为滚动ViewPager加入数据
			RollViewPager.this.setAdapter(new MyPageAdapter(imageUrls));
			// 初始化文字描述信息
			tv_title.setText(titles.get(0));
			
			// 创建点
			initdots(imageUrls.size(), ll_dots);

			// 添加监听
			setOnPageChange();

			// 发送自动切换ViewPager图片的内容
			handler.sendEmptyMessageDelayed(0, 3000);
		}
	}

	/**
	 * 拦截事件,让ViewPager可以左右滑动
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			handler.removeMessages(0);
			downX = ev.getX();
			downY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float offX = downX - ev.getX();
			float offY = downY - ev.getY();

			if (Math.abs(offX) > Math.abs(offY)) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}

			break;
		case MotionEvent.ACTION_UP:
			handler.sendEmptyMessageDelayed(0, 3000);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 滚动ViewPager的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	class MyPageAdapter extends PagerAdapter {
		/**
		 * 需要加载网络图片的url
		 */
		private List<String> imageUrls;
		private View v;

		public MyPageAdapter(List<String> imageUrls) {
			this.imageUrls = imageUrls;
		}

		@Override
		public int getCount() {
			return imageUrls.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			v = View.inflate(context, R.layout.viewpager_item, null);
			// 通过工具吧图片加载的view上
			bitmapUtils.display(v.findViewById(R.id.image),
					imageUrls.get(position));
			// 把该view对象添加的viewPager上
			container.addView(v);
			return v;
		}

	}

	/**
	 * 创建点
	 * 
	 * @param size
	 * @param ll_dots
	 */
	private void initdots(int size, LinearLayout dots_ll) {
		dots_ll.removeAllViews();

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				CommonUtil.dip2px(context, 6),//
				CommonUtil.dip2px(context, 6));
		params.setMargins(5, 0, 5, 0);
		TextView tv = null;
		for (int i = 0; i < size; i++) {
			tv = new TextView(context);
			// tv.setText(titles.get(0));
			tv.setLayoutParams(params);
			tv.setBackgroundResource(R.drawable.dot_normal);
			if (i == 0) {
				tv.setBackgroundResource(R.drawable.dot_focus);
			}
			dots_ll.addView(tv);
		}

	}

	/**
	 * 给ViwePage(就是当前对象)添加setOnPageChangeListener监听
	 */
	private void setOnPageChange() {
		RollViewPager.this.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

				if (dots_ll.getChildCount() > 0) {// 设置点和描述跟随图片切换
					dots_ll.getChildAt(preDotIndex).setBackgroundResource(
							R.drawable.dot_normal);
					dots_ll.getChildAt(position).setBackgroundResource(
							R.drawable.dot_focus);
					tv_title.setText(titles.get(position));

					preDotIndex = position;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int position) {
				//System.out.println("onPageScrollStateChanged: " + position);
			}
		});
		
		
	}

}
