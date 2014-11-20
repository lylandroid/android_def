package com.wi360.mobile.wallet.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wi360.mobile.wallet.R;

/**
 * QidaDialog dialog = new QidaDialog(context,R.style.QidaDialog);
 * dialog.show(); 
 * dialog.setTitle("警告"); 
 * dialog.setContent("登录失败");
 * dialog.setOnBtn("确定", new OnClickListener() {
 * 
 * @Override 
 * public void onClick(View v) {
 * 
 * } }); 
 * dialog.setCancleBtn("取消", new OnClickListener() {
 * @Override 
 * public void onClick(View v) {
 * 
 * } });
 * @author hugo
 * 
 */
public class QidaDialog extends Dialog {

	private Context context;

	public QidaDialog(Context context) {
		super(context);
		this.context = context;
	}

	public QidaDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	private TextView mTitle;
	private TextView mContent;
	private Button mOkBtn;
	private Button mCancleBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.qida_dialog);
		this.mTitle = (TextView) this.findViewById(R.id.title);
		this.mContent = (TextView) this.findViewById(R.id.content);
		this.mOkBtn = (Button) this.findViewById(R.id.dialog_button_ok);
		this.mCancleBtn = (Button) this.findViewById(R.id.dialog_button_cancle);
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
		this.mOkBtn.setText(txt);
		this.mOkBtn.setOnClickListener(onClickListener);
		this.mOkBtn.setVisibility(View.VISIBLE);
	}

	public void setCancleBtn(String txt, android.view.View.OnClickListener onClickListener) {
		this.mCancleBtn.setText(txt);
		this.mCancleBtn.setOnClickListener(onClickListener);
		this.mCancleBtn.setVisibility(View.VISIBLE);
	}

}
