package com.wi360.mobile.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;

public class CallsSuccessActivity extends BaseActivity {

	// 支付状态
	@ViewInject(R.id.tv_pay_status)
	private TextView tv_pay_status;
	@ViewInject(R.id.tv_momey)
	private TextView tv_momey;
	@ViewInject(R.id.tv_order_id)
	private TextView tv_order_id;
	@ViewInject(R.id.tv_pay_time)
	private TextView tv_pay_time;

	@ViewInject(R.id.tv_pay_status2)
	private TextView tv_pay_status2;
	@ViewInject(R.id.tv_product_name)
	private TextView tv_product_name;

	private ResultBean resultBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		View view = View.inflate(context, R.layout.layout_calls_success, null);
		ViewUtils.inject(this, view);
		setContentView(view);
		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("话费充值");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);

		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 销毁当前activity,并执行动画
				myOnKeyDown();
			}
		});

		Intent intent = getIntent();
		if (intent.getExtras().get("bean") != null) {
			resultBean = (ResultBean) intent.getExtras().get("bean");
			if (resultBean.order != null) {
				tv_order_id.setText(resultBean.order.orderId);
				tv_pay_time.setText(resultBean.order.payTime);
				tv_product_name.setText(resultBean.order.productName);
				tv_pay_status.setText(resultBean.order.status);
				tv_pay_status2.setText(resultBean.order.status);
				tv_momey.setText(resultBean.order.sum + "元");
			}
		} else {
			finish();
		}
	}

	@Override
	public boolean initData() {
		return false;
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(CallsSuccessActivity.this, MainActivity.class);
		return true;
	}

}
