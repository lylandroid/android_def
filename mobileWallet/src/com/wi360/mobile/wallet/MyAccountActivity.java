/*package com.wi360.mobile.wallet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wi360.mobile.wallet.view.QidaDialog;

*//**
 * 我的账户界面
 * 
 * @author hugo
 * 
 *//*
public class MyAccountActivity extends Activity implements OnMessageChangeListener, ReloadDataListener
{
	
	private ListView mAccountList;
	private MyAccountAdapter mAdapter;
	
	private ProgressBar mProgressBar;
	
	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			mProgressBar.setVisibility(View.GONE);
			switch (msg.what)
			{
				case Constant.FAIL:
				{
					break;
				}
				case Constant.SERVER_BUSY:
				{
					Toast.makeText(MyAccountActivity.this, MyAccountActivity.this.getString(R.string.network_error_tips), Toast.LENGTH_SHORT)
							.show();
					break;
				}
				case Constant.SUCCESS:
				{
					User user = UserManager.getInstance().getUser();
					List<CommonItem> items = mAdapter.getItems();
					items.get(1).setValue(user.getName());
					items.get(2).setValue(user.getMyAccount());
					items.get(3).setValue(user.getSexStr());
					
					items.get(5).setValue(user.getTitle());
					items.get(6).setValue(String.valueOf(user.getOrder()));
					
					items.get(8).setValue(String.valueOf(user.getMsgCount()));
					mAdapter.notifyDataSetChanged();
					break;
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_list);
		this.init();
		if (QidaUiUtil.isNetwokOkAndisLogin(this))
		{
			loadUserInfo();
		}
		
		UserManager.getInstance().addOnMessageChangeListener(this);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		ActivityManager.getInstance().setReloadDataListener(this);
		
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		MobclickAgent.onPause(this);
	}
	
	private void loadUserInfo()
	{
		mProgressBar.setVisibility(View.VISIBLE);
		UserManager.getInstance().refreshMyAccount(mHandler);
	}
	
	private void init()
	{
		UiUtil.enabledBackButton(this);
		TextView tv = (TextView) this.findViewById(R.id.topbar_title);
		tv.setText(this.getResources().getString(R.string.my_account));
		mAccountList = (ListView) this.findViewById(R.id.common_list);
		
		View footerView = this.getLayoutInflater()
				.inflate(R.layout.list_footer, null);
		Button footerBtn = (Button) footerView.findViewById(R.id.footer_btn);
		mAccountList.addFooterView(footerView);
		
		footerBtn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				
				final QidaDialog dialog = new QidaDialog(MyAccountActivity.this, R.style.QidaDialog);
				
				dialog.show();
				
				dialog.setTitle("退出提示");
				dialog.setContent("是否退出当前账号");
				
				dialog.setOnBtn("确定", new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
						QidaApplication.mainActivity.setTab(0, "ONE");
						UserManager.getInstance().exit();
						Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
						MyAccountActivity.this.finish();
						MyAccountActivity.this.startActivity(intent);
						String[] methods = CourseManager.getInstance()
								.getLearnCourseTypes();
						for (int i = 0; i < methods.length; i++)
						{
							try
							{
								CourseDao.getInstance()
										.deleteByType(methods[i]);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				});
				dialog.setCancleBtn("取消", new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
					}
				});
				
			}
		});
		
		mAdapter = new MyAccountAdapter(this, initItems());
		mAccountList.setAdapter(mAdapter);
		
		mProgressBar = (ProgressBar) this.findViewById(R.id.progress_bar);
	}
	
	private List<CommonItem> initItems()
	{
		List<CommonItem> items = new ArrayList<CommonItem>();
		CommonItem item = new CommonItem();
		item.setSpace(true);
		items.add(item);
		
		item = new CommonItem();
		item.setName("名字");
		items.add(item);
		
		item = new CommonItem();
		item.setName("账号");
		items.add(item);
		
		item = new CommonItem();
		item.setName("性别");
		items.add(item);
		
		item = new CommonItem();
		item.setSpace(true);
		items.add(item);
		
		item = new CommonItem();
		item.setName("头衔");
		items.add(item);
		
		item = new CommonItem();
		item.setName("综合排名");
		item.setNeedArrow(true);
		item.setTargetClass(RankActivity.class);
		items.add(item);
		
		item = new CommonItem();
		item.setSpace(true);
		items.add(item);
		
		item = new CommonItem();
		item.setName("我的消息");
		item.setTargetClass(MyMessageActivity.class);
		item.setNeedArrow(true);
		items.add(item);
		
		return items;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		UserManager.getInstance().removeOnMessageChangeListener(this);
		ActivityManager.getInstance().setReloadDataListener(null);
	}
	
	@Override
	public void onMessageChange()
	{
		mProgressBar.setVisibility(View.VISIBLE);
		UserManager.getInstance().refreshMyAccount(mHandler);
	}
	
	@Override
	public void reload()
	{
		loadUserInfo();
	}
	
}
*/