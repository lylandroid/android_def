package com.wi360.mobile.wallet.bean;

public class PhoneMomeyBean {

	/**
	 * 该价格是否被选中
	 */
	public boolean isSelect = false;
	/**
	 * 充值价格
	 */
	public String momey;
	/**
	 * 应付价格
	 */
	public String momey2;
	
	public PhoneMomeyBean(String momey, String momey2, boolean isSelect) {
		this.isSelect = isSelect;
		this.momey = momey;
		this.momey2 = momey2;
	}

	public PhoneMomeyBean(String momey, String momey2) {
		this.momey = momey;
		this.momey2 = momey2;
	}

	@Override
	public String toString() {
		return momey + "元(售价:" + momey2 + "元)";
	}

}
