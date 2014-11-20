package com.creditpay.ui;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class ProgressView extends FrameLayout {

	public ProgressView(Context context, int width) {
		super(context);
		// 设置圆形背景
		FrameLayout frameLayout=new FrameLayout(context);
		setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		float[] outerR = new float[] { 3, 3, 3, 3, 3, 3, 3, 3 };
		ShapeDrawable sd = new ShapeDrawable(new RoundRectShape(outerR, null,
				null));
		sd.getPaint().setColor(0xff000000);
		frameLayout.setBackground(sd);
		ProgressBar progressBar = new ProgressBar(context);
		int padding = (int) (width * 0.04f);
		frameLayout.setPadding(padding, padding, padding, padding);
		LayoutParams lp = new LayoutParams(DimenUtil.dip2px(context, 70),
				DimenUtil.dip2px(context, 70));
		lp.gravity = Gravity.CENTER;
//		lp.topMargin = padding;
//		lp.leftMargin = padding;
//		lp.rightMargin = padding;
//		lp.bottomMargin = padding;
		frameLayout.addView(progressBar, lp);
		addView(frameLayout);
	}

}
