package com.creditpay;

import com.creditpay.client.GetCreditResp;
import com.creditpay.client.GetSmsResp;
import com.creditpay.client.LoginResp;
import com.creditpay.client.Order;
import com.creditpay.client.PayResp;
import com.creditpay.domain.OrderInfo;
import com.creditpay.ui.PayDialog;
import com.creditpay.ui.PayDialog.PayDialogListener;
import com.creditpay.util.Logger;
import com.creditpay.util.MD5Util;
import com.creditpay.util.PhoneUtil;
import com.creditpay.util.SPUtil;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Base64;
import android.widget.Toast;

public class CreditPayManager implements PayDialogListener {
	final static String ACTION = "com.creditpay.sendAction";
	private static CreditPayManager creditPayManager;
	private Context context;
	private CreditPayManagerCallBack callBack;
	private PayDialog payDialog;
	private boolean isOnPay;// true为有业务在支付中，false:允许支付
	private CreditPayLogic creditPayLogic;
	private String appId;
	private String productName;
	private int sum;// 金额
	private String alias;
	private String sellerUserId;
	private OrderInfo orderInfo;
	private int payModel = 1;// 1为联网模式，2为短信模式
	private int sendSmsIndex = 0, sendSmsTimeOutIndex;// 如果sendSmsIndex=sendSmsTimeOutIndex说明是当前支付，如果不一样不是当前支付
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:// 检测网络，回到当前页面
				checkWifi();
				break;
			case 1:// 发送获取认证码成功
				payDialog.hiddenLoginProgressView();
				Toast.makeText(context, "请求认证码成功，已发到您的手机上，请注意查收！",
						Toast.LENGTH_LONG).show();
				break;
			case 2:// 登录成功
				payDialog.hiddenLoginProgressView();
				checkMoney();
				break;
			case 3:// 支付成功
				payDialog.setPaySuccessView(orderInfo);
				break;
			case 4:// 获取信用数据成功
				int creditMoney = SPUtil.getCreditLimit(context);
				int usedCredit = SPUtil.getUsedCredit(context);
				if (sum > creditMoney - usedCredit) {
					// 如果使用额度加当前额度大于信用额度,去充值页面
					payDialog.setRechargeView(creditMoney - usedCredit);
				} else {
					payDialog.setLoadingView();
					pay();
				}
				break;
			case 100:// 获取认证码是失败
				payDialog.hiddenLoginProgressView();
				Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			case 101:// 登录失败
				payDialog.hiddenLoginProgressView();
				Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			case 102:// 支付是吧,通知上次应用
				payDialog.cancel();
				callBack.fail(msg.arg1, (String) msg.obj);
				isOnPay = false;
				Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			case 103:// 获取信用数据失败
				payDialog.cancel();
				callBack.fail(msg.arg1, (String) msg.obj);
				isOnPay = false;
				Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			case 104:// 发送短信失败，没有发送权限或者发送超时
				if(sendSmsIndex == sendSmsTimeOutIndex){
					sendSmsTimeOutIndex++;
					payDialog.cancel();
					callBack.fail(Constant.ERROR_CODE[4], Constant.ERROR_MSG[4]);
					isOnPay = false;
				}
				break;
			default:
				break;
			}
		};
	};

	// 如果对方接收到短信
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			if (arg1.getAction().equals(ACTION) && isOnPay
					&& sendSmsIndex == sendSmsTimeOutIndex) {// 发送短信成功
				sendSmsIndex++;
				switch (getResultCode()) {
				case Activity.RESULT_OK:// 发送成功，扣费成功，去成功页面
					orderInfo = new OrderInfo("***", 1, "", "", sum, "",
							"支付成功", "", 0, productName);
					payDialog.setPaySuccessView(orderInfo);
					break;
				default:// 发送短信失败
					payDialog.cancel();
					callBack.fail(Constant.ERROR_CODE[3], Constant.ERROR_MSG[3]);
					isOnPay = false;
					Toast.makeText(context, Constant.ERROR_MSG[3],
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	};

	private CreditPayManager() {
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		creditPayManager = new CreditPayManager();
		creditPayManager.context = context;
		creditPayManager.payDialog = new PayDialog(context, creditPayManager);
		creditPayManager.creditPayLogic = new CreditPayLogic();

		IntentFilter filter = new IntentFilter(ACTION);
		context.registerReceiver(creditPayManager.receiver, filter);
	}

	public static CreditPayManager getInstance() {
		return creditPayManager;
	}

	/**
	 */
	public void creditPay(String appId, String productName, int sum,
			String alias, String sellerUserId, CreditPayManagerCallBack callBack) {
		if (isOnPay) {// 如果有在支付中，不允许支付
			callBack.fail(Constant.ERROR_CODE[0], Constant.ERROR_MSG[0]);
			return;
		}
		this.callBack = callBack;
		this.appId = appId;
		this.productName = productName;
		this.sum = sum;
		this.alias = alias;
		this.sellerUserId = sellerUserId;
		payModel = 1;
		isOnPay = true;
		checkLogin();
	}

	// 检测网络和token
	private void checkWifi() {
		boolean isLinkWifi = PhoneUtil.CheckNetwork(context);
		if (!isLinkWifi) {// 如果没有连接wifi去选择模式界面
			payDialog.setChooseModelView();
			payDialog.show();
		} else {// 如果有wifi直接检测额度够不够
			payDialog.setLoadingView();
			payDialog.show();
			checkMoney();
		}
	}

	// 检测是否登录
	private void checkLogin() {
		String currentImsi = PhoneUtil.getImsi(context);
		String oldImsi = SPUtil.getOldImsi(context);
		if (oldImsi != null && oldImsi.equals(currentImsi)) {
			// 如果是有使用纪录的sim卡,判断token是否存在
			String token = SPUtil.getToken(context);
			if (token != null && !"".equals(token)) {
				// 如果存在token，判断是否有网络
				checkWifi();
			} else {
				// 不存在token去登录页面
				payDialog.setLoginView();
				payDialog.show();
			}
		} else {
			// 如果是新sim用户去登录页面
			payDialog.setLoginView();
			payDialog.show();
		}
	}

	// 检测钱是否够支付，不够去充值界面
	private void checkMoney() {
		int creditMoney = SPUtil.getCreditLimit(context);
		int usedCredit = SPUtil.getUsedCredit(context);
		if (sum > creditMoney - usedCredit) {
			// 如果使用额度加当前额度大于信用额度,调获取信用额度接口，获取信用额度
			if (payModel == 1) {// 网络模式
				getCreditInfo();
			} else {// 网络模式,去充值页面
				payDialog.setRechargeView(creditMoney - usedCredit);
			}

		} else {
			if (payModel == 1) {// 网络模式
				payDialog.setLoadingView();
				pay();
			} else {// 网络模式，发送短信
				payDialog.setLoadingView();
				String timestamp = (int) ((long) System.currentTimeMillis() / 1000)
						+ "";
				String nonce = creditPayLogic.getRandom();
				String signature = MD5Util.MD5(appId
						+ creditPayLogic.getKeyHash(context) + nonce
						+ timestamp);
				StringBuffer sb = new StringBuffer();

				sb.append("CP,")
						.append(appId + ",")
						.append(sum + ",")
						.append(SPUtil.getUsedCredit(context) + ",")
						.append(signature + ",")
						.append(alias + ",")
						.append(sellerUserId + ",")
						.append(Base64.encodeToString(productName.getBytes(),
								Base64.DEFAULT));
				sendSms(sb.toString());
			}

		}
	}

	// 支付
	private void pay() {
		new Thread() {
			public void run() {
				PayResp resp = creditPayLogic.pay(appId,
						SPUtil.getMobileNum(context), SPUtil.getToken(context),
						sum, alias, productName, sellerUserId, context);
				if (resp != null) {
					if (resp.errcode == Constant.RETURN_CODE) {
						// 支付成功
						Order order = resp.order;
						orderInfo = new OrderInfo(order.orderId,
								order.isDisplay, order.sellerName,
								order.appName, order.sum, order.payTime,
								order.status, order.buyerId, order.appId,
								productName);
						handler.sendEmptyMessage(3);
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = resp.errmsg;
						msg.arg1 = resp.errcode;
						msg.what = 102;
						handler.sendMessage(msg);
					}
				} else {
					Message msg = handler.obtainMessage();
					msg.obj = Constant.ERROR_MSG[2];
					msg.arg1 = Constant.ERROR_CODE[2];
					msg.what = 102;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	// 获取认证码
	private void getSmsCode(final String phone) {
		new Thread() {
			public void run() {
				GetSmsResp resp = creditPayLogic.getSms(appId, phone, context);
				if (resp != null) {
					if (resp.errcode == Constant.RETURN_CODE) {
						// 发送获取认证码成功
						handler.sendEmptyMessage(1);
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = resp.errmsg;
						msg.what = 100;
						handler.sendMessage(msg);
					}
				} else {
					Message msg = handler.obtainMessage();
					msg.arg1 = Constant.ERROR_CODE[2];
					msg.obj = Constant.ERROR_MSG[2];
					msg.what = 100;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	// 登录
	private void login(final String phone, final String code) {
		new Thread() {
			public void run() {
				LoginResp resp = creditPayLogic.login(appId, phone, code,
						context);
				if (resp != null) {
					if (resp.errcode == Constant.RETURN_CODE) {
						// 登录成功,保存token和imsi
						SPUtil.saveTokenAndImsi(context, resp.token,
								PhoneUtil.getImsi(context),
								resp.credit.creditLimit,
								resp.credit.usedCredit, phone);
						handler.sendEmptyMessage(2);
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = resp.errmsg;
						msg.what = 101;
						handler.sendMessage(msg);
					}
				} else {
					Message msg = handler.obtainMessage();
					msg.arg1 = Constant.ERROR_CODE[2];
					msg.obj = Constant.ERROR_MSG[2];
					msg.what = 101;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	// 获取信用额度详细
	private void getCreditInfo() {
		new Thread() {
			public void run() {
				GetCreditResp resp = creditPayLogic.getCredit(appId,
						SPUtil.getMobileNum(context), SPUtil.getToken(context),
						context);
				if (resp != null) {
					if (resp.errcode == Constant.RETURN_CODE) {
						// 登录成功,保存token和imsi
						SPUtil.saveCredit(context, resp.creditLimit,
								resp.usedCredit);
						handler.sendEmptyMessage(4);
					} else {
						Message msg = handler.obtainMessage();
						msg.obj = resp.errmsg;
						msg.what = 103;
						handler.sendMessage(msg);
					}
				} else {
					Message msg = handler.obtainMessage();
					msg.arg1 = Constant.ERROR_CODE[2];
					msg.obj = Constant.ERROR_MSG[2];
					msg.what = 103;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	// 发送短信
	private void sendSms(final String msg) {
		Logger.d("send sms...msg=" + msg);
		sendSmsIndex++;
		sendSmsTimeOutIndex = sendSmsIndex;
		new Thread() {
			public void run() {
				try {
					// 10秒短信没有发送出去，说明发送短信失败
					handler.sendEmptyMessageDelayed(104, 1000 * 10);

					SmsManager smsManager = SmsManager.getDefault();
					PendingIntent sentIntent = PendingIntent.getBroadcast(
							context, 0, new Intent(ACTION), 0);
					smsManager.sendTextMessage(Constant.UP_NUMBER, null, msg,
							sentIntent, null);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(104);
				}
			};
		}.start();
	}

	// 检测app是否在当前页面状态
	private void checkForeStatus() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000 * 4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!payDialog.isShowing()) {
						break;
					}
					if (context == null) {
						break;
					}
					ActivityManager am = (ActivityManager) context
							.getSystemService(Context.ACTIVITY_SERVICE);
					ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
					String packageName = cn.getPackageName();
					if (packageName != null
							&& packageName
									.equals(context.getApplicationInfo().packageName)) {
						// 如果是当前app
						Logger.d("on foreground...");
						handler.sendEmptyMessage(0);
						break;
					}
				}
			};
		}.start();

	}

	/**
	 * 注销
	 */
	public static void destroy() {
		if (creditPayManager != null) {
			creditPayManager.context
					.unregisterReceiver(creditPayManager.receiver);
		}
		creditPayManager = null;
	}

	public interface CreditPayManagerCallBack {
		public void success();

		public void fail(int errorCode, String errorMsg);
	}

	@Override
	public void cancel() {
		// 用户取消支付
		isOnPay = false;
		callBack.fail(Constant.ERROR_CODE[1], Constant.ERROR_MSG[1]);
	}

	@Override
	public void clickNetWorkModel() {
		// 打开联网模式
		payModel = 1;
		context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		payDialog.setLoadingView();
		checkForeStatus();
	}

	@Override
	public void clickSmsModel() {
		// 选择短信支付模式
		payModel = 2;
		checkMoney();
	}

	@Override
	public void clickGetSmsCode(String phone) {
		// 获取认证码
		getSmsCode(phone);
	}

	@Override
	public void clickLogin(String phone, String code) {
		// 请求登录
		login(phone, code);
	}

	@Override
	public void clickFinish() {
		// 支付成功
		payDialog.cancel();
		callBack.success();
		isOnPay = false;
	}

	@Override
	public void clickRecharge() {
		// 去充值页面
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setData(Uri.parse(Constant.RECHCARGE_URL));
		context.startActivity(intent);
		checkForeStatus();
	}
}
