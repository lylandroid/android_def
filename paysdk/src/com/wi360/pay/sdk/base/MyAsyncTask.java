package com.wi360.pay.sdk.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.wi360.pay.sdk.PaySelectController;
import com.wi360.pay.sdk.R;
import com.wi360.pay.sdk.bean.PayOrderBean.Pay;
import com.wi360.pay.sdk.interfaces.MyRequestCallBack;
import com.wi360.pay.sdk.interfaces.ResponseCallback;
import com.wi360.pay.sdk.util.CommonUtil;
import com.wi360.pay.sdk.util.Constants;
import com.wi360.pay.sdk.util.SharedPreferencesUtils;
import com.wi360.pay.sdk.util.StringUtils;
import com.wi360.pay.sdk.util.UIUtils;

public abstract class MyAsyncTask<ResBean> extends AsyncTask<String, String, String> {
	protected Activity context;
	public Dialog loadDialog;
	private String loadMsg;
	private String resultStr;
	protected ResBean resBean;

	private Object thiszz = this;
	// 开发者回调
	private ResponseCallback responseCallback;
	// 保存开发者传入数据
	private Pay payBean;

	/**
	 * @params:-1,onFailure
	 * 
	 * @params:0,onSuccess
	 * @params:1,onSuccess,拿到数据错误
	 */
	protected int isSuccess = -1;
	// 判断是否有网络,默认有网络
	protected boolean isNetsWork = true;

	public MyAsyncTask(Activity context, String loadMsg, Pay payBean, ResponseCallback responseCallback) {
		this.context = context;
		this.loadMsg = loadMsg;
		this.payBean = payBean;
		this.responseCallback = responseCallback;
	}

	@Override
	protected void onPreExecute() {

		if (CommonUtil.isNetworkAvailable((Context) context) == 0) {// 没有网络
			isNetsWork = false;
		}
	}

	/**
	 * @params[0]:url
	 * @params[1]:json
	 * @return 发送成功返回null,发送失败返回失败信息
	 */
	@Override
	protected String doInBackground(String... params) {
		resultStr = null;
		// 有网络
		if (isNetsWork) {
			synchronized (thiszz) {
				MyHttpUtils.sendPost(params[0], params[1], null, new MyRequestCallBack() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						isSuccess = 1;
						resultStr = connectNetWorkSuccess(responseInfo);
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
						resultStr = connectNetWorkFailure(error, msg);
						// UIUtils.showToast(context, "onFailure失败");

						error.printStackTrace();
						synchronized (thiszz) {
							thiszz.notifyAll();
						}
					}
				});
				try {
					thiszz.wait(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return resultStr;
	}

	/**
	 * 失败的时候调用,弹出失败信息,msg:失败信息,msg==null不弹出失败框
	 */
	@Override
	protected void onPostExecute(String msg) {
		isNetsWork = true;
		if (msg != null) {
			UIUtils.showToast(context, msg);
		}
		if (CommonUtil.isNetworkAvailable((Context) context) == 0) {// 没有网络
			// 打开网络界面
			// context.startActivity(new
			// Intent(Settings.ACTION_WIFI_SETTINGS));
			QidaDialog dialog = new QidaDialog(context, R.layout.dialog_select_model, R.style.QidaDialog,
					responseCallback);
			dialog.show();
			new PaySelectController(context, dialog, dialog.view, payBean, responseCallback);
			// 没有网络
			// isNetsWork = false;
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
