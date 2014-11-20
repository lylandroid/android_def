package com.wi360.pay.sdk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.text.TextUtils;

public class CheckUtils {
	/**
	 * 验证是否是有效电话号码
	 * @param mobiles
	 * @return  true验证通过(有效)
	 */
	public static boolean checkMobileNO(Activity context,String mobiles){ 
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
        
        if(!state){
        	UIUtils.showToast(context, msg);
        }
        
        return state;     
    } 
	
	
}
