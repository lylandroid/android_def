package com.wi360.mobile.wallet.home;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.AboutMyActivity;
import com.wi360.mobile.wallet.LoginActivity;
import com.wi360.mobile.wallet.MainActivity;
import com.wi360.mobile.wallet.MyVolumeActivity;
import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.UploadHeadImageActivity;
import com.wi360.mobile.wallet.base.BasePager;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.MyVolumeBean2;
import com.wi360.mobile.wallet.bean.MyVolumeListBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.bean.GetTokenBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.FileUtils;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.HttpUtils;
import com.wi360.mobile.wallet.utils.RoundHeadImageUtil;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;
import com.wi360.mobile.wallet.utils.UIUtils;
import com.wi360.mobile.wallet.utils.VersionUtils;
import com.wi360.mobile.wallet.utils.ZjhtClient2;

/**
 * 我的
 * 
 * @author Administrator
 * 
 */

public class MyPager extends BasePager {

	public static View view;

	/** title头 */
	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	/**
	 * 头像
	 */
	@ViewInject(R.id.iv_head)
	private ImageView iv_head;

	// 版本更新的工具类
	private VersionUtils versionUtils;

	@ViewInject(R.id.tv_login)
	private TextView tv_login;
	// 昵称
	@ViewInject(R.id.tv_show_nick_name)
	private TextView tv_show_nick_name;

	private String def_nick_name;

	// 消息处理器
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			versionUtils.handlerMessage((MainActivity) context, msg);
		};
	};

	public MyPager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.layout_my, null);
		ViewUtils.inject(this, view);
		// 获取默认登录昵称
		def_nick_name = context.getResources().getString(R.string.def_nick_name);
		txt_title.setText("我的");
		RoundHeadImageUtil.getRoundHeadImageUI((MainActivity) context, iv_head, true);
		tv_login.setText("登录");
		if (SharedPreferencesUtils.getBoolean(context, Constants.IS_LOGIN, false)) {
			tv_login.setVisibility(View.VISIBLE);
			tv_login.setText("退出");
		} else {
			tv_login.setVisibility(View.INVISIBLE);
		}
		tv_show_nick_name.setText(SharedPreferencesUtils.getString(context, Constants.SHOW_NICK_NAME, def_nick_name));

		return view;
	}

	@Override
	public void initData() {
	}

	@Override
	public void onstart() {
		// 检测是否有上传头像
		// if (SharedPreferencesUtils.getBoolean(context,
		// Constants.is_save_head_state_name, false)) {
		// // 圆形头像
		// RoundHeadImageUtil.getRoundHeadImageUI((MainActivity) context,
		// iv_head, true);
		//
		// SharedPreferencesUtils.saveBoolean((MainActivity) context,
		// Constants.is_save_head_state_name, false);
		// }
	}

	@OnClick({ R.id.rl_volume, R.id.rl_my, R.id.rl_version_update, R.id.iv_head, R.id.parent_login })
	public void myOnclick(View v) {
		switch (v.getId()) {

		case R.id.rl_volume:// 我的电子卷
			myVolume();

			break;
		case R.id.rl_my:// 关于我们
			ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, AboutMyActivity.class);
			break;
		case R.id.rl_version_update:// 版本更新
			versionUtils = new VersionUtils();
			// 获取当前app版本号
			int versionCode = versionUtils.getAppVersionNum(context);
			versionUtils.checkVersionUpdate((MainActivity) context, handler, versionCode);
			break;
		case R.id.iv_head:
			// 上传头像判断是否登录
			if (!SharedPreferencesUtils.getBoolean(context, Constants.IS_LOGIN, false)) {
				ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, LoginActivity.class);
				UIUtils.showToast((MainActivity) context, "你还没有登录");
			} else {
				ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, UploadHeadImageActivity.class);
			}
			break;
		case R.id.parent_login:
			if (!SharedPreferencesUtils.getBoolean(context, Constants.IS_LOGIN, false)) {
				ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, LoginActivity.class);
			}
			break;
		}
	}

//	/**
//	 * 我的电子券
//	 */
//	private void myVolume2() {
//		GetTokenBean testBean = new GetTokenBean(context);
//		String testJson = GsonTools.createGsonString(testBean);
//		new MyAsyncTask<ResultBean>((Activity) context, "test") {
//			@Override
//			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
//				System.out.println("connectNetWorkSuccess " + responseInfo.result);
//				return null;
//			}
//
//			protected void onPostExecute(String msg) {
//				super.onPostExecute(msg);
//				System.out.println("onPostExecute " + msg);
//			};
//		}.execute(new String[] { "http://115.29.249.236:8080/auth/user/get_info", testJson });
//	}

	/**
	 * 我的电子卷
	 */
	private void myVolume() {

		MyVolumeBean2 volumeBean = new MyVolumeBean2(context, 1 + "", 10 + "");
		String json = GsonTools.createGsonString(volumeBean);
		// json = volumeBean.getJson(context, json);
		final Map<String, String> map = volumeBean.getJson(context, json);
		new MyAsyncTask<MyVolumeListBean>((MainActivity) context, "查询中") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				String result = null;
				try {
					result = HttpUtils.URLPost(Constants.MY_VOLUME_LIST, map);
					resBean = GsonTools.changeGsonToBean(result, ResultBean.class);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				// 连接网络成功
				if (resBean != null) {
					// 查询成功
					if (resBean.content != null && resBean.content.records != null
							&& resBean.content.records.size() > 0) {
						ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, MyVolumeActivity.class,
								resBean);
						msg = null;
					} else {
						if (resBean.content != null && resBean.content.respMsg != null) {
							msg = resBean.content.respMsg;
						}
					}
				}
				super.onPostExecute(msg);
			};
		}.execute(new String[] {});
	}

	@OnClick(R.id.tv_login)
	public void login(View v) {
		// 登录情况下,退出登录
		if (SharedPreferencesUtils.getBoolean(context, Constants.IS_LOGIN, false)) {
			// 吧登录状态改为没有登录状态
			SharedPreferencesUtils.saveBoolean(context, Constants.IS_LOGIN, false);
			// 把保存的电话号码,清理掉
			SharedPreferencesUtils.saveString(context, Constants.SHOW_NICK_NAME, null);
			tv_login.setText("登录");
			tv_login.setVisibility(View.INVISIBLE);
			tv_show_nick_name.setText(def_nick_name);
			File headLockFile = RoundHeadImageUtil.getPhotoFilePath(context, true);
			// 删除本地头像文件
			FileUtils.delete(headLockFile);
			RoundHeadImageUtil.getRoundHeadImageUI((MainActivity) context, iv_head, true);
			UIUtils.showToast((MainActivity) context, "退出登录成功");
			SharedPreferencesUtils.clear(context);

			// 没有登录情况下,进入登录页面
		} else {
			ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, LoginActivity.class);
		}
	}

}
