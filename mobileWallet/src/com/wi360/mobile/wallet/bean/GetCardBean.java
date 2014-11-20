package com.wi360.mobile.wallet.bean;

import java.io.Serializable;
/**
 * 获取钱包账户详情
 * @author Administrator
 *
 */
public class GetCardBean implements Serializable {
	public String mobileNum;

	public GetCardBean(String mobileNum) {
		this.mobileNum = mobileNum;
	}
}
