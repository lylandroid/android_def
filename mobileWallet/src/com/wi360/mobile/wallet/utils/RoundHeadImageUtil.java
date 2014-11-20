package com.wi360.mobile.wallet.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.wi360.mobile.wallet.MainActivity;
import com.wi360.mobile.wallet.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

public class RoundHeadImageUtil {
	private static final int STROKE_WIDTH = 4;

	// 从assets资源中获取图片
	public static Bitmap getBitmap(Context context, int resId) {
		Bitmap image = null;
		image = BitmapFactory.decodeResource(context.getResources(), resId);
		return image;
	}

	/**
	 * 
	 * @param context
	 * @param file
	 *            图片文件+名
	 * @return
	 */
	public static Bitmap getBitmap(Context context, File file) {
		InputStream is = null;
		Bitmap image = null;
		try {
			is = new FileInputStream(file);
			image = BitmapFactory.decodeStream(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	public static Bitmap toRoundBitmap(Context context, int resId) {
		Bitmap bitmap = getBitmap(context, resId);
		return squareToRoundBitMap(bitmap);
	}

	public static Bitmap toRoundBitmap(Context context, File file) {
		Bitmap bitmap = getBitmap(context, file);
		return squareToRoundBitMap(bitmap);
	}

	public static Bitmap toRoundBitmap(Context context, Bitmap bitmap) {
		return squareToRoundBitMap(bitmap);
	}

	/**
	 * 把矩形的bitmap 转换成圆形
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap squareToRoundBitMap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			left = 0;
			bottom = width;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(4);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		// 画白色圆圈
		paint.reset();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setAntiAlias(true);
		canvas.drawCircle(width / 2, width / 2, width / 2 - STROKE_WIDTH / 2,
				paint);
		return output;
	}

	/**
	 * 保存图片到本地
	 * 
	 * @param context
	 * @param mitmap
	 * @return
	 */
	public static boolean saveBitMapLock(Activity context, Bitmap mitmap,
			File file) {
		boolean isSave = false;
		try {
			// File file = new File(Environment.getExternalStorageDirectory(),
			// "haha.png");
			FileOutputStream stream = new FileOutputStream(file);
			mitmap.compress(CompressFormat.PNG, 100, stream);
			stream.close();
			isSave = true;
			// 欺骗图库应用。模拟一个sd卡挂载的广播消息。
			// Intent intent = new Intent();
			// intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
			// intent.setData(Uri.fromFile(Environment
			// .getExternalStorageDirectory()));
			// context.sendBroadcast(intent);
		} catch (Exception e) {
			e.printStackTrace();
			isSave = false;
		}
		return isSave;
	}

	/**
	 * 获取本地图片
	 * 
	 * @param file
	 * @return null,读取失败
	 */
	public static Bitmap getLockHeadToBitmap(File file) {
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	/**
	 * 获取本地圆形头像,如果没有找到,使用默认图片
	 * 
	 * @param context
	 * @param iv_head
	 *            ,UI上显示图片的ImageView
	 * @param isTemp
	 *            ,true本地头像,false临时头像
	 */
	public static void getRoundHeadImageUI(Activity context, ImageView iv_head,
			boolean isTemp) {
		// 本地图片文件
		File headLockFile = getPhotoFilePath(context,isTemp);
		// 把本地头像文件转化成bitmap
		Bitmap bitmap = RoundHeadImageUtil.getLockHeadToBitmap(headLockFile);
		if (bitmap == null) {
			// iv_head.setBackgroundDrawable(new
			// BitmapDrawable(RoundHeadImageUtil
			// .toRoundBitmap(context, R.drawable.head_img)));
			// iv_head.getBackground().setAlpha(0);
			iv_head.setImageBitmap(RoundHeadImageUtil.toRoundBitmap(context,
					R.drawable.head_img));
		} else {
			// iv_head.setBackgroundDrawable(new
			// BitmapDrawable(RoundHeadImageUtil
			// .toRoundBitmap(context, bitmap)));
			// iv_head.getBackground().setAlpha(0);

			// Bitmap bitmap2 =
			// RoundHeadImageUtil.getLockHeadToBitmap(headLockFile);
			iv_head.setImageBitmap(RoundHeadImageUtil.toRoundBitmap(context,
					bitmap));
		}

	}

	// 使用系统当前日期加以调整作为照片的名称
	public static File getPhotoFilePath(Context context,boolean isTemp) {

		String md5Head = Md5Utils.encode(Constants.server_url
				+ Constants.head_img_name);
		// 头像名
		if (isTemp) {
			md5Head = md5Head + Constants.head_ext;
		} else {
			// 临时头像名
			md5Head = md5Head + "2" + Constants.head_ext;
		}
//		File headLockFile = new File(Environment.getExternalStorageDirectory(),
//				md5Head);
		File headLockFile = new File(context.getFilesDir(),
				md5Head);
		return headLockFile;
	}
}
