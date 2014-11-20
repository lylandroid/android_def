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

public class Step2View extends FrameLayout implements OnClickListener{
	private Context context;
	private LinearLayout contentLlay;
	private int width;
	private Step2ViewListener step2ViewListener;
	private TextView blanceTv;
	public Step2View(Context context, int width,Step2ViewListener step2ViewListener) {
		super(context);
		this.context = context;
		this.width = width;
		this.step2ViewListener=step2ViewListener;
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
		addMarginLeftDiliver();
		addContentLine2();
		addDiliver();
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
		line.setBackgroundColor(Color.WHITE);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);

		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv.setText("商品: ");
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		TextView titleTv2 = new TextView(context);
		titleTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv2.setText("道具砖石卡");
		line.addView(titleTv2, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		setLeftMargin(titleTv2);
	}

	private void addContentLine2() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setBackgroundColor(Color.WHITE);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);

		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv.setText("金额: ");
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		TextView titleTv2 = new TextView(context);
		titleTv2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv2.setText("10.00元");
		titleTv2.setTextColor(Color.argb(255, 77, 77, 77));
		line.addView(titleTv2, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		TextView titleTv3 = new TextView(context);
		titleTv3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		titleTv3.setText("信用");
		titleTv3.setPadding(3, 3, 3, 3);
		titleTv3.setBackgroundColor(ColorUtil.getThemeColor());
		titleTv3.setTextColor(Color.WHITE);
		line.addView(titleTv3, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		setLeftMargin(titleTv2);
		setLeftMargin(titleTv3);
	}

	private void addContentLine3() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setBackgroundColor(Color.WHITE);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);

		TextView tv = new TextView(context);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		tv.setText("您的信用额度不足，当前可以用额度");
		line.addView(tv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		blanceTv = new TextView(context);
		blanceTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		blanceTv.setText("1000元");
		blanceTv.setTextColor(ColorUtil.getThemeColor());
		line.addView(blanceTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));


		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	private void addContentLine4() {
		int padding = (int) (width * 0.03f);
		LinearLayout line4 = new LinearLayout(context);
		line4.setOrientation(LinearLayout.VERTICAL);
		line4.setPadding(padding, padding, padding, padding);

		MyButton button = new MyButton(context);
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		button.setPadding(padding);
		button.setText("立即充值");

		line4.addView(button, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line4, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		button.setOnClickListener(this);
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
	
	public void setBlance(int blance){
		blanceTv.setText(blance+"元");
	}

	private void setLeftMargin(View v) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v
				.getLayoutParams();
		lp.leftMargin = DimenUtil.dip2px(getContext(), 3);
		v.setLayoutParams(lp);
	}
	
	interface Step2ViewListener{
		public void clickRechargeNow(Step2View step2View);
	}

	@Override
	public void onClick(View arg0) {
		step2ViewListener.clickRechargeNow(this);
	}
}
