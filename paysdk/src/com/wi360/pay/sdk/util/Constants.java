package com.wi360.pay.sdk.util;

import android.net.Uri;

/**
 * 常量类
 * 
 * @author Administrator
 * 
 */
public class Constants {
	/**
	 * sdk代码
	 */
	// 验证码是否发送成功
	public static String IS_SEND_SMS_CODE_SUCCESS = "is_code_send_cuccess";

	public static String HTTP_URL = "http://115.29.7.155:8086/";
	public static String SEND_SMS_URL = HTTP_URL + "if/auth/user/get_smscode";
	public static String LOGIN_URL = HTTP_URL + "if/auth/user/login";
	public static String PAY_URL = HTTP_URL + "if/credit_billing/order/pay";
	// 获取信用信息接口
	public static String GET_PAY_URL = HTTP_URL + "if/credit_billing/credit/get";

	/**
	 * save属性名字
	 */
	 public static String token = "token";
	 public static String charset = "UTF-8";
	 // 信用额度
	 public static String creditLimit = "creditLimit";
	 // 已使用信用
	 public static String usedCredit = "usedCredit";
	 //电话号码
	 public static String mobileNum = "mobileNum";
	public static String end_sms_code_time = "end_sms_code_time";
	// 验证码名
	public static String SEND_SMS_CODE_TIEM = "send_sms_code_time";

	/**
	 * 支付状态吗
	 */
	// 支付成功
	public static int pay_code_success = 200;
	// 支付失败
	public static int pay_code_failure = 404;
	// 商品名称，可以是汉字，支持28个字节的字母和数字，或支持14个汉字
	public static int pay_code_productName = 401;
	// 交易金额，分为单位，最大到千元，即6个字节
	public static int pay_code_sum = 402;
	// 商户自定义，28个字节，如果商户传入值大于28，则截取28个字节
	public static int pay_code_alias = 403;
	// 商户用户ID，28个字节，如果商户传入值大于28，则截取28个字节
	public static int pay_code_sellerUserId = 405;

	// 没有网络
	public static int pay__code_no_netswork = 406;
	//用户取消支付
	public static int pay__code_user_cancel = 407;

}
