package com.wi360.mobile.wallet.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class VersionUtils {
	private final int SHOW_UPDATE_DIALOG = 1;
	private final int LOAD_MAINUI = 2;
	private String downloadurl;
	private String desc;
	// 包管理器
	private static PackageManager packageManager;

	/**
	 * 连接服务器,获取服务器版本号
	 */
	public void checkVersionUpdate(final Activity context,
			final Handler handler, final int appVewsion) {
		new Thread() {

			public void run() {
				Message msg = Message.obtain();
				// 检查 代码执行的时间。如果时间少于2秒 补足2秒
				long startTime = System.currentTimeMillis();
				try {
					URL url = new URL(HMApi.SERVER_URL + HMApi.APP_VERSION_JSON);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(2000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream is = conn.getInputStream();// json文本
						String json = SystemUtils.readStream(is);
						if (TextUtils.isEmpty(json)) {
							// 服务器json获取失败
							// 错误2016 请联系客服
							UIUtils.showToast(context,
									"错误2016, 服务器json获取失败,请联系客服");
							msg.what = LOAD_MAINUI;
						} else {
							JSONObject jsonObj = new JSONObject(json);
							downloadurl = jsonObj.getString("downloadurl");
							int serverVersionCode = jsonObj.getInt("version");
							desc = jsonObj.getString("desc");
							if (appVewsion == serverVersionCode) {
								// 相同，进入应用程序主界面
								msg.what = LOAD_MAINUI;
							} else {
								// 不同，弹出更新提醒对话框
								msg.what = SHOW_UPDATE_DIALOG;
							}
						}
					} else {
						// 错误2015 请联系客服
						UIUtils.showToast(context, "错误2015, 服务器状态码错误,请联系客服");
						msg.what = LOAD_MAINUI;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					// 错误2011 请联系客服
					UIUtils.showToast(context, "错误2011, url路径不正确,请联系客服");
					msg.what = LOAD_MAINUI;
				} catch (NotFoundException e) {
					e.printStackTrace();
					// 错误2012 请联系客服
					UIUtils.showToast(context, "错误2012, 服务器地址找不到,请联系客服");
					msg.what = LOAD_MAINUI;
				} catch (IOException e) {
					e.printStackTrace();
					// 错误2013 请联系客服
					UIUtils.showToast(context, "错误2013, 网络错误,请联系客服");
					msg.what = LOAD_MAINUI;
				} catch (JSONException e) {
					e.printStackTrace();
					// 错误2014 请联系客服
					UIUtils.showToast(context, "错误2014, json解析错误,请联系客服");
					msg.what = LOAD_MAINUI;
				} finally {
					long endtime = System.currentTimeMillis();
					long dtime = endtime - startTime;
					// if (dtime > 2000) {
					handler.sendMessage(msg);
					// } else {
					// try {
					// Thread.sleep(2000 - dtime);
					// } catch (InterruptedException e) {
					// e.printStackTrace();
					// }
					// handler.sendMessage(msg);
					// }
				}
			};
		}.start();

	}

	public void handlerMessage(final Activity context, Message msg) {
		switch (msg.what) {
		case LOAD_MAINUI:
			// loadMainUI();
			System.out.println("load main ui");
			break;
		case SHOW_UPDATE_DIALOG:
			// 因为对话框是activity的一部分显示对话框 必须指定activity的环境（令牌）
			AlertDialog.Builder builder = new Builder(context);
			builder.setTitle("更新提醒");
			builder.setMessage(desc);
			// builder.setCancelable(false);
			builder.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					// loadMainUI();
				}
			});
			builder.setNegativeButton("下次再说", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// loadMainUI();
				}

			});
			builder.setPositiveButton("立刻更新", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.out.println("下载：" + downloadurl);
					download(context, downloadurl);
				}

			});
			builder.show();
			break;
		}
	}

	/**
	 * 多线程的下载器
	 * 
	 * @param downloadurl
	 */
	private void download(final Activity context, String downloadurl) {
		// 多线程断点下载。
		HttpUtils http = new HttpUtils();
		http.download(downloadurl, Environment.getExternalStorageDirectory()
				+ HMApi.APP_INSTALL_URL, new RequestCallBack<File>() {
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				System.out.println("安装 /mnt/sdcard/temp.apk");

				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.addCategory("android.intent.category.DEFAULT");

				intent.setDataAndType(
						Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(),
								HMApi.APP_INSTALL_URL)),
						"application/vnd.android.package-archive");
				context.startActivityForResult(intent, 0);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(context, "下载失败", 0).show();
				System.out.println(arg1);
				arg0.printStackTrace();
				// loadMainUI();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// tv_info.setText(current + "/" + total);
				super.onLoading(total, current, isUploading);
			}
		});
	}

	/**
	 * 获取当前app的版本号
	 * 
	 * @return app版本号
	 */
	public int getAppVersionNum(Context context) {
		if (packageManager == null) {
			packageManager = context.getPackageManager();
		}
		int versionCode = 1;
		try {
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);

			versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
}
