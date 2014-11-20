package com.wi360.pay.sdk.bean;

import java.io.Serializable;

public class ResultBean implements Serializable {

	public int errcode;
	public String errmsg;
	public String token;
	public String mobileNum;

	public Credit credit;
	public Order order;

	public class Credit {
		// 信用额度
		public int creditLimit;
		// 已使用信用
		public int usedCredit;
		public String mobileNum;
	}

	// 支付订单接口
	public class Order {
		public String orderId;
		public int isDisplay;
		public String sellerName;
		public String appName;
		public String productName;
		public int sum;
		public String payTime;
		public String status;
		public String buyerId;
		public String appId;
	}

	// 信用额度
	public int creditLimit;
	// 已使用信用
	public int usedCredit;

}
