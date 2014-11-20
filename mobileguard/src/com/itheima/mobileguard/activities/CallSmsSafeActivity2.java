package com.itheima.mobileguard.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.mobileguard.R;
import com.itheima.mobileguard.db.dao.BlackNumberDao;
import com.itheima.mobileguard.domain.BlackNumberInfo;

public class CallSmsSafeActivity2 extends Activity {
	private ListView lv_callsms_safe;
	private LinearLayout ll_add_number_tips;
	private LinearLayout ll_loading;
	/**
	 * 页面输入框
	 */
	private EditText et_page_number;
	
	/**
	 * 页码信息
	 */
	private TextView tv_page_info;
	
	/**
	 * 页面大小
	 */
	private static final int pageSize = 20;
	/**
	 * 当前页码号
	 */
	private int curentPgeNumber = 0;
	/**
	 * 一共有多少页
	 */
	private int totalPage = 0;
	
	private BlackNumberDao dao;
	private List<BlackNumberInfo> infos;
	/**
	 * 消息处理器
	 */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			ll_loading.setVisibility(View.INVISIBLE);
			if (infos.size() == 0) {
				// 没有数据，设置添加数据的提醒
				ll_add_number_tips.setVisibility(View.VISIBLE);
			} else {
				lv_callsms_safe.setAdapter(new CallSmsSafeAdapter());
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initUI();
		fillData();
	}


	/**
	 * 填充数据
	 */
	private void fillData() {
		dao = new BlackNumberDao(this);
		//数据库的总条目个数 /  每个页面最多显示多少条数据
		totalPage  =  dao.getTotalNumber()/pageSize;
		tv_page_info.setText(curentPgeNumber+"/"+totalPage);
		//耗时的操作 逻辑应该放在子线程里面执行。
		ll_loading.setVisibility(View.VISIBLE);
		new Thread(){
			public void run() {
				infos = dao.findPart(curentPgeNumber, pageSize);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
	
	
	/**
	 * 初始化ui的逻辑块
	 */
	private void initUI() {
		setContentView(R.layout.activity_callsms_safe);
		ll_add_number_tips = (LinearLayout) findViewById(R.id.ll_add_number_tips);
		ll_loading = (LinearLayout) findViewById(R.id.ll_loading);
		lv_callsms_safe = (ListView) findViewById(R.id.lv_callsms_safe);
		et_page_number = (EditText) findViewById(R.id.et_page_number);
		tv_page_info = (TextView) findViewById(R.id.tv_page_info);
	}

	private class CallSmsSafeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infos.size();
		}

		//这个方法要被执行很多次， 有多个条目 就要执行多少次
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			System.out.println("getview:"+position);
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(CallSmsSafeActivity2.this,
						R.layout.item_callsms, null);
				holder = new ViewHolder(); //减少子孩子查询的次数
				holder.tv_phone = (TextView) view
						.findViewById(R.id.tv_item_phone);
				holder.tv_mode = (TextView) view.findViewById(R.id.tv_item_mode);
				//把孩子id的引用 存放在holder里面，设置给父亲 view
				view.setTag(holder);
			} else {
				view = convertView; //使用历史缓存view对象， 减少view对象被创建的次数
				holder = (ViewHolder) view.getTag();
			}
			
			BlackNumberInfo info = infos.get(position);
			holder.tv_phone.setText(info.getNumber());
			// 1 全部拦截 2 短信拦截 3 电话拦截
			String mode = info.getMode();
			if ("1".equals(mode)) {
				holder.tv_mode.setText("全部拦截");
			} else if ("2".equals(mode)) {
				holder.tv_mode.setText("短信拦截 ");
			} else if ("3".equals(mode)) {
				holder.tv_mode.setText("电话拦截 ");
			}
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
	
	/**
	 * 家庭组 view对象的容器
	 * @author Administrator
	 *
	 */
	class ViewHolder{
		TextView tv_phone;
		TextView tv_mode;
	}
	
	/**
	 * 上一页
	 * @param view
	 */
	public void prePage(View view){
		if(curentPgeNumber<=0){
			Toast.makeText(this, "已经是第一页", 0).show();
			return;
		}
		curentPgeNumber--;
		fillData();
	}
	
	/**
	 * 下一页
	 * @param view
	 */
	public void nextPage(View view){
		if(curentPgeNumber>(totalPage-1)){
			Toast.makeText(this, "已经是最后一页", 0).show();
			return;
		}
		curentPgeNumber++;
		fillData();
	}
	
	/**
	 * 跳转
	 * @param view
	 */
	public void jump(View view){
		String str_pagenumber = et_page_number.getText().toString().trim();
		if(TextUtils.isEmpty(str_pagenumber)){
			Toast.makeText(this, "请输入页面号", 0).show();
		}else{
			int number = Integer.parseInt(str_pagenumber);
			if(number>=0&&number<totalPage){
				curentPgeNumber=number;
				fillData();
			}else{
				Toast.makeText(this, "请输入正确的页面号", 0).show();
			}
		}
	}
}
