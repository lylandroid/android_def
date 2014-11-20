package com.creditpay.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class Step4View extends FrameLayout implements OnClickListener {
	private Context context;
	private LinearLayout contentLlay;
	private int width;
	private EditText phoneEdt,codeEdt;
	private Step4ViewListener step4ViewListener;
	private FrameLayout progressView;
	public Step4View(Context context, int width,Step4ViewListener step4ViewListener) {
		super(context);
		this.context = context;
		this.width = width;
		this.step4ViewListener=step4ViewListener;
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
		addProgressView();
	}
	
	private void addProgressView(){
		progressView=new FrameLayout(context);
		setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		float[] outerR = new float[] { 3, 3, 3, 3, 3, 3, 3, 3 };
		ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(outerR, null,
				null));
		sd.getPaint().setColor(0xff000000);
		progressView.setBackground(sd);
		ProgressBar progressBar = new ProgressBar(context);
		int padding = (int) (width * 0.04f);
		progressView.setPadding(padding, padding, padding, padding);
		LayoutParams lp = new LayoutParams(DimenUtil.dip2px(context, 70),
				DimenUtil.dip2px(context, 70));
		lp.gravity = Gravity.CENTER;
		progressView.addView(progressBar, lp);
		addView(progressView);
		lp=(LayoutParams) progressView.getLayoutParams();
		lp.gravity=Gravity.CENTER;
		lp.width=padding+DimenUtil.dip2px(context, 70);
		lp.height=lp.width;
		progressView.setLayoutParams(lp);
		progressView.setVisibility(View.GONE);
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
		titleTv.setText("    +86");
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		phoneEdt= new EditText(context);
		phoneEdt.setInputType(InputType.TYPE_CLASS_PHONE);
		phoneEdt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		phoneEdt.setHint("请填写手机号码");
		line.addView(phoneEdt, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		setLeftMargin(phoneEdt);
	}

	private void addContentLine2() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setBackgroundColor(Color.WHITE);
		line.setOrientation(LinearLayout.HORIZONTAL);
		line.setPadding(padding, padding, padding, padding);
		
		TextView titleTv = new TextView(context);
		titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		titleTv.setText("验证码");
		titleTv.setInputType(InputType.TYPE_CLASS_NUMBER);
		line.addView(titleTv, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		codeEdt = new EditText(context);
		codeEdt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		codeEdt.setHint("请填写验证码");
		line.addView(codeEdt, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)codeEdt.getLayoutParams();
		lp.weight=1;
		lp.width=0;
		codeEdt.setLayoutParams(lp);

		MyButton button = new MyButton(context);
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		button.setText("获取验证码");
		button.setPadding(padding, 0, padding, 0);
		line.addView(button, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				DimenUtil.dip2px(context, 35)));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		button.setTag(1);
		button.setOnClickListener(this);
	}
	
	private void addContentLine3() {
		int padding = (int) (width * 0.03f);
		LinearLayout line = new LinearLayout(context);
		line.setOrientation(LinearLayout.VERTICAL);
		line.setPadding(padding, padding, padding, padding);

		MyButton button = new MyButton(context);
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		button.setPadding(padding);
		button.setText("登录");

		line.addView(button, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		contentLlay.addView(line, new LinearLayout.LayoutParams(width,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		button.setTag(2);
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
	
	interface Step4ViewListener{
		public void clickLogin(Step4View step4View,String phone,String code);
		public void clickGetSmsCode(String phone);
	}

	@Override
	public void onClick(View arg0) {
		int tag=(Integer) arg0.getTag();
		String phone=phoneEdt.getText().toString().trim();
		if(tag==1){
			if(phone.length()!=11){
				Toast.makeText(context, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
				return;
			}
			progressView.setVisibility(View.VISIBLE);
			step4ViewListener.clickGetSmsCode(phone);
		}else if(tag==2){
			String code=codeEdt.getText().toString().trim();
			if(phone.length()!=11){
				Toast.makeText(context, "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
				return;
			}
			if(code.length()!=4){
				Toast.makeText(context, "请输入4位验证码！", Toast.LENGTH_SHORT).show();
				return;
			}
			progressView.setVisibility(View.VISIBLE);
			step4ViewListener.clickLogin(this,phone,code);
		}
		
		
	}
	
	public void hiddenProgressView(){
		progressView.setVisibility(View.GONE);
	}
}
