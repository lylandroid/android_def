package com.creditpay.ui;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.View;
import android.widget.Button;

public class MyButton extends Button {

	public MyButton(Context context) {
		super(context);
		setBackground(setbg());
		setTextColor(Color.WHITE);
	}
	// 以下这个方法也可以把你的图片数组传过来，以StateListDrawable来设置图片状态，来表现button的各中状态。未选
    // 中，按下，选中效果。
    public StateListDrawable setbg() {
    	float[] outerR = new float[] { 4, 4, 4, 4, 4, 4, 4, 4 };
		ShapeDrawable normal = new ShapeDrawable(new RoundRectShape(outerR, null,
				null));
		normal.getPaint().setColor(ColorUtil.getThemeColor());
		ShapeDrawable pressed = new ShapeDrawable(new RoundRectShape(outerR, null,
				null));
		pressed.getPaint().setColor(ColorUtil.pressolor());
		StateListDrawable bg = new StateListDrawable();
        bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
        bg.addState(View.ENABLED_FOCUSED_STATE_SET, pressed);
        bg.addState(View.ENABLED_STATE_SET, normal);
        bg.addState(View.FOCUSED_STATE_SET, pressed);
        bg.addState(View.EMPTY_STATE_SET, normal);
//        StateListDrawable bg = new StateListDrawable();
//        Drawable normal = this.getResources().getDrawable(mImageIds[0]);
//        Drawable selected = this.getResources().getDrawable(mImageIds[1]);
//        Drawable pressed = this.getResources().getDrawable(mImageIds[2]);
//        bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
//        bg.addState(View.ENABLED_FOCUSED_STATE_SET, selected);
//        bg.addState(View.ENABLED_STATE_SET, normal);
//        bg.addState(View.FOCUSED_STATE_SET, selected);
//        bg.addState(View.EMPTY_STATE_SET, normal);
        return bg;
    }
    
    public void setPadding(int padding){
    	setPadding(padding, padding, padding, padding);
    }
    
    public static StateListDrawable getListBg(){
    	float[] outerR = new float[] { 4, 4, 4, 4, 4, 4, 4, 4 };
		ShapeDrawable normal = new ShapeDrawable(new RoundRectShape(null, null,
				null));
		normal.getPaint().setColor(Color.WHITE);
		ShapeDrawable pressed = new ShapeDrawable(new RoundRectShape(null, null,
				null));
		pressed.getPaint().setColor(ColorUtil.pressolor());
		StateListDrawable bg = new StateListDrawable();
        bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
        bg.addState(View.ENABLED_FOCUSED_STATE_SET, pressed);
        bg.addState(View.ENABLED_STATE_SET, normal);
        bg.addState(View.FOCUSED_STATE_SET, pressed);
        bg.addState(View.EMPTY_STATE_SET, normal);
        return bg;
    }
}
