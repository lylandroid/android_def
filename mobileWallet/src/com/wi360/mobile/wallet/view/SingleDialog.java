package com.wi360.mobile.wallet.view;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.base.MyBaseAdapter;
import com.wi360.mobile.wallet.bean.PhoneMomeyBean;

/**
 * 弹出选择充值金额对话框
 * 
 * @author Administrator
 * 
 */
public class SingleDialog extends Dialog {

	public List<PhoneMomeyBean> items;
	public SingleDialog(Context context, ListView lv, final TextView tv_momey,
			final TextView tv_momey2, List<PhoneMomeyBean> itemsTemp) {
		super(context, R.style.NoBorderDialog);
		this.items = itemsTemp;
		LinearLayout dialogLayout = (LinearLayout) View.inflate(context,
				R.layout.dialog_layout_single, null);
		lv = (ListView) dialogLayout.findViewById(R.id.lv_single_dialog);
		lv.setSelector(R.drawable.listview_item_selector);
		lv.setDivider(null);
		MyAdapter adapter = new MyAdapter(context, items);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			int index = 2;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				tv_momey.setText(items.get(position).toString());
				tv_momey2.setText(items.get(position).momey2+"元 ");
				
				items.get(index).isSelect = false;
				items.get(position).isSelect = true;

				parent.getChildAt(index).findViewById(R.id.ib_cb)
						.setSelected(items.get(index).isSelect);
				parent.getChildAt(position).findViewById(R.id.ib_cb)
						.setSelected(items.get(position).isSelect);
				index = position;
				SingleDialog.this.dismiss();

			}
		});

		setContentView(dialogLayout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
		params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);

	}

	public SingleDialog(Context context, int style) {
		super(context, R.style.NoBorderDialog);
	}

	public SingleDialog(Context context) {
		super(context);
	}

	public void showDialog() {
		this.show();
	}

	class MyAdapter extends MyBaseAdapter<PhoneMomeyBean, View> {
		private TextView tv_momey_desc;
		private ImageButton cb;

		public MyAdapter(Context context, List<PhoneMomeyBean> lists) {
			super(context, lists);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.item_layout_raido,
						null);
			}
			tv_momey_desc = (TextView) convertView
					.findViewById(R.id.tv_momey_desc);
			tv_momey_desc.setText(lists.get(position).toString());
			cb = (ImageButton) convertView.findViewById(R.id.ib_cb);
			cb.setSelected(lists.get(position).isSelect);
			return convertView;
		}

	};
}
