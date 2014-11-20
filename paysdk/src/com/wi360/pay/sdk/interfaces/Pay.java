package com.wi360.pay.sdk.interfaces;

public interface Pay {
	/**
	 * 
	 * @param productName
	 *            :商品名称，可以是汉字，支持28个字节的字母和数字，或支持14个汉字
	 * @param sum
	 *            :交易金额(1-100000分)
	 * @param alias
	 *            :开发者自定义串 ,可以是渠道标识，长度不能超过为100个字符(只能是字符或数字)
	 * @param sellerUserId
	 *            :商户用户ID，28个字符，如果商户传入值大于28，则截取28个字符(只能是字符或数字)
	 * @param responseCallback
	 *            :回调接口,通过此接口通知开发者支付状态
	 */
	void creditPay(String productName, int sum, String alias, String sellerUserId, ResponseCallback responseCallback);
}
