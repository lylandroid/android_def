package com.huitong.ui;

import com.huitong.huitongbao.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

@SuppressLint("ValidFragment")
public class ContactFragment extends Fragment {
	private Context context;
	public ContactFragment(Context context){
		this.context=context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.contact_layout, null, false);
		return view;
	}
}
