package com.creditpay.client;

public class PayParam {
	public String appId;
	public String timeStamp;
	public String nonce;
	public String token;
	public String signature;
	public Pay pay;
	@Override
	public String toString() {
		return "CreateOrderResp [appId=" + appId + ", timeStamp=" + timeStamp
				+ ", nonce=" + nonce + ", token=" + token + ", signature="
				+ signature + ", pay=" + pay + "]";
	}
	
}
