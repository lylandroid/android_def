package com.wi360.mobile.wallet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class CheckUtils {
	/**
	 * 验证是否是有效电话号码
	 * 
	 * @param mobiles
	 * @return true验证通过(有效)
	 */
	public static boolean checkMobileNO(Activity context, String mobiles) {
		String msg = null;
		boolean state = true;
		if (TextUtils.isEmpty(mobiles)) {
			msg = "请填写手机号码";
			state = false;
		}
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		//
		if (!m.matches() && state) {
			msg = "电话号码格式不正确 !";
			state = false;
		}

		if (!state) {
			UIUtils.showToast(context, msg);
		}

		return state;
	}

	/**
	 * 移动号码判断,
	 * 
	 * @param mobile
	 * @return :boolean：是移动号码返回真，否则返回假
	 */
	public static boolean IsMobileNum(String mobile) {
		String reg = "/^(((13)[5-9]{1})|((15)[0,1,2,7,8,9]{1})|(188))[0-9]{8}$|(^((134)[0-8]{1})[0-9]{7}$)/";
		return Pattern.matches(reg, mobile);
	}

}
