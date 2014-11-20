package com.wi360.pay.sdk.base;

import com.wi360.pay.sdk.R;
import com.wi360.pay.sdk.R.id;
import com.wi360.pay.sdk.interfaces.ResponseCallback;
import com.wi360.pay.sdk.util.Constants;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * QidaDialog dialog = new QidaDialog(context,R.style.QidaDialog);
 * dialog.show(); dialog.setTitle("警告"); dialog.setContent("登陆失败");
 * dialog.setOnBtn("确定", new OnClickListener() {
 * 
 * @Override public void onClick(View v) {
 * 
 *           } }); dialog.setCancleBtn("取消", new OnClickListener() {
 * @Override public void onClick(View v) {
 * 
 *           } });
 * @author hugo
 * 
 */
public class QidaDialog extends Dialog {

	private Context context;
	public View view;
	public ResponseCallback responseCallback;

	public QidaDialog(Context context) {
		super(context);
		this.context = context;

	}

	public QidaDialog(Context context, int layout_id, int theme, ResponseCallback responseCallback) {
		super(context, theme);
		this.context = context;
		this.responseCallback = responseCallback;
		view = View.inflate(context, layout_id, null);

	}

	private TextView mTitle;
	private TextView mContent;
	private Button mOkBtn;
	private Button mCancleBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCancelable(false);
		// setCanceledOnTouchOutside(false);
		this.setContentView(view);
		this.mTitle = (TextView) view.findViewById(R.id.tv_title);
		// this.mContent = (TextView) this.findViewById(R.id.content);
		// this.mOkBtn = (Button) this.findViewById(R.id.dialog_button_ok);
		this.mCancleBtn = (Button) this.findViewById(R.id.bt_cancel);
	}

	public void setTitle(String title) {
		this.mTitle.setText(title);
		this.mTitle.setVisibility(View.VISIBLE);
	}

	public void setContent(String content) {
		this.mContent.setText(content);
		this.mContent.setVisibility(View.VISIBLE);
	}

	public void setOnBtn(String txt, android.view.View.OnClickListener onClickListener) {
		// this.mOkBtn.setText(txt);
		this.mOkBtn.setOnClickListener(onClickListener);
		this.mOkBtn.setVisibility(View.VISIBLE);
	}

	public void setCancleBtn(String txt, android.view.View.OnClickListener onClickListener) {
		// this.mCancleBtn.setText(txt);
		this.mCancleBtn.setOnClickListener(onClickListener);
		this.mCancleBtn.setVisibility(View.VISIBLE);
	}

	/**
	 * 用户点击取消button回调
	 */
	public void onClickCancleBut() {
		if (responseCallback != null) {
			responseCallback.responseStateCode(Constants.pay__code_user_cancel);
		}
	}
	/**
	 * 用户点击取消button回调
	 * @param code 状态码
	 */
	public void onClickCancleBut(int code) {
		if (responseCallback != null) {
			responseCallback.responseStateCode(code);
		}
	}

}
