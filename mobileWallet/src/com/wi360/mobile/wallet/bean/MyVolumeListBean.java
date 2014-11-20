package com.wi360.mobile.wallet.bean;

/**
 * 显示电子卷列表的bean
 * 
 * @author Administrator
 * 
 */
public class MyVolumeListBean {
	/**
	 * title
	 */
	public String title;
	/**
	 * 编号
	 */
	public String number;
	/**
	 * 有效期到
	 */
	public String date;
	/**
	 * 使用次数
	 */
	public int repeatNumber;

	public MyVolumeListBean(String title, String number, String date, int repeatNumber) {
		this.title = title;
		this.number = number;
		this.date = date;
		this.repeatNumber = repeatNumber;
	}

}
