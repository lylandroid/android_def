package com.wi360.mobile.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.BindCardBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.home.CardManagerPager;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.EditTextChar;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.StringUtils;
import com.wi360.mobile.wallet.utils.UIUtils;

/**
 * 添加汇通卡
 * 
 * @author Administrator
 * 
 */
public class AddCardActivity extends BaseActivity {
	private LinearLayout ib_back;

	@ViewInject(R.id.et_card_number)
	private EditText et_card_number;
	@ViewInject(R.id.et_pwd)
	private EditText et_pwd;

	private EditTextChar editTextCharacter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		View view = View.inflate(context, R.layout.layout_add_card, null);
		ViewUtils.inject(context, view);
		setContentView(view);
		editTextCharacter = new EditTextChar(et_card_number);
		//
		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("添加汇通卡");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);

		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myOnKeyDown();
			}
		});
	}

	@OnClick({ R.id.ib_clear_number, R.id.ib_clear_pwd, R.id.bt_submit })
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.ib_clear_number:
			et_card_number.setText("");
			break;
		case R.id.ib_clear_pwd:
			et_pwd.setText("");
			break;
		case R.id.bt_submit:
			// 弹出确认提交对话框
			Intent intent = new Intent(this, ButtomConfirmDialogActivity.class);
			intent.putExtra("msg", "确认添加");
			intent.putExtra("messenger", new Messenger(new Handler() {
				@Override
				public void handleMessage(Message msg) {
					mySubmit();
				}
			}));
			context.startActivity(intent);
			break;
		}
	}

	/**
	 * 点击提交按钮
	 */
	private void mySubmit() {
		// UIUtils.showProgressBar(context, null,
		// "正在提交数据");

		String cardNum = et_card_number.getText().toString().trim();
		String pwd = et_pwd.getText().toString().trim();
		if (StringUtils.isEmpty(cardNum)) {
			failureDialog(context, "请填写卡号");
			return;
		} else if (StringUtils.isEmpty(pwd)) {
			failureDialog(context, "请填写密码");
			return;
		}
		cardNum = EditTextChar.getEditTextReplace(cardNum);

		BindCardBean bean = new BindCardBean(context, cardNum, pwd);
		String json = GsonTools.createGsonString(bean);
		new MyAsyncTask<BindCardBean>(context, "绑定中") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				// 访问网络成功
				// {"errmsg":"认证失败","errcode":10018}
				if (responseInfo.statusCode == 200) {
					String resJson = responseInfo.result;
					resBean = GsonTools.changeGsonToBean(resJson, ResultBean.class);
					if (resBean.errcode == 0) {
						CardManagerPager.isRepeat = true;
						UIUtils.showToast(context, resBean.errmsg);
					} else {
						return resBean.errmsg;
					}
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				super.onPostExecute(msg);
			};
		}.execute(new String[] { Constants.BIND_CARD_URL, json });

	}

	@Override
	public boolean initData() {
		Intent intent = getIntent();
		ResultBean resultBean = (ResultBean) intent.getSerializableExtra("bean");
		// 没有任何数据
		if (resultBean == null) {
			return false;
		}
		et_card_number.setText(resultBean.pan);

		return true;
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(AddCardActivity.this, MainActivity.class);
		return false;
	}

}
