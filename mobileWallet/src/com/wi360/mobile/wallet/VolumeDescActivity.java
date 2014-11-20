package com.wi360.mobile.wallet;

import java.io.IOException;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.MyVolumeTransferBean;
import com.wi360.mobile.wallet.bean.MyVolumeTransferBean2;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.CheckUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.HttpUtils;
import com.wi360.mobile.wallet.utils.UIUtils;

/**
 * 我的电子卷
 * 
 * @author Administrator
 * 
 */
public class VolumeDescActivity extends BaseActivity {

	protected static final String TAG = "VolumeDescActivity";

	@ViewInject(R.id.et_phone_number)
	private EditText et_phone_number;

	// 电子劵号码
	@ViewInject(R.id.volume_number)
	private TextView volume_number;

	@ViewInject(R.id.iv_er_wei_ma)
	private ImageView iv_er_wei_ma;

	private ResultBean resultBean;
	/**
	 * 是否正在提交数据
	 */
	private boolean isSubmit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();

	}

	@Override
	public void initView() {
		View view = View.inflate(context, R.layout.layout_volume_desc, null);
		ViewUtils.inject(context, view);
		setContentView(view);
		//
		TextView txt_title = (TextView) findViewById(R.id.txt_title);
		txt_title.setText("电子卷详情");
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
		// 获取数据
		resultBean = (ResultBean) intent.getExtras().getSerializable("bean");
		// 如果查询出来有数据
		if (resultBean != null && resultBean.content != null) {
			volume_number.setText(resultBean.content.assisCode);
			// 根据路径判断是否加载二维码图片
			if (resultBean.content.qrcodePicFileId != null && resultBean.content.qrcodePicFileId.length() > 0) {
				BitmapUtils bitmapUtils = new BitmapUtils(this);
				// 加载网络图片
				bitmapUtils.display(iv_er_wei_ma, resultBean.content.qrcodePicFileId);
			}
		}

		return false;
	}

	@OnClick(R.id.bt_submit)
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.bt_submit:
			btSubmit();
			break;
		}
	}

	/**
	 * 点击转让按钮,执行转让操作
	 */
	private void btSubmit() {
		if (resultBean == null || isSubmit) {
			return;
		}
		String phone = et_phone_number.getText().toString().trim();
		// 验证手机号码格式是否正确
		if (!CheckUtils.checkMobileNO(context, phone)) {
			return;
		}
		MyVolumeTransferBean transferBean = new MyVolumeTransferBean(context, resultBean.content.assisCode, phone);
		String json = GsonTools.createGsonString(transferBean);
		final Map<String, String> map = transferBean.getJson(context, json);
		isSubmit = true;
		// 连接网络
		new MyAsyncTask<MyVolumeTransferBean>(context, "转让中") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				String result = null;
				try {

					Log.i(TAG, map.toString());
					result = HttpUtils.URLPost(Constants.MY_VOLUME_TRANSFER, map);
					resBean = GsonTools.changeGsonToBean(result, ResultBean.class);
				} catch (IOException e) {
					e.printStackTrace();
					return "失败";
				}
				return null;
			}

			protected void onPostExecute(String msg) {
				// 连接网络成功
				if (resBean != null && resBean.content!=null) {
					// 返回正确的数据
					if (resBean.content.respCode != null) {
						int respCode = 2;
						try {
							respCode = Integer.valueOf(resBean.content.respCode);
							if(respCode == 0){
								UIUtils.showToast(context, resBean.content.respMsg);
								msg = null;
							}else{
								msg = resBean.content.respMsg;
							}
						} catch (NumberFormatException e) {
							msg = resBean.content.respMsg;
							e.printStackTrace();
						}
					}
				}
				isSubmit = false;
				super.onPostExecute(msg);
			};
		}.execute(new String[] {});

	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(VolumeDescActivity.this, MyVolumeActivity.class);
		return true;
	}

}
