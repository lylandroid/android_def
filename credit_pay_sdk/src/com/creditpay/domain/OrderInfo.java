package com.creditpay.domain;

public class OrderInfo {
	private String orderId;
	private int isDisplay;
	private String sellerName;
	private String appName;
	private int sum;
	private String payTime;
	private String status;
	private String buyerId;
	private int appId;
	private String goodsName;
	public OrderInfo(String orderId, int isDisplay, String sellerName,
			String appName, int sum, String payTime, String status,
			String buyerId, int appId,String goodsName) {
		super();
		this.orderId = orderId;
		this.isDisplay = isDisplay;
		this.sellerName = sellerName;
		this.appName = appName;
		this.sum = sum;
		this.payTime = payTime;
		this.status = status;
		this.buyerId = buyerId;
		this.appId = appId;
		this.goodsName=goodsName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(int isDisplay) {
		this.isDisplay = isDisplay;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Override
	public String toString() {
		return "OrderInfo [orderId=" + orderId + ", isDisplay=" + isDisplay
				+ ", sellerName=" + sellerName + ", appName=" + appName
				+ ", sum=" + sum + ", payTime=" + payTime + ", status="
				+ status + ", buyerId=" + buyerId + ", appId=" + appId + "]";
	}
	
}
