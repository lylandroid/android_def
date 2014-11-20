package com.huitong.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
	private Context context;
	private WebView webView;

	public HomeFragment(Context context) {
		this.context = context;
		webView = new WebView(context);
		WebSettings webSettings = webView.getSettings();
		// mWebView.getSettings().setAppCacheMaxSize(1024*1024*8);
		// String appCachePath =
		// getApplicationContext().getCacheDir().getAbsolutePath();
		// mWebView.getSettings().setAppCachePath(appCachePath);
		// mWebView.getSettings().setAllowFileAccess(true);
		// mWebView.getSettings().setAppCacheEnabled(true);
		webSettings.setDatabaseEnabled(true);
		String dir = context.getCacheDir().getAbsolutePath();

		// 启用地理定位
		webSettings.setGeolocationEnabled(true);
		// 设置定位的数据库路径
		webSettings.setGeolocationDatabasePath(dir);

		// 最重要的方法，一定要设置，这就是出不来的主要原因

		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				if (!webView.getSettings().getLoadsImagesAutomatically()) {
					webView.getSettings().setLoadsImagesAutomatically(true);
				}
			}
		});
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}
			
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// webView.loadUrl("file:///android_asset/index.html");
//		webView.loadUrl("http://115.29.7.155:8000/index/liantong/zjht.html");
		webView.loadUrl("http://115.29.7.155:8000/index/liantong/loadding.html");
		return webView;
	}

	public WebView getWebView() {
		return webView;
	}

}
