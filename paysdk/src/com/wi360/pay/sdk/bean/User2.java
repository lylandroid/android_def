package com.wi360.pay.sdk.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;

public class User2 {
	@Id
	@NoAutoIncrement
	private int id;
	@Column(column = "token")
	private String token;
	@Column(column = "mobileNum")
	private String mobileNum;
	@Column(column = "creditLimit")
	private int creditLimit;
	@Column(column = "usedCredit")
	private int usedCredit;

	public User2() {
	}

	public User2(int id, String token, String mobileNum, int creditLimit, int usedCredit) {
		this.id = id;
		this.token = token;
		this.mobileNum = mobileNum;
		this.creditLimit = creditLimit;
		this.usedCredit = usedCredit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public int getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(int creditLimit) {
		this.creditLimit = creditLimit;
	}

	public int getUsedCredit() {
		return usedCredit;
	}

	public void setUsedCredit(int usedCredit) {
		this.usedCredit = usedCredit;
	}
	
	

}
