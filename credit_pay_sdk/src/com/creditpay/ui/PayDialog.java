package com.creditpay.ui;

import java.util.List;

import com.creditpay.domain.OrderInfo;
import com.creditpay.ui.Step1View.Step1ViewListener;
import com.creditpay.ui.Step2View.Step2ViewListener;
import com.creditpay.ui.Step3View.Step3ViewListener;
import com.creditpay.ui.Step4View.Step4ViewListener;
import com.creditpay.ui.Step5View.Step5ViewListener;
import com.creditpay.ui.Step6View.Step6ViewListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PayDialog extends Dialog implements Step1ViewListener,
		Step2ViewListener, Step3ViewListener, Step4ViewListener,
		Step5ViewListener, Step6ViewListener {
	private String TAG = PayDialog.class.getSimpleName();
	private PayDialogListener payDialogListener;
	private int width;
	private Activity context;
	private Step1View step1View;
	private Step2View step2View;
	private Step3View step3View;
	private Step4View step4View;
	private Step5View step5View;
	private Step6View step6View;
	private ProgressView progressView;
	private long clickStartTime;// 点击退出按钮开始时间
	private boolean canClickBack=true;
	public PayDialog(Context context, PayDialogListener payDialogListener) {
		super(context);
		// 设置对话框属性
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setCancelable(false);
		this.context = (Activity) context;
		this.payDialogListener = payDialogListener;
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int w = dm.widthPixels;
		int h = dm.heightPixels;
		if (w < h) {
			width = (int) (w * 0.8f);
		} else {
			width = (int) (h * 0.8f);
		}

		step1View = new Step1View(context, width, this);
		step2View = new Step2View(context, width, this);
		step3View = new Step3View(context, width, this);
		step4View = new Step4View(context, width, this);
		step5View = new Step5View(context, width, this);
		step6View = new Step6View(context, width, this);
		progressView = new ProgressView(context, width);
		setContentView(step1View);
	}
	
	@Override
	public void show() {
		canClickBack=true;
		super.show();
	}

	// 设置为加载界面
	public void setLoadingView() {
		setContentView(progressView);
	}
	
	//支付成功界面
	public void setPaySuccessView(OrderInfo orderInfo){
		canClickBack=false;
		step3View.showData(orderInfo);
		setContentView(step3View);
	}
	
	//设置到登录页面
	public void setLoginView(){
		step4View.hiddenProgressView();
		setContentView(step4View);
	}
	
	//显示充值界面
	public void setRechargeView(int blance){
		step2View.setBlance(blance);
		setContentView(step2View);
	}

	// 设置为选择模式界面
	public void setChooseModelView() {
		setContentView(step5View);
	}
	
	//隐藏progressView
	public void hiddenLoginProgressView(){
		step4View.hiddenProgressView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(!canClickBack){
				return false;
			}
			long endTime = System.currentTimeMillis();
			if (endTime - clickStartTime > 1000 * 2) {
				clickStartTime = endTime;
				Toast.makeText(context, "再次点击退出支付!", Toast.LENGTH_SHORT).show();
			} else {
				payDialogListener.cancel();
				cancel();
			}

			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	

	@Override
	public void clickPayNow(Step1View step1View) {
		setContentView(step2View);
	}

	@Override
	public void clickRechargeNow(Step2View step2View) {
		//点击确认充值按钮
		payDialogListener.clickRecharge();
	}

	@Override
	public void clickFinish(Step3View step3View) {
		//支付成功，点击确认按钮
		payDialogListener.clickFinish();
	}

	@Override
	public void clickGetSmsCode(String phone) {
		// TODO Auto-generated method stub
		payDialogListener.clickGetSmsCode(phone);
	}

	@Override
	public void clickLogin(Step4View step4View, String phone, String code) {
		// TODO Auto-generated method stub
		payDialogListener.clickLogin(phone, code);
	}

	@Override
	public void clickSure(Step6View step6View) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clickCancel(Step6View step6View) {
		// TODO Auto-generated method stub

	}

	public interface PayDialogListener {
		public void cancel();

		public void clickNetWorkModel();

		public void clickSmsModel();
		
		public void clickGetSmsCode(String phone);
		
		public void clickLogin(String phone,String code);
		
		public void clickFinish();
		
		public void clickRecharge();
	}

	@Override
	public void clickNetWorkModel(Step5View step5View) {
		// 打开联网模式
		payDialogListener.clickNetWorkModel();
	}

	@Override
	public void clickSmsModel(Step5View step5View) {
		// 选择短信支付模式
		payDialogListener.clickSmsModel();
	}

}
