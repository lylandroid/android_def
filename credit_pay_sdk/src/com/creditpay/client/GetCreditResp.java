package com.creditpay.client;

public class GetCreditResp {
	public int errcode;
	public String errmsg;
	public String mobileNum;
	public String appId;
	public int creditLimit;
	public int usedCredit;
	@Override
	public String toString() {
		return "GetCreditResp [errcode=" + errcode + ", errmsg=" + errmsg
				+ ", mobileNum=" + mobileNum + ", appId=" + appId
				+ ", creditLimit=" + creditLimit + ", usedCredit=" + usedCredit
				+ "]";
	}
	
}
