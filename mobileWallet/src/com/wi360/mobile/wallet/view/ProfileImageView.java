package com.wi360.mobile.wallet.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ProfileImageView extends ImageView {
	private Path mPath = new Path();

	public ProfileImageView(Context paramContext) {
		this(paramContext, null);
	}

	public ProfileImageView(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	@SuppressLint({ "NewApi" })
	public ProfileImageView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		if (Build.VERSION.SDK_INT > 10)
			setLayerType(1, null);
	}

	protected void onDraw(Canvas paramCanvas) {
		float f1 = getMeasuredWidth() / 2;
		float f2 = getMeasuredHeight() / 2;
		float f3 = 0;
		if (f1 < f2) {
			f3 = f1;
		} else {
			f3 = f2;
		}
		this.mPath.reset();
		this.mPath.addCircle(f1, f2, f3, Path.Direction.CCW);
		
//		Matrix matrix = new Matrix();
//		matrix.postScale(0.5f, 0.5f);
//		paramCanvas.setMatrix(matrix);
//		paramCanvas.setDensity(1);
		paramCanvas.clipPath(this.mPath);

//		Matrix matrix = new Matrix();
////		matrix.setTranslate(f1, f2);
//
//		Paint paint = new Paint();
//		paint.setAntiAlias(true);
//		paramCanvas.drawBitmap(getRoundedCornerBitmap(BitmapFactory
//				.decodeResource(getResources(), R.drawable.head_icon)), 0, 0,
//				paint);

		super.onDraw(paramCanvas);
	}

	public static abstract interface LockScreenLayoutListener {
		public abstract void onUnLock();
	}

	public Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPX = bitmap.getWidth() / 2;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return outBitmap;
	}

}

/*
 * Location:
 * C:\Users\Administrator\Desktop\com.esun.cdgroup_66806400_classes_dex2jar.jar
 * Qualified Name: com.esun.cdgroup.view.ProfileImageView JD-Core Version: 0.6.0
 */