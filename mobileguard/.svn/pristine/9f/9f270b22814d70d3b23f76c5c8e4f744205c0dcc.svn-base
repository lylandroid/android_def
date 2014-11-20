package com.itheima.mobileguard.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.domain.ContactInfo;
import com.itheima.mobileguard.engine.ContactInfoParser;

public class SelectContactActivity extends Activity {
	private List<ContactInfo> infos;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		infos = ContactInfoParser.findAll(this);
		lv = (ListView) findViewById(R.id.lv_contacts);
		lv.setAdapter(new ContactsAdapter());
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent data = new Intent();
				data.putExtra("phone", infos.get(position).getPhone());
				setResult(0, data);
				finish();
			}
		});
	}

	private class ContactsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infos.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.item_contact, null);
			} else {
				view = convertView;
			}
			TextView tv_name = (TextView) view.findViewById(R.id.tv_item_name);
			TextView tv_phone = (TextView) view
					.findViewById(R.id.tv_item_phone);
			tv_name.setText(infos.get(position).getName());
			tv_phone.setText(infos.get(position).getPhone());
			return view;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

}
