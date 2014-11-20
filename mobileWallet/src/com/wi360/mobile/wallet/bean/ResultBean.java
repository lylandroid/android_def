package com.wi360.mobile.wallet.bean;

import java.io.Serializable;
import java.util.List;

public class ResultBean implements Serializable {
	/**
	 * 如果返回成功json没有errcode字段,默认值0
	 */
	public int errcode;
	public String errmsg;

	public String token;
	public int expire_in;

	// 卡号
	public String pan;
	// 身份证号码
	public String account;
	// 绑定金额
	public String balance;

	public List<CardList> cardList;

	public class CardList implements Serializable {
		// 卡号
		public String pan;
		public String name;
	}

	/**
	 * 交易明细
	 */
	public String service;
	public String state;
	public String type;
	public String version;

	public Content content;

	public class Content implements Serializable {
		public int pageIndex;
		public int pageSize;
		public int totalRecords;
		public List<Records> records;

		public class Records implements Serializable {
			public String accountNo;
			public String acqId;
			public float amount;
			public String area;
			public String areaName;
			public String carLicenseNo;
			public String cardNo;
			public String cardType;
			public String custName;
			public String custNo;
			public String goodsNum;
			public String goodsPrice;
			public String goodsQty;
			public String issuId;
			public String merchantCode;
			public String merchantName;
			public String shopCode;
			public String shopName;
			public String statusName;
			public String terminalNo;
			public String transDate;
			public String transId;
			public String transType;
			public String transTypeName;
			/**
			 * 电子券3.4.10接口数据
			 */
			public String assisCode;
			public int balance;
			public String couponName;
			public String endTime;
			public String parPrice;
			public String status;
		}

		/**
		 * 中经汇通,返回数据
		 */
		public String respCode;
		public String respMsg;
		/**
		 * 中经汇通,10.4.11返回数据
		 */
		public float amount;
		public String assisCode;
		public int balance;
		// 电子券商品编码
		public String couponCode;
		public String couponName;
		public String endTime;
		public float parPrice;
		// 二维码图片文件名（大图）
		public String qrcodePicFileId;
		public String shopName;
		public String status;
		public String txnTime;

	}

	/**
	 * 查询我的电子劵接口
	 */
	public String pageSize;
	public String pageIndex;
	public String totalRecords;
	public List<RecordsMyVolume> records;

	public class RecordsMyVolume implements Serializable {
		/**
		 * 查询我的电子劵接口
		 */
		public String amount;
		// 辅助码
		public String assisCode;
		// 剩余使用次数
		public String balance;
		// 电子劵名称
		public String couponName;
		// 电子劵有效期截止时间
		public String endTime;
		// 电子劵面值
		public String parPrice;
		// 使用状态(未使用、已转让、已使用、部分使用)
		public String status;

	}

	// end查询我的电子劵接口

	/**
	 * 我的电子劵详情接口
	 */
	public String amount;
	public String assisCode;
	// public String balance;
	public String couponCode;
	public String coupon_name;
	public String endTime;
	public String parPrice;
	public String qrcodePicFileId;
	public String shopName;
	public String status;
	public String txnTime;
	// end 我的电子劵详情接口
	/**
	 * 话费充值接口
	 */
	public String payUrl;
	// 电话号码
	public String mobileNum;
	// end 话费充值接口

	/**
	 * 查询话费充值接口
	 */
	public Order order;

	public class Order implements Serializable {
		public String orderId;
		public String payTime;
		public String productName;
		public String status;
		public String sum;
	}

	// end 查询话费充值接口

	/**
	 * 获取钱包账户详情
	 */

	public String cardName;
	public String cardPic;
	public String expiryDate;
	public String picType;
	public String sum;
	// end 获取钱包账户详情

	/**
	 * 中经汇通,返回数据
	 */
	// public String service;
	// public String state;

}
