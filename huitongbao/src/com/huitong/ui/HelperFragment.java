package com.huitong.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class HelperFragment extends Fragment {
	private Context context;
	public HelperFragment(Context context){
		this.context=context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("Test", "onCreateView...");
		WebView webView=new  WebView(context);
		webView.loadUrl("http://www.baidu.com");
		return webView;
	}
}
