/**
 * File Name:JsonModel.java
 * Package Name:com.zzkko.component.http
 * author:yangxiongjie
 * Date:2014-1-16下午1:57:15
 * Copyright (c) 2014, zzkko All Rights Reserved.
 */
package com.creditpay.component;

/**
 * json字符串的解析对象的基类
 */
public class JsonModel {
	public String msg;

	public int timestamp;

	public int ret;

	@Override
	public String toString() {
		return "JsonModel [msg=" + msg + ", timestamp=" + timestamp + ", ret="
				+ ret + "]";
	}

}
