package com.creditpay.client;

public class PayResp {
	public int errcode;
	public String errmsg;
	public Order order;
	public Credit credit;
	@Override
	public String toString() {
		return "CreateOrderResp [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", order=" + order + ", credit=" + credit + "]";
	}
	
}
