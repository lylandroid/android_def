package com.wi360.sms.marketing.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Messenger;
import android.util.Log;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.wi360.sms.marketing.dialog.ButtomConfirmDialogActivity;
import com.wi360.sms.marketing.dialog.LoadDialog;
import com.wi360.sms.marketing.interfaces.MyRequestCallBack;
import com.wi360.sms.marketing.utils.CommonUtil;

public abstract class MyAsyncTask<T> extends AsyncTask<String, String, String> {
	protected static final String TAG = " MyAsyncTask<T>";
	protected Activity context;
	public Dialog loadDialog;
	private String loadMsg;
	/**
	 * 显示提示消息
	 */
	private String showHintMsg;
	protected T resBean;

	private Object thiszz = this;

	public MyAsyncTask(Activity context, String loadMsg) {
		this.context = context;
		this.loadMsg = loadMsg;
	}

	@Override
	protected void onPreExecute() {
		if (loadMsg != null) {
			loadDialog = LoadDialog.createLoadingDialog(context, loadMsg + "...");
			if (!loadDialog.isShowing()) {
				loadDialog.show();
			}
		}

	}

	/**
	 * @params[0]:url
	 * @params[1]:json
	 * @return 发送成功返回null,发送失败返回失败信息
	 */
	@Override
	protected String doInBackground(String... params) {
		showHintMsg = "连接超时";
		// 如果不需要使用默认的方式访问网络
		if (params == null || params.length < 1) {
			showHintMsg = connectNetWorkSuccess(null);
		} else {
			synchronized (thiszz) {
				MyHttpUtils.sendPost(params[0], params[1], null, new MyRequestCallBack() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i(TAG, "onSuccess " + responseInfo.result);
						showHintMsg = connectNetWorkSuccess(responseInfo);
						// UIUtils.showToast(context, "onSuccess成功");
						synchronized (thiszz) {
							thiszz.notifyAll();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Log.e(TAG, "onFailure "+msg);
						// 如果需要处理onFailure的失败信息,重写该方法即可
						showHintMsg = connectNetWorkFailure(error, msg);
						// UIUtils.showToast(context, "onFailure失败");

						synchronized (thiszz) {
							thiszz.notifyAll();
						}
						error.printStackTrace();
					}
				});
				try {
					thiszz.wait(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return showHintMsg;
	}

	/**
	 * 失败的时候调用,弹出失败信息,msg:失败信息,msg==null不弹出失败框
	 */
	@Override
	protected void onPostExecute(String msg) {

		if (CommonUtil.isNetworkAvailable(context) == 0) {// 没有
			msg = "请检查网络";
			Intent intent = new Intent(context, ButtomConfirmDialogActivity.class);
			Messenger messenger = new Messenger(new Handler());
			intent.putExtra("messenger", messenger);
			intent.putExtra("msg", msg);
			context.startActivity(intent);
		} else if (msg != null) {// 失败,有错误信息
			Intent intent = new Intent(context, ButtomConfirmDialogActivity.class);
			Messenger messenger = new Messenger(new Handler());
			intent.putExtra("messenger", messenger);
			intent.putExtra("msg", msg);
			context.startActivity(intent);
		}

		if (loadDialog != null && loadDialog.isShowing()) {
			loadDialog.dismiss();
		}

	}

	/**
	 * 连接网络成功,调用该方法
	 * 
	 * @param responseInfo
	 */
	public abstract String connectNetWorkSuccess(ResponseInfo<String> responseInfo);

	/**
	 * 连接网络失败,调用该方法 如果需要处理onFailure的失败信息,重写该方法即可
	 * 
	 * @param responseInfo
	 * @param msg
	 */
	public String connectNetWorkFailure(HttpException error, String msg) {
		return "服务器忙,请稍后再试";
	}

}
