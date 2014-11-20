package com.wi360.mobile.wallet.home;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.wallet.service.api.MocamProfile;
import com.cmcc.wallet.service.api.MocamRemoteService;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.ButtomConfirmDialogActivity;
import com.wi360.mobile.wallet.MainActivity;
import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.application.BaseApplication;
import com.wi360.mobile.wallet.application.SlaveClient;
import com.wi360.mobile.wallet.base.BasePager;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.ApplyWalletBean;
import com.wi360.mobile.wallet.bean.GetCardBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.utils.CheckIDCard;
import com.wi360.mobile.wallet.utils.CommonUtil;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.MyProgressAsyncTask;
import com.wi360.mobile.wallet.utils.StringUtils;
import com.wi360.mobile.wallet.utils.UIUtils;
import com.wi360.mobile.wallet.view.LoadDialog;
import com.wi360.mobile.wallet.view.MyDialog;
import com.wi360.mobile.wallet.view.QidaDialog;

/**
 * 钱包账户
 * 
 * @author Administrator
 * 
 */
public class HomePager extends BasePager {

	private static final String TAG = "HomePager";

	/** title头 */
	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	@ViewInject(R.id.fl_content)
	private FrameLayout fl_content;

	@ViewInject(R.id.bt_submit)
	private Button bt_submit;

	@ViewInject(R.id.et_idcard)
	private EditText et_idcard;

	@ViewInject(R.id.et_name)
	private EditText et_name;

	private MyDialog myDialog;
	private View view;

	public HomePager(Context context) {
		super(context);
	}

	private MocamRemoteService remoteService;

	private String remotePhone;

	@Override
	public View initView() {
		view = View.inflate(context, R.layout.layout_home_frame, null);
		ViewUtils.inject(this, view);

		txt_title.setText("申请汇通钱包账户");

		return view;
	}

	@Override
	public void initData() {
		// 测试取消
		if (true) {
			return;
		}
		SlaveClient slaveClient = BaseApplication.remoteClient;
		if (slaveClient != null) {
			remoteService = slaveClient.mRemoteService;
		}
		// 判断服务是否连接成功
		if (remoteService != null && remoteService.isConnected()) {
			remotePhone = remoteService.getProfile(MocamProfile.MOCAM_PROFILE_MSISDN);
			// 判断是否获取到电话号码,如果获取到
			if (remotePhone != null && remotePhone.length() > 0) {
				GetCardBean getBean = new GetCardBean(remotePhone);
				String json = GsonTools.createGsonString(getBean);
				// 获取钱包账户详情
				new MyAsyncTask<GetCardBean>((MainActivity) context, "加载中") {
					@Override
					public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
						if (responseInfo.statusCode == 200) {
							resBean = GsonTools.changeGsonToBean(responseInfo.result, ResultBean.class);
						}
						return null;
					}

					@Override
					protected void onPostExecute(String msg) {
						if (resBean != null) {
							// 成功
							if (resBean.errcode == 0) {
								registerSuccessUI(resBean);
								// 该手机号码没有汇通钱包账户
							} else if (resBean.errcode == 1031) {

							}
						}
						super.onPostExecute(msg);
					}
				};
			}
		}
	}

	@OnClick({ R.id.bt_submit, R.id.ib_clear_id, R.id.ib_clear_name })
	public void mySubmitOnClick(View v) {
		switch (v.getId()) {
		case R.id.bt_submit:
//			final String json = dataValidation();
//			if (json == null) {
//				return;
//			}
			Intent intent = new Intent(context, ButtomConfirmDialogActivity.class);
			intent.putExtra("msg", "确认提交?");
			intent.putExtra("messenger", new Messenger(new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// registerSuccessUI(null);
					// applyCard(json);
					findPayAccount();
				}

			}));
			context.startActivity(intent);

			break;
		case R.id.ib_clear_id:
			et_idcard.setText("");
			break;
		case R.id.ib_clear_name:
			et_name.setText("");
			break;
		}
	}

	/**
	 * 数据格式验证
	 */
	private String dataValidation() {
		String json = null;
		String idcard = ((TextView) view.findViewById(R.id.et_idcard)).getText().toString().trim();
		String etName = ((TextView) view.findViewById(R.id.et_name)).getText().toString().trim();
		// 检测身份证号码是否有效
		if (!CheckIDCard.checkIDCardValidate(context, idcard)) {
			json = null;
		} else if (StringUtils.isEmpty(etName)) {
			UIUtils.showToast((Activity) context, "请输入姓名");
			json = null;
		} else {
			// 测试暂时去掉
			// ApplyWalletBean walletBean = new ApplyWalletBean(etName, idcard,
			// remotePhone);
			ApplyWalletBean walletBean = new ApplyWalletBean(etName, idcard, "13048868272");
			json = GsonTools.createGsonString(walletBean);
			return json;
		}
		return json;

	}

	/**
	 * 申请汇通钱包账户
	 */
	private void applyCard(String json) {
		Log.i(TAG, json);
		new MyAsyncTask<ApplyWalletBean>((MainActivity) context, "申请中") {

			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				if (responseInfo.statusCode == 200) {
					resBean = GsonTools.changeGsonToBean(responseInfo.result, ResultBean.class);
				}
				return null;
			}

			@Override
			protected void onPostExecute(String msg) {
				if (resBean != null) {
					if (resBean.errcode == 0) {
						registerSuccessUI(resBean);
					} else {
						// 联系客服
						msg = resBean.errmsg;
					}
				}
				super.onPostExecute(msg);
			}
		}.execute(new String[] { Constants.APPLY_PAY_URL, json });

	}

	class FindPayAccount {
		public String mobileNum;

		public FindPayAccount(String mobileNum) {
			this.mobileNum = mobileNum;
		}
	}

	private void findPayAccount() {
		FindPayAccount payAccount = new FindPayAccount("13048868272");
		String json = GsonTools.createGsonString(payAccount);
		new MyAsyncTask<FindPayAccount>((Activity) context, null) {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				if(responseInfo.statusCode == 200){
					resBean = GsonTools.changeGsonToBean(responseInfo.result, ResultBean.class);
				}
				return null;
			}
		}.execute(new String[]{Constants.FIND_APPLY_PAY_DETAIL_URL,json});
	}

	/**
	 * 在申请汇通卡成功后改变的layout
	 */
	public void registerSuccessUI(ResultBean resBean) {
		fl_content.removeAllViews();
		bt_submit.setVisibility(View.GONE);
		fl_content.addView(View.inflate(context, R.layout.item_layout_account_success, null));
		EditText et_account_number = (EditText) fl_content.findViewById(R.id.et_account_number);
		EditText et_left_money = (EditText) fl_content.findViewById(R.id.et_left_money);
		EditText et_date = (EditText) fl_content.findViewById(R.id.et_date);
		if (resBean != null) {
			et_account_number.setText(resBean.account);
			et_left_money.setText(resBean.sum);
			et_date.setText(resBean.expiryDate);
		}
	}

}
