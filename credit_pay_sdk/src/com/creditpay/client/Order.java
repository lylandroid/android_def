package com.creditpay.client;

public class Order {
	public String orderId;
	public int isDisplay;
	public String sellerName;
	public String appName;
	public int sum;
	public String payTime;
	public String status;
	public String buyerId;
	public int appId;
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", isDisplay=" + isDisplay
				+ ", sellerName=" + sellerName + ", appName=" + appName
				+ ", sum=" + sum + ", payTime=" + payTime + ", status="
				+ status + ", buyerId=" + buyerId + ", appId=" + appId + "]";
	}
	
}
