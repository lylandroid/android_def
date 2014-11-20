package com.wi360.pay.sdk.interfaces;

/**
 * 开发者通过注册回调,返回支付状态响应吗
 * 
 * @author Administrator
 * 
 */
public interface ResponseCallback {
	/**
	 * 该方法在主线程中在UI线程(主线程)中执行
	 * 
	 * @param code
	 *            :支付状态码
	 */
	public void responseStateCode(int code);
}
