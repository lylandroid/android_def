package com.wi360.mobile.wallet.home;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.ButtomConfirmDialogActivity;
import com.wi360.mobile.wallet.MainActivity;
import com.wi360.mobile.wallet.PayActivity;
import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.base.BasePager;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.PayBean;
import com.wi360.mobile.wallet.bean.PhoneMomeyBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.CheckUtils;
import com.wi360.mobile.wallet.utils.CommonUtil;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.SystemUtils;
import com.wi360.mobile.wallet.utils.UIUtils;
import com.wi360.mobile.wallet.view.SingleDialog;

/**
 * 话费充值
 * 
 * @author Administrator
 * 
 */
public class MomeyRechargePager extends BasePager {

	private String TAG = "MomeyRechargePager";

	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	@ViewInject(R.id.item_layout_memoy_list)
	private LinearLayout item_layout_memoy_list;

	@ViewInject(R.id.et_phone_number)
	private EditText et_phone_number;

	@ViewInject(R.id.bt_submit)
	private Button bt_submit;

	@ViewInject(R.id.tv_momey)
	private TextView tv_momey;

	@ViewInject(R.id.tv_momey2)
	private TextView tv_momey2;

	private boolean isSubmit = false;

	public MomeyRechargePager(Context context) {
		super(context);
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.layout_recharge, null);
		ViewUtils.inject(this, view);

		txt_title.setText("话费充值");
		// System.out.println("dfjsakdfjd " + et_phone_number.isFocusable());
		return view;
	}

	@Override
	public void initData() {
		memeys = new ArrayList<PhoneMomeyBean>();
		memeys.add(new PhoneMomeyBean("30", "30.50"));
		memeys.add(new PhoneMomeyBean("50", "49.98"));
		memeys.add(new PhoneMomeyBean("100", "99.98", true));
		memeys.add(new PhoneMomeyBean("200", "199.60"));
		// if (dialog == null) {
		dialog = new SingleDialog(context, null, tv_momey, tv_momey2, memeys);
		// }
		// 初始化选择金额
		selectMomeyDialog(false);
	}

	/**
	 * 给选择的费用的layout添加事件
	 * 
	 * @param v
	 */
	@OnClick({ R.id.rl_item_menoy30, R.id.rl_item_menoy50, R.id.rl_item_menoy100, R.id.rl_item_menoy200 })
	public void myOnClick(View v) {
		switch (v.getId()) {
		}
	}

	/**
	 * 点击选择menoy的item
	 * 
	 * @param v
	 */
	boolean falg = true;
	boolean isAnimation = false;

	private SingleDialog dialog;

	private List<PhoneMomeyBean> memeys;

	/**
	 * 点击清除手机号码
	 * 
	 * @param v
	 */
	@OnClick({ R.id.ib_clear_number, R.id.rl_select_momey })
	public void clearPhone(View v) {
		switch (v.getId()) {
		case R.id.ib_clear_number:
			et_phone_number.setText("");
			break;
		case R.id.rl_select_momey:
			selectMomeyDialog(true);
			break;
		}

	}

	/**
	 * 调用弹出选择金额对话框
	 * 
	 * @param isShow
	 */
	private void selectMomeyDialog(boolean isShow) {

		if (dialog != null && !dialog.isShowing() && isShow) {
			dialog.show();
		}
	}

	/**
	 * 给立即充值button添加点击事件
	 */
	@OnClick(R.id.bt_submit)
	public void recharageButt(View v) {
		final String phone = et_phone_number.getText().toString();
		// 验证通过,电话号码格式正确
		if (CheckUtils.checkMobileNO((MainActivity) context, phone)) {
			// 是否在提交中
			if (isSubmit) {
				return;
			}
			isSubmit = true;
			// 检测是否是广东移动号码
			checkMobileNum(phone);

		} else {
			// 验证失败,电话号码格式错误
			// TODO Auto-generated method stub
		}
		// activity跳转到另外一个activity,并且执行一个动画
		// ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context,
		// CallsSuccessActivity.class);

	}

	/**
	 * 请求直接的服务器,返回给我url
	 * 
	 * @param phone
	 * @param momey
	 */
	private void connectNnetworkMoney(final String phone, String momey) {
		isSubmit = true;
		// momey = 10+"";
		PayBean payBean = new PayBean(context, phone, momey);
		String json = GsonTools.createGsonString(payBean);

		// 获取网络资源
		new MyAsyncTask<PayBean>((MainActivity) context, "充值中") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					if (responseInfo.result != null) {
						String resJson = responseInfo.result;
						resBean = GsonTools.changeGsonToBean(resJson, ResultBean.class);
						resBean.mobileNum = phone;
					}
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				isSubmit = false;
				// 银行返回的支付结果,不需要处理
				if (resBean != null) {
					if (resBean.errcode == 0) {
						ActivityAnimationUtils.rightToLeftInAnimation(context, PayActivity.class, resBean);
						et_phone_number.setText("");
					} else {
						msg = resBean.errmsg;
					}
				}
				super.onPostExecute(msg);
				// ActivityAnimationUtils.rightToLeftInAnimation(context,
				// PayActivity.class, resBean);
				// super.onPostExecute(null);
			};
		}.execute(new String[] { Constants.PHONE_PAY_URL, json });

	}

	/**
	 * 获取用户选择的充值金额
	 * 
	 * @return
	 */
	private String getSelectMomey() {
		// selectMomeyDialog(false);
		memeys = dialog.items;
		String momey = "100";
		if (memeys != null) {
			for (int i = 0; i < memeys.size(); i++) {
				if (memeys.get(i).isSelect) {
					momey = memeys.get(i).momey;
					break;
				}
			}
		}
		return momey;
	}

	/**
	 * 检测电话号码是否是广东移动
	 * 
	 * @param phone
	 * @return
	 */
	private String resultStr = null;

	public void checkMobileNum(final String phone) {
		if (CommonUtil.isNetworkAvailable(context) == 0) {// 没有
			UIUtils.showToast((Activity) context, "请检查网络");
			isSubmit = false;
			return;
		}
		Thread thread = new Thread() {
			public void run() {
				try {
					String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + phone;
					URL url2 = new URL(url);
					HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection();
					urlConnection.setRequestMethod("GET");// 设置请求的方式
					urlConnection.setReadTimeout(5000);// 设置超时的时间
					urlConnection.setConnectTimeout(5000);// 设置链接超时的时间
					// 设置请求的头
					urlConnection.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
					// 获取响应的状态码 404 200 505 302
					if (urlConnection.getResponseCode() == 200) {
						InputStream is = urlConnection.getInputStream();
						resultStr = SystemUtils.readStream(is);
					}
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			};
		};
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		boolean isMoblie = resultStr.contains("广东移动");
		Intent intent = new Intent(context, ButtomConfirmDialogActivity.class);
		// 是广东移动号码
		if (isMoblie) {
			// 测试金额暂时添加上
			final String momey = "1";
			String msg = "确认充值?\n";
			msg += "充值手机号码:" + phone + "\n";
			msg += ("充值金额:" + momey + "元");
			Messenger messenger = new Messenger(new Handler() {
				@Override
				public void handleMessage(Message msg) {
					connectNnetworkMoney(phone, momey);
				}
			});
			intent.putExtra("messenger", messenger);
			intent.putExtra("msg", msg);
			context.startActivity(intent);
		} else {
			String msg = "请输入广东移动号码";
			if (CommonUtil.isNetworkAvailable(context) == 0) {
				msg = "请检查网络";
			}
			intent.putExtra("messenger", new Messenger(new Handler()));
			intent.putExtra("msg", msg);
			context.startActivity(intent);
		}

		isSubmit = false;

	}
}
