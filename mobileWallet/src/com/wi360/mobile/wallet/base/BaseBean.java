package com.wi360.mobile.wallet.base;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.content.Context;

import com.wi360.mobile.wallet.R;

/**
 * 基类bean
 * 
 * @author Administrator
 * 
 */
public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String getAppCode(Context context) {
		return context.getResources().getString(R.string.appCode);
	}
	protected String getAppCodeZjht(Context context) {
		return context.getResources().getString(R.string.appCodeZjht);
	}
	protected String getKeyZJHT(Context context) {
		return context.getResources().getString(R.string.key_zjht);
	}

	protected String getAppKey(Context context) {
		return context.getResources().getString(R.string.appKey);
	}

	protected String getAppId(Context context) {
		return context.getResources().getString(R.string.appId);
	}

	protected String getRequestType(Context context) {
		return context.getResources().getString(R.string.requestType);
	}

	/**
	 * MD5 加密
	 */
	protected String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	protected long genRandNum(int codeLen) {

		StringBuffer tmpBuff = new StringBuffer("1,2,3,4,5,6,7,8,9,0");

		java.util.Random r = new Random();
		String[] strArray = tmpBuff.toString().split(",");

		StringBuffer resultBuff = new StringBuffer();

		for (int i = 0; i < codeLen; i++) {
			int k = r.nextInt();
			resultBuff.append(String.valueOf(strArray[Math.abs(k % 10)]));
		}
		return Long.parseLong(resultBuff.toString());

	}

	public String genSign(String key, String msg) {
		return com.wi360.mobile.wallet.utils.MessageDigestHelper.encode("SHA-1", key + msg + key, null);
	}

}
