package com.creditpay;

public class Constant {
	final static String ADDRESS="http://115.29.7.155:8086";
	final static String GETSMS_URL=ADDRESS+"/if/auth/user/get_smscode";
	final static String LOGIN_URL=ADDRESS+"/if/auth/user/login";
	final static String PAY_URL=ADDRESS+"/if/credit_billing/order/pay";
	final static String GETCREDIT_URL=ADDRESS+"/if/credit_billing/credit/get";
	public final static int[] ERROR_CODE={1001,1200,1201,1202,1203};
	public final static String[] ERROR_MSG={"有线程正在支付！","用户退出支付!","网络异常！","没有发送短信权限！","没有发送短信权限或者发送短信超时！"};
	final static int RETURN_CODE=0;
	final static String UP_NUMBER="10657563219310";
	final static String RECHCARGE_URL="http://183.232.65.119:8881/paycredit/login.action";
}
