package com.creditpay.ui;

import com.creditpay.domain.OrderInfo;

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

public class Step3View extends FrameLayout implements OnClickListener{
	private Context context;
	private LinearLayout contentLlay;
	private int width;
	private Step3ViewListener step3ViewListener;
	private TextView goodsNameTv,moneyTv,ordreNumTv,statusTv;
	public Step3View(Context context, int width,Step3ViewListener step3ViewListener) {
		super(context);
		this.context = context;
		this.width = width;
		this.step3ViewListener=step3ViewListener;
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
		addMarginLeftDiliver();
		addContentLine3();
		addMarginLeftDiliver();
		addContentLine4();
		addDiliver();
		addContentLine5();
	}
	
	public void showData(OrderInfo orderInfo){
		goodsNameTv.setText(orderInfo.getGoodsName());
		moneyTv.setText(orderInfo.getSum()+"元");
		ordreNumTv.setText(orderInfo.getOrderId());
		statusTv.setText(orderInfo.getStatus());
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

		goodsNameTv = new TextView(context);
		goodsNameTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		goodsNameTv.setText("");
		line.addView(goodsNameTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		setLeftMargin(goodsNameTv);
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

		moneyTv = new TextView(context);
		moneyTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		moneyTv.setText("0元");
		moneyTv.setTextColor(Color.argb(255, 77, 77, 77));
		line.addView(moneyTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		setLeftMargin(moneyTv);
	}

	private void addContentLine3() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setBackgroundColor(Color.WHITE);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);
		
		TextView tv = new TextView(context);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		tv.setText("订单号:");
		line.addView(tv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		ordreNumTv = new TextView(context);
		ordreNumTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		ordreNumTv.setText("");
		ordreNumTv.setTextColor(ColorUtil.getThemeColor());
		line.addView(ordreNumTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));


		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}
	
	private void addContentLine4() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setBackgroundColor(Color.WHITE);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);
		
		TextView tv = new TextView(context);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		tv.setText("状态:");
		line.addView(tv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		statusTv = new TextView(context);
		statusTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		statusTv.setText("");
		statusTv.setTextColor(Color.argb(255, 44, 200, 44));
		line.addView(statusTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));


		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
	}

	private void addContentLine5() {
		int padding = (int) (width * 0.03f);
		LinearLayout line4 = new LinearLayout(context);
		line4.setOrientation(LinearLayout.VERTICAL);
		line4.setPadding(padding, padding, padding, padding);

		MyButton button = new MyButton(context);
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		button.setPadding(padding);
		button.setText("确认");

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
	

	private void setLeftMargin(View v) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v
				.getLayoutParams();
		lp.leftMargin = DimenUtil.dip2px(getContext(), 3);
		v.setLayoutParams(lp);
	}
	
	interface Step3ViewListener{
		public void clickFinish(Step3View step3View);
	}

	@Override
	public void onClick(View arg0) {
		step3ViewListener.clickFinish(this);
	}
}
