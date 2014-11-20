package com.itheima.mynews35.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.itheima.mynews35.MainActivity;
import com.itheima.mynews35.R;

public class MenuFragment extends Fragment {
	private View menu;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView lv = (ListView) menu.findViewById(R.id.list_view);
		List<String> objects = new ArrayList<String>();
		objects.add("fragment1");
		objects.add("fragment2");
		objects.add("fragment3");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.test_list_item, android.R.id.text1, objects);

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Fragment fragment = null;
				switch (position) {
				case 0:
					fragment = new Fragment1();
					break;
				case 1:
					fragment = new Fragment2();
					break;
				case 2:
					fragment = new Fragment3();
					break;
				}
				repalaceFragment(fragment);
			}

			private void repalaceFragment(Fragment fragment) {
				if (fragment != null) {
					((MainActivity) getActivity()).replaceFragment(fragment);
				}
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		menu = inflater.inflate(R.layout.list_view, null);
		return menu;
	}

}
