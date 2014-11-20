package com.creditpay.ui;

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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Step6View extends FrameLayout implements OnClickListener {
	private Context context;
	private LinearLayout contentLlay;
	private int width;
	private Step6ViewListener step6ViewListener;

	public Step6View(Context context, int width,
			Step6ViewListener step6ViewListener) {
		super(context);
		this.context = context;
		this.width = width;
		this.step6ViewListener = step6ViewListener;
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
		titleTv.setText("我们将发送1条短信(运营商收费0.1元)，系统将弹出是否允许应用发送短信的界面。请选择“允许”，注意如果选择“禁止”，该游戏后续无法采用短信进行信用支付。");
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	private void addContentLine2() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);

		MyButton button = new MyButton(context);
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		button.setPadding(padding);
		button.setText("确认");
		
		MyButton button2 = new MyButton(context);
		button2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		button2.setPadding(padding);
		button2.setText("返回");

		LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.weight=1;
		line.addView(button, lp);
		lp.leftMargin=padding;
		line.addView(button2, lp);

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		button.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	private void addDiliver() {
		View diliver = new View(context);
		diliver.setBackgroundColor(0xffe6e6e6);
		contentLlay.addView(diliver, new LinearLayout.LayoutParams(width,
				DimenUtil.dip2px(context, 0.5f)));
	}

	interface Step6ViewListener {
		public void clickSure(Step6View step6View);

		public void clickCancel(Step6View step6View);
	}

	@Override
	public void onClick(View arg0) {
		step6ViewListener.clickSure(this);
	}
}
