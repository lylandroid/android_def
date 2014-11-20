package com.huitong.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("ValidFragment")
public class PurseFragment extends Fragment {
	private Context context;
	private WebView webView;

	public PurseFragment(Context context) {
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		webView = new WebView(context);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				if (!webView.getSettings().getLoadsImagesAutomatically()) {
					webView.getSettings().setLoadsImagesAutomatically(true);
				}
			}
		});
		webView.loadUrl("http://www.jd.com");
		return webView;
	}
}
