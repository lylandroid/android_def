package com.wi360.mobile.wallet.utils;

import android.net.Uri;

/**
 * 常量类
 * 
 * @author Administrator
 * 
 */
public class Constants {

	public static String token = "token";
	public static Uri intent_uri = Uri.parse("content://https://www.51pme.com/auth/");
	/**
	 * 头像名
	 */
	public static String head_img_name = "head_img";
	/**
	 * 头像后缀名
	 */
	public static String head_ext = ".png";
	// 显示的昵称名字
	// public static String SHOW_NICK_NAME = "show_nick_name";

	// 验证码名
	public static String SEND_SMS_CODE_TIEM = "send_sms_code_time";
	public static String SEND_SMS_CODE_TIEM_NEWS = "send_sms_code_time_news";
	public static String LOGIN_TIEM = "login_time";
	public static String IS_LOGIN = "is_login";
	public static String PHONE_NUMBER = "phone_number";

	// 验证码是否发送成功
	public static String IS_CODE_SEND_SUCCESS = "is_code_send_cuccess";
	public static String IS_CODE_SEND_SUCCESS_NEWS = "is_code_send_cuccess_news";
	// 服务器返回的短信验证码
	public static String SMS_CODE = "res_sms_code";

	// 保存头像是否成功
	public static String is_save_head_state_name = "is_save_head_state";

	/**
	 * url
	 */
	// 域名 https://115.29.249.236:8080/auth/user/login
	public static String COM_URL = "http://115.29.249.236:8080/";
	public static String COM_URL_ZJHT = "http://121.8.155.5:9060/zjhtplatform/";
	 public static String server_url = "https://192.168.56.1:8080/";
	// public static String COM_URL = "http://www.51pme.com/auth/";
	public static String SHOW_NICK_NAME = "show_nick_name";
	public static String charset = "UTF-8";
	// 发送短信的URL
	public static String SEND_SMS_URL = COM_URL + "auth/user/get_smscode";
	public static String UPDATE_USER_INFO_URL = COM_URL + "cmws/user/update";
	public static String LOGIN_URL = COM_URL + "auth/user/login";
	// public static String SEND_SMS_URL2 =
	// "https://www.51pme.com/auth/user/get_smscode";

	// 绑定(添加)汇通卡接口
	public static String BIND_CARD_URL = COM_URL + "cmws/huitongcard/bind";
	// 汇通卡详情(查询余额)
	public static String FIND_CARD_DETAIL_URL = COM_URL + "cmws/huitongcard/queryCardBalance";
	// 查询绑定汇通卡列表url
	public static String FIND_BIND_LIST_URL = COM_URL + "cmws/huitongcard/myCardList";
	// 交易明细
	public static String GET_DETAIL_URL = COM_URL + "cmws/huitongcard/queryCardDetail";
	// 激活汇通卡接口
	public static String ACTIVATE_CARD_SMS_URL = COM_URL + "cmws/huitongcard/sendSmsCode";
	// 激活汇通卡接口
	public static String ACTIVATE_CARD_URL = COM_URL + "cmws/huitongcard/activate";
	// 我的电子卷接口huishenghuo/ticket/getlist
	public static String MY_VOLUME_LIST = COM_URL_ZJHT + "trade.queryBarCodeList/1.0/";
	// 我的电子劵详情
	public static String MY_VOLUME_DETAIL = COM_URL_ZJHT + "trade.queryBarCodeDetail/1.0/";
	// 我的电子劵转让
	public static String MY_VOLUME_TRANSFER = COM_URL_ZJHT + "trade.assignBarCode/1.0/";
	// 查询手机充值结果
	public static String FIND_MOBILE_PAY = COM_URL + "cmws/mobile/isCharged";
	// 话费充值
	public static String PHONE_PAY_URL = COM_URL + "cmws/mobile/recharge/step1";

	// 申请钱包账户
	public static String APPLY_PAY_URL = COM_URL + "cmws/wallet/apply";
	// 申请钱包账户
	public static String FIND_APPLY_PAY_DETAIL_URL = COM_URL + "cmws/wallet/queryWalletDetail";

}
