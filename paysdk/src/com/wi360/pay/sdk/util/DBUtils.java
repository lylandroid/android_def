/*package com.wi360.pay.sdk.util;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.wi360.pay.sdk.bean.User2;

public class DBUtils {
	private static DbUtils db;
	private static User2 user;

	public static User2 getUser(Context context) {
		if (db == null) {
			db = DbUtils.create(context);
		}
		try {
			user = db.findById(User2.class, 0);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static void updateUser(Context context, User2 user) {
		if (db == null) {
			db = DbUtils.create(context);
		}
		try {
			db.saveOrUpdate(user);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	public static void saveUser(Context context, User2 user) {
		if (db == null) {
			db = DbUtils.create(context);
		}
		try {
			db.save(user);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
*/