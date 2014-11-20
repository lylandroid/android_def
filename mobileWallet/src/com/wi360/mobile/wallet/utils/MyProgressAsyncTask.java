package com.wi360.mobile.wallet.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class MyProgressAsyncTask extends
		AsyncTask<String, ProgressDialog, String> {

	/**
	 * 子线程
	 */
	@Override
	protected String doInBackground(String... params) {
		return null;
	}

	/**
	 * 子线程,前
	 */
	@Override
	protected void onPreExecute() {
	}

	/**
	 * 子线程后
	 */
	@Override
	protected void onPostExecute(String result) {
	}

}
