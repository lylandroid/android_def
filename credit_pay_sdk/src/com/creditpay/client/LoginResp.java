package com.creditpay.client;

public class LoginResp {
	public int errcode;
	public String errmsg;
	public String token;
	public Credit credit;
	@Override
	public String toString() {
		return "LoginResp [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", token=" + token + ", credit=" + credit + "]";
	}
	
}
