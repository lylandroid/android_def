package com.wi360.mobile.wallet.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.wi360.mobile.wallet.R;

/**
 * ============================================================
 * 
 * 版 权 ： 黑马程序员教育集团 版权所有 (c) 2014
 * 
 * 作 者 : 马伟奇
 * 
 * 版 本 ： 1.0
 * 
 * 创建日期 ： 2014-8-29 下午3:28:24
 * 
 * 描 述 ：
 * 
 *            自动滚动的viewpager
 * 修订历史 ：
 * 
 * ============================================================
 **/
/**
 * 1 先把有几个点给传过来 2 把具体需要展示的图片url地址传过来 3 文字描述信息 4 把textview传过来
 */
public class RollViewPager extends ViewPager {

	public RollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	private MyOnTouchListener myOnTouchListener;
	private Context context;
	private List<View> dotsLists;

	public RollViewPager(Context context, List<View> dotLists) {
		super(context);
		this.context = context;
		this.dotsLists = dotLists;

		tastk = new Task();

		bitmapUtils = new BitmapUtils(context);
		// 设置图片的色彩模式
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);

//		RollViewPager.this.setOnTouchListener(new MyOnTouchListener());
		myOnTouchListener = new MyOnTouchListener();
	}

	public class MyOnTouchListener implements OnTouchListener {

		private float touchDownX;
		private long startTimeMillis;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				handler.removeCallbacksAndMessages(null);
				touchDownX = event.getX();
				startTimeMillis = System.currentTimeMillis();
				break;

			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				float TouchUpX = event.getX();
				long duration = System.currentTimeMillis() - startTimeMillis;
				if(touchDownX == TouchUpX && duration < 500){
					
				}
				
				startRoll();
				break;
			}
			return true;
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);

			downX = ev.getX();
			downY = ev.getY();
			break;

		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = ev.getX();
			float moveY = ev.getY();
			if (Math.abs(downX - moveX) > Math.abs(downY - moveY)) {
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent arg0) {
	// switch (arg0.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// downX = arg0.getX();
	// break;
	//
	// case MotionEvent.ACTION_UP:
	//
	// break;
	// case MotionEvent.ACTION_MOVE:
	//
	// float moveX = arg0.getX();
	//
	// if(Math.abs(moveX - downX) >0){
	// return true;
	// }
	//
	//
	//
	// break;
	// }
	// return super.onTouchEvent(arg0);
	// }

	/**
	 * 计算图片占用内存的大小 位图
	 * 
	 * 图片的大小 = 长 * 宽 * 像素的字节数 * 2
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	private class Task implements Runnable {

		@Override
		public void run() {
			mCurrentPosition = (mCurrentPosition + 1) % mImageUrlLists.size();
			handler.obtainMessage().sendToTarget();
		}

	}

	private List<String> mImageUrlLists;

	// 设置图片展示的url的集合
	public void setImageUrlLists(List<String> mImageUrlLists) {
		// TODO Auto-generated method stub
		this.mImageUrlLists = mImageUrlLists;
	}

	private TextView top_news_title;
	private List<String> mTitleLists;

	public void setTitleLists(TextView top_news_title, List<String> mTitleLists) {
		// TODO Auto-generated method stub
		this.top_news_title = top_news_title;

		this.mTitleLists = mTitleLists;

		if (top_news_title != null && mTitleLists != null
				&& mTitleLists.size() > 0) {
			top_news_title.setText(mTitleLists.get(0));
		}

	}

	private boolean had_adapter = false;

	// viewpager跳动
	public void startRoll() {
		if (!had_adapter) {
			had_adapter = true;
			RollViewPager.this
					.setOnPageChangeListener(new MyOnPageChangeListener());
			ViewPagerAdapter pagerAdapter = new ViewPagerAdapter();
			RollViewPager.this.setAdapter(pagerAdapter);
		}

		handler.postDelayed(tastk, 4000);
	}

	private int mCurrentPosition = 0;

	private class MyOnPageChangeListener implements OnPageChangeListener {
		int oldPostion = 0;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mCurrentPosition = position;

			if (top_news_title != null && mTitleLists != null
					&& mTitleLists.size() > 0) {
				top_news_title.setText(mTitleLists.get(position));
			}

			if (dotsLists != null && dotsLists.size() > 0) {
				dotsLists.get(position).setBackgroundResource(
						R.drawable.dot_focus);
				dotsLists.get(oldPostion).setBackgroundResource(
						R.drawable.dot_normal);
			}
			oldPostion = position;
		}

	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = View.inflate(context, R.layout.viewpager_item, null);
			ImageView image = (ImageView) view.findViewById(R.id.image);
			bitmapUtils.display(image, mImageUrlLists.get(position));
			((ViewPager) container).addView(view);
			return view;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImageUrlLists.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			RollViewPager.this.setCurrentItem(mCurrentPosition);
			startRoll();
		}

	};
	private Task tastk;
	private BitmapUtils bitmapUtils;
	private float downX;
	private float downY;

}
