package com.creditpay.ui;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Step5View extends FrameLayout implements OnClickListener{
	private Context context;
	private LinearLayout contentLlay;
	private int width;
	private Step5ViewListener step5ViewListener;
	public Step5View(Context context, int width,Step5ViewListener step5ViewListener) {
		super(context);
		this.context = context;
		this.width = width;
		this.step5ViewListener=step5ViewListener;
		initContainer();
	}

	private void initContainer() {
		// 设置圆形背景
		float[] outerR = new float[] { 4, 4, 4, 4, 4, 4, 4, 4 };
		ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(outerR, null,
				null));
		sd.getPaint().setColor(0xffeeeeee);
		setBackground(sd);

		ScrollView view = new ScrollView(context);
		view.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
		contentLlay = new LinearLayout(context);
		contentLlay.setOrientation(LinearLayout.VERTICAL);
		view.addView(contentLlay, new ScrollView.LayoutParams(width,
				ScrollView.LayoutParams.WRAP_CONTENT));
		addView(view);
		addTitle();
		addDiliver();
		addContentLine1();
		addDiliver();
		addContentLine2();
		addMarginLeftDiliver();
		addContentLine3();
		addDiliver();
		addContentLine4();
	}

	private void addTitle() {
		int padding = (int) (width * 0.04f);
		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		titleTv.setGravity(Gravity.CENTER);
		titleTv.setPadding(0, padding, 0, padding);
		titleTv.setText("博升信用支付");
		contentLlay.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	private void addContentLine1() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);

		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv.setText("您的设备无法上网，请选择");
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));

	}

	private void addContentLine2() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setGravity(Gravity.CENTER_VERTICAL);
		line.setBackground(MyButton.getListBg());
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);

		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv.setTextColor(ColorUtil.getThemeColor());
		titleTv.setText("联网模式(推荐)");
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)titleTv.getLayoutParams();
		lp.weight=1;
		lp.width=0;
		titleTv.setLayoutParams(lp);

		TextView titleTv2 = new TextView(context);
		titleTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv2.setText(">");
		titleTv2.setTextColor(Color.argb(255, 77, 77, 77));
		line.addView(titleTv2, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				DimenUtil.dip2px(context, 48)));

		setLeftMargin(titleTv2);
		line.setTag(1);
		line.setOnClickListener(this);
	}

	private void addContentLine3() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setGravity(Gravity.CENTER_VERTICAL);
		line.setBackground(MyButton.getListBg());
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);
		
		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv.setText("短信模式");
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)titleTv.getLayoutParams();
		lp.weight=1;
		lp.width=0;
		titleTv.setLayoutParams(lp);

		TextView titleTv2 = new TextView(context);
		titleTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv2.setText(">");
		titleTv2.setTextColor(Color.argb(255, 77, 77, 77));
		line.addView(titleTv2, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				DimenUtil.dip2px(context, 48)));

		setLeftMargin(titleTv2);
		line.setTag(2);
		line.setOnClickListener(this);
	}
	
	private void addContentLine4() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setOrientation(LinearLayout.VERTICAL);
		line.setPadding(padding, padding, padding, padding);

		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv.setText("联网模式：我们需要您打开网络设备，将***1个月使用流量为10K\n短信模式：使用短信方式的信用支付");

		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	private void addDiliver() {
		View diliver = new View(context);
		diliver.setBackgroundColor(0xffe6e6e6);
		contentLlay.addView(diliver, new LinearLayout.LayoutParams(width,
				DimenUtil.dip2px(context, 0.5f)));
	}
	
	private void addMarginLeftDiliver() {
		LinearLayout diliver = new LinearLayout(context);
		diliver.setBackgroundColor(Color.WHITE);
		
		View view = new View(context);
		view.setBackgroundColor(0xffe6e6e6);
		
		diliver.addView(view, new LinearLayout.LayoutParams(width,
				DimenUtil.dip2px(context, 0.5f)));
		
		contentLlay.addView(diliver, new LinearLayout.LayoutParams(width,
				DimenUtil.dip2px(context, 0.5f)));
		
		int padding = (int) (width * 0.03f);
		LinearLayout.LayoutParams llp=(LinearLayout.LayoutParams) view.getLayoutParams();
		llp.leftMargin=padding;
		view.setLayoutParams(llp);
	}
	

	private void setLeftMargin(View v) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v
				.getLayoutParams();
		lp.leftMargin = DimenUtil.dip2px(getContext(), 3);
		v.setLayoutParams(lp);
	}
	
	interface Step5ViewListener{
		public void clickNetWorkModel(Step5View step5View);
		public void clickSmsModel(Step5View step5View);
	}

	@Override
	public void onClick(View arg0) {
		int tag=(Integer) arg0.getTag();
		if(tag==1){
			step5ViewListener.clickNetWorkModel(this);
		}else if(tag==2){
			step5ViewListener.clickSmsModel(this);
		}
		
	}
}
