package com.itheima.mynews35;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * webView的activity
 * @author Administrator
 *
 */
public class DetailAct extends Activity implements OnClickListener {
	@ViewInject(R.id.news_detail_wv)
	private WebView web_view;
	private String path;
	@ViewInject(R.id.loading_view)
	private View loading_view;
	@ViewInject(R.id.btn_left)
	private Button btn_left;
	@ViewInject(R.id.btn_right)
	private ImageButton btn_right;
	@ViewInject(R.id.imgbtn_left)
	private ImageButton imgbtn_left;
	@ViewInject(R.id.imgbtn_text)
	private ImageButton imgbtn_text;
	private WebSettings mSettings;
	/**
	 * 设置字体大小
	 */
	private int fontSize;
	private WebSettings websettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String url = intent.getStringExtra("url");

		setContentView(R.layout.act_news_detail);
		ViewUtils.inject(this);

		btn_left.setVisibility(View.GONE);
		btn_right.setVisibility(View.GONE);
		imgbtn_left.setVisibility(View.VISIBLE);
		imgbtn_left.setImageResource(R.drawable.back);
		imgbtn_text.setVisibility(View.VISIBLE);
		imgbtn_text.setImageResource(R.drawable.icon_textsize);
		imgbtn_text.setOnClickListener(this);
		imgbtn_left.setOnClickListener(this);

		websettings = web_view.getSettings();
		
		web_view.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				loading_view.setVisibility(View.INVISIBLE);
			}
		});
		
//		web_view.loadUrl(url);
		System.out.println("web_view.loadUrl");
		web_view.loadUrl("http://192.168.2.39:8080/mwq/10002/724D6A55496A11726628.html");
		
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 改变字体大小
		case R.id.imgbtn_text:
			fontSize = (++fontSize) % 3;
			switch (fontSize) {
			case 0:
				websettings.setTextSize(TextSize.LARGER);
				break;
			case 1:
				websettings.setTextSize(TextSize.NORMAL);
				break;
			case 2:
				websettings.setTextSize(TextSize.SMALLER);
				break;
			}

			break;

		case R.id.imgbtn_left:// 关闭当前activity
			finish();
			break;
		}

	}
}
