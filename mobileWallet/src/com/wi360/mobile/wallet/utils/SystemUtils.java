package com.wi360.mobile.wallet.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

public class SystemUtils {

	/**
	 * 获取缓存文件的大小
	 * 
	 * @param context
	 * @param packName
	 * @return
	 */
	public static long getAppCacheSize(Context context, String packName) {
		File file = context.getCacheDir();
		if (file.exists()) {
			return file.length();
		}
		return 0;
	}

	/**
	 * 把一个流里面的内容 转化成一个字符串
	 * 
	 * @param is
	 *            流里面的内容
	 * @return null解析失败
	 */
	public static String readStream(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			is.close();
			String res = new String(baos.toByteArray(),"GBK");
			baos.close();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 判断SD卡是否可用
	 * 
	 * @return true,SD可用
	 */
	public boolean isSDRead() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	// ---------------------------------------------
	
	

}
