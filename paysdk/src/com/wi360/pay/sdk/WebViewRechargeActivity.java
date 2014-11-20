package com.wi360.pay.sdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wi360.pay.sdk.bean.PayOrderBean.Pay;

public class WebViewRechargeActivity extends Activity {

	private WebView webView;
	private Pay payBean;
	private Messenger messenger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_web_view_recharge);
		webView = (WebView) findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		initViewWebView();
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
			    if(!webView.getSettings().getLoadsImagesAutomatically()) {
			        webView.getSettings().setLoadsImagesAutomatically(true);
			    }
			}
		});
		Intent intent = getIntent();
		messenger = (Messenger) intent.getExtras().get("messenger");
		String url = intent.getStringExtra("url");
		if (url != null) {
			webView.loadUrl(url);
		}

	}
	
	public void initViewWebView () {
	    if(Build.VERSION.SDK_INT >= 19) {
	        webView.getSettings().setLoadsImagesAutomatically(true);
	    } else {
	        webView.getSettings().setLoadsImagesAutomatically(false);
	    }
	}

	@Override
	protected void onDestroy() {
		if (messenger != null) {
			try {
				messenger.send(new Message());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		webView.clearCache(true);
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
