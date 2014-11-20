package com.wi360.mobile.wallet;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lidroid.xutils.http.ResponseInfo;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.FindMobileBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;

/**
 * 展示银行的支付webView页面
 * 
 * @author Administrator
 * 
 */
public class PayActivity extends Activity {
	
	private String TAG = "PayActivity";

	private WebView webView;
	private ResultBean resultBean;
	/**
	 * fase:没有开始支付, true:正在支付中
	 */
	private boolean isPay = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_pay);
		webView = (WebView) findViewById(R.id.web_view);
		// webView.getSettings().setBuiltInZoomControls(true);// 设置使支持缩放
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				// TODO Auto-generated method stub
				super.onShowCustomView(view, callback);
			}

		});

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				// handler.cancel(); // Android默认的处理方式
				handler.proceed(); // 接受所有网站的证书 4
				// handleMessage(Message msg); // 进行其他处理
			}

		});

		Intent intent = getIntent();
		if (intent.getExtras() != null && intent.getExtras().get("bean") != null) {
			resultBean = (ResultBean) intent.getExtras().get("bean");
			if (resultBean.payUrl != null) {
				webView.loadUrl(resultBean.payUrl);
				// webView.loadUrl("http://www.baidu.com");
			} else {
				webView.loadUrl(resultBean.errmsg);
			}
		} else {
			finish();
		}

		// webView.loadUrl(resultBean.payUrl);

	}

	// 默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 判断是否正在进行支付
		if (!isPay) {
			if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
				webView.goBack();
				return true;
			}
			backFind();
			// return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	/**
	 * 点击退出的时候,查询支付结果
	 */
	public void backFind() {
		FindMobileBean mobileBean = new FindMobileBean(this, resultBean.mobileNum);
		String json = GsonTools.createGsonString(mobileBean);
		Log.i(TAG, json);
		new MyAsyncTask<Object>(this, "查询中") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					resBean = GsonTools.changeGsonToBean(responseInfo.result, ResultBean.class);
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				if (resBean != null) {
					if (resBean.errcode == 0) {
						// 支付成功
						ActivityAnimationUtils.rightToLeftInAnimation(context, CallsSuccessActivity.class, resBean);
					} else {
						// 支付失败
						ActivityAnimationUtils.leftToRightOutAnimation(PayActivity.this, MainActivity.class);
					}
				}
				// 测试代码
				// ActivityAnimationUtils.rightToLeftInAnimation(context,
				// CallsSuccessActivity.class, resultBean);
				super.onPostExecute(msg);
			};
		}.execute(new String[] { Constants.FIND_MOBILE_PAY, json });
	}

}
