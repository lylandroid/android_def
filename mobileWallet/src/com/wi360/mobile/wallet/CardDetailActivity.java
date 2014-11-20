package com.wi360.mobile.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.bean.TransactionsDetailsBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;

/**
 * 汇通卡详情
 * 
 * @author Administrator
 * 
 */
public class CardDetailActivity extends BaseActivity {
	@ViewInject(R.id.et_money)
	private EditText et_money;

	@ViewInject(R.id.et_date)
	private EditText et_date;

	@ViewInject(R.id.rl_volume)
	private RelativeLayout rl_volume;

	private ResultBean resultBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		View view = View.inflate(context, R.layout.layout_card_detail, null);
		setContentView(view);
		ViewUtils.inject(context, view);

		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("汇通卡详情");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);

		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myOnKeyDown();
			}
		});
	}

	@Override
	public boolean initData() {
		Intent intent = getIntent();
		resultBean = (ResultBean) intent.getSerializableExtra("bean");
		// 没有任何数据
		if (resultBean == null) {
			return false;
		}

		et_money.setText(resultBean.balance + "元");
		// et_date.setText(new Date().getTime()+"");

		return false;
	}

	@OnClick(R.id.ll_transactions_desc)
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.ll_transactions_desc:
			loadTransactionsData();

			break;

		default:
			break;
		}
	}

	/**
	 * 加载交易明细,网络数据
	 */
	private void loadTransactionsData() {

		TransactionsDetailsBean transactionsDetailsBean = new TransactionsDetailsBean(context, resultBean.pan, "1");
		String json = GsonTools.createGsonString(transactionsDetailsBean);

		new MyAsyncTask<TransactionsDetailsBean>(context, "查询中") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				// 访问网络成功
				if (responseInfo.statusCode == 200) {
					// UIUtils.showToast(context, "成功");
					String resStr = responseInfo.result;
					resBean = GsonTools.changeGsonToBean(resStr, ResultBean.class);
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				if (resBean != null) {
					if (resBean.errcode == 0) {
						ActivityAnimationUtils
								.rightToLeftInAnimation(context, TransactionDetailActivity.class, resBean);
						msg = null;
					}
				}
				super.onPostExecute(msg);
			};
		}.execute(new String[] { Constants.GET_DETAIL_URL, json });

	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(CardDetailActivity.this, MainActivity.class);
		return true;
	}
}
