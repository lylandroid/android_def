package com.example.googleplay35.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.googleplay35.R;
import com.example.googleplay35.protocol.RecommendProtocol;
import com.example.googleplay35.utils.UIUtils;
import com.mwqi.ui.widget.StellarMap;
import com.mwqi.ui.widget.LoadingPage.LoadResult;
import com.mwqi.ui.widget.StellarMap.Adapter;

public class RecommendFragment extends BaseFragment {


	private List<String> mDatas;
	private StellarMap stellarMap;

	@Override
	protected LoadResult load() {
		RecommendProtocol protocol = new RecommendProtocol();
		mDatas = protocol.load(0);
		return check(mDatas);
	}

	@Override
	public View loadView() {
		stellarMap = new StellarMap(UIUtils.getContext());
		stellarMap.setInnerPadding(20, 20, 20, 20);
		stellarMap.setRegularity(6, 9);
		StellarMapAdapter adapter = new StellarMapAdapter();
		stellarMap.setAdapter(adapter);
		return stellarMap;
	}
	
	public class StellarMapAdapter implements Adapter{

		
		
		private Random mRandom;

		public StellarMapAdapter() {
			mRandom = new Random();
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public int getCount(int group) {
			// TODO Auto-generated method stub
			return 15;
		}

		@Override
		public View getView(int group, int position, View convertView) {
			TextView textView = new TextView(UIUtils.getContext());
			
			int red = 30 + mRandom.nextInt(200);
			int green = 30 + mRandom.nextInt(200);
			int blue = 30 + mRandom.nextInt(200);
			
			int colors = Color.rgb(red, green, blue);
			textView.setTextColor(colors);
			textView.setTextSize(10 + mRandom.nextInt(10));
			textView.setText(mDatas.get(position));
			return textView;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree) {
			// TODO Auto-generated method stub
			return (group + 1)%2;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			// TODO Auto-generated method stub
			return (group + 1)%2;
		}
		
	}

}
