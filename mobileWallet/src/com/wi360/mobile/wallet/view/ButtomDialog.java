package com.wi360.mobile.wallet.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 弹出选择充值金额对话框
 * 
 * @author Administrator
 * 
 */
public class ButtomDialog{

//	public ButtomDialog(Context context, ListView lv, final TextView tv_momey,
//			final TextView tv_momey2, final List<PhoneMomeyBean> items) {
//		super(context, R.style.NoBorderDialog);
//
//		// set
//
//		// setContentView(dialogLayout);
//		Window window = getWindow();
//		WindowManager.LayoutParams params = window.getAttributes();
//		params.width = LinearLayout.LayoutParams.MATCH_PARENT;
//		params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//		params.gravity = Gravity.BOTTOM;
//		window.setAttributes(params);
//
//	}

//	public ButtomDialog(Context context, int style) {
//		super(context, R.style.NoBorderDialog);
//	}

//	public ButtomDialog(Context context) {
//		super(context);
//	}
//
//	public void showDialog() {
//		this.show();
//	}

	// --------------------------------------------

	private AlertDialog dialog;

	public void showButtomDialog(Context context,String message) {
		AlertDialog.Builder builder = new Builder(context);
//		builder.setIcon(R.drawable.main_icon_36);
		builder.setCustomTitle(null);
		builder.setMessage(message);
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		dialog = builder.show();
		Window window = dialog.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = LinearLayout.LayoutParams.MATCH_PARENT;
		params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.BOTTOM;
		window.setAttributes(params);
	}

}
