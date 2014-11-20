package com.creditpay.ui;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.View.MeasureSpec;

public class BitmapUtil {
	private static Bitmap getRoundedCornerBitmap(Bitmap bitmap,int radius) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 12;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		bitmap.recycle();
		return output;
	}

	private static Bitmap create(View view,int width,int height,int radius) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, width, height);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return getRoundedCornerBitmap(bitmap,radius);
	}

	public static void setBackGround(View v,int w,int h,int radius,int color) {
		View bgView = new View(v.getContext());
		bgView.setBackgroundColor(color);
		v.setBackground(new BitmapDrawable(v.getContext().getResources(),
				BitmapUtil.create(bgView,w,h,radius)));
	}
}
