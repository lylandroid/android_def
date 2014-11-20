package com.wi360.mobile.wallet.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Messenger;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.wi360.mobile.wallet.ButtomConfirmDialogActivity;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.interfaces.MyRequestCallBack;
import com.wi360.mobile.wallet.utils.CommonUtil;
import com.wi360.mobile.wallet.utils.MyHttpUtils;
import com.wi360.mobile.wallet.utils.UIUtils;
import com.wi360.mobile.wallet.view.LoadDialog;

public abstract class MyAsyncTask<Bean> extends AsyncTask<String, String, String> {
	protected Activity context;
	public Dialog loadDialog;
	private String loadMsg;
	/**
	 * 显示提示消息
	 */
	private String showHintMsg;
	protected ResultBean resBean;

	private Object thiszz = this;

	/**
	 * @params:-1,onFailure
	 * 
	 * @params:0,onSuccess
	 * @params:1,onSuccess,拿到数据错误
	 */
	protected int isSuccess = -1;

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

						isSuccess = 1;
						showHintMsg = connectNetWorkSuccess(responseInfo);
						// UIUtils.showToast(context, "onSuccess成功");
						synchronized (thiszz) {
							thiszz.notifyAll();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						isSuccess = -1;
						System.out.println(msg);
						// 如果需要处理onFailure的失败信息,重写该方法即可
						showHintMsg = connectNetWorkFailure(error, msg);
						// UIUtils.showToast(context, "onFailure失败");

						error.printStackTrace();
						synchronized (thiszz) {
							thiszz.notifyAll();
						}
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
