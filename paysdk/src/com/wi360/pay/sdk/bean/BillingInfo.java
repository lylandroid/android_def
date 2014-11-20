/**
 * File Name:BillingInfo.java
 * Package Name:com.jifei.android.domain
 * author:yangxiongjie
 * Date:2014-3-17上午11:45:13
 * Copyright (c) 2014, zzkko All Rights Reserved.
 */
package com.wi360.pay.sdk.bean;

import java.io.Serializable;

/**
 * 结算信息
 * simple desrciption
 */
public class BillingInfo implements Serializable {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.6
	 */
	private static final long serialVersionUID = 1L;
	private String orderid;// 创建好的订单流水号
	private String smsPort;// 需要发送计费短信的端口号
	private String upSMS;// 需要发送计费短信内容
	private int isDisplay;// 是否需要弹窗显示，让用户二次确认
	private String feeDisplayName;// 需要显示的计费商品名称
	private String goodsName;//扣费商品名称
	private String payAmount;//扣费总额
	private int isBuyerIdExist;
	public BillingInfo(String orderid, String smsPort, String upSMS,
			int isDisplay, String feeDisplayName,int isBuyerIdExist) {
		super();
		this.orderid = orderid;
		this.smsPort = smsPort;
		this.upSMS = upSMS;
		this.isDisplay = isDisplay;
		this.feeDisplayName = feeDisplayName;
		this.isBuyerIdExist=isBuyerIdExist;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getSmsPort() {
		return smsPort;
	}
	public void setSmsPort(String smsPort) {
		this.smsPort = smsPort;
	}
	public String getUpSMS() {
		return upSMS;
	}
	public void setUpSMS(String upSMS) {
		this.upSMS = upSMS;
	}
	public int getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(int isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getFeeDisplayName() {
		return feeDisplayName;
	}
	public void setFeeDisplayName(String feeDisplayName) {
		this.feeDisplayName = feeDisplayName;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	public int getIsBuyerIdExist() {
		return isBuyerIdExist;
	}
	public void setIsBuyerIdExist(int isBuyerIdExist) {
		this.isBuyerIdExist = isBuyerIdExist;
	}
	@Override
	public String toString() {
		return "BillingInfo [orderid=" + orderid + ", smsPort=" + smsPort
				+ ", upSMS=" + upSMS + ", isDisplay=" + isDisplay
				+ ", feeDisplayName=" + feeDisplayName + ", goodsName="
				+ goodsName + ", payAmount=" + payAmount + ", isBuyerIdExist="
				+ isBuyerIdExist + "]";
	}
	
}
