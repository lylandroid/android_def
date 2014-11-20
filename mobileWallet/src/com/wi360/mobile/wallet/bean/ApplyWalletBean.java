package com.wi360.mobile.wallet.bean;

import android.content.Context;

import com.wi360.mobile.wallet.base.BaseBean;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 申请汇通钱包账户接口
 * 
 * @author Administrator
 * 
 */
public class ApplyWalletBean extends BaseBean {

	public ApplyWalletBean(String name, String id, String mobile) {
		this.name = name;
		this.id = id;
		this.mobile = mobile;
	}

	public String name;
	public String id;
	public String mobile;

}
