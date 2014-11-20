package com.wi360.mobile.wallet.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.wi360.mobile.wallet.AddCardActivity;
import com.wi360.mobile.wallet.MainActivity;
import com.wi360.mobile.wallet.NewCardActivateActivity;
import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.adapter.CardManagerAdapter;
import com.wi360.mobile.wallet.base.BasePager;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.bean.CardDetailBean;
import com.wi360.mobile.wallet.bean.CardManagerBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.bean.ResultBean.CardList;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.EditTextChar;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;

/**
 * 卡片管理
 * 
 * @author Administrator
 * 
 */
public class CardManagerPager extends BasePager {
	/**
	 * 是否需要加载数据
	 */
	public static boolean isRepeat = true;

	public CardManagerPager(Context context) {
		super(context);
		isOnstart = true;
		// initData();
	}

	// // 卡片管理的布局id
	// @ViewInject(R.id.fl_content)
	// private FrameLayout fl_content;
	/**
	 * 判断initData方法是否在执行中,true正在执行
	 */
	private boolean isInitData = false;

	@ViewInject(R.id.ll_content)
	private LinearLayout ll_content;

	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	@ViewInject(R.id.ll_card_activate)
	private LinearLayout ll_card_activate;

	@ViewInject(R.id.et_card_number)
	private EditText et_card_number;

	private ListView lv;

	@Override
	public void onstart() {
		if (isOnstart && isRepeat) {
			initData();
			isRepeat = false;
		}
	}

	@Override
	public View initView() {
		View view = View.inflate(context, R.layout.layout_card_manager, null);
		ViewUtils.inject(this, view);
		// initTitleBar(view);
		txt_title.setText("卡片管理");
		//

		lv = new ListView(context);
		lv.setDivider(context.getResources().getDrawable(R.drawable.listview_split_line));
		lv.setDividerHeight(1);
		lv.setPadding(0, 0, 0, 30);
		ll_content.addView(lv);
		// 添加汇通卡,把该view添加到listView的底部
		View lv_footer = View.inflate(context, R.layout.item_layout_add_card, null);
		lv.addFooterView(lv_footer);
		// 添加卡片
		lv_footer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, AddCardActivity.class);
			}
		});

		return view;
	}

	@Override
	public void initData() {
		// 该方法是否正在执行
		if (isInitData) {
			return;
		} else {
			isInitData = true;
		}
		// 判断是否登录,如果没有登陆,adapter不显示任何数据
		if (!SharedPreferencesUtils.getBoolean(context, Constants.IS_LOGIN, false)) {
			return;
		}
		CardManagerBean bean = new CardManagerBean(context);
		String json = GsonTools.createGsonString(bean);
		// 获取卡片列表信息
		new MyAsyncTask<CardDetailBean>((MainActivity) context, "正在加载数据") {
			@Override
			public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
				// 连接成功
				if (responseInfo.statusCode == 200) {
					String json = responseInfo.result;
					resBean = GsonTools.changeGsonToBean(json, ResultBean.class);
					// List<CardList> lists = resBean.cardList;
				}
				return null;
			};

			protected void onPostExecute(String msg) {
				isInitData = false;
				super.onPostExecute(msg);
				List<CardList> lists = new ArrayList<ResultBean.CardList>();
				if (msg == null) {
					if (resBean != null && resBean.cardList != null) {
						lists = resBean.cardList;
						// 测试中暂时注释掉
					}
				}
				//放在外面的原因,只要是一等了,就能够帮到汇通卡
				lv.setAdapter(new CardManagerAdapter(context, lists));
			};

		}.execute(new String[] { Constants.FIND_BIND_LIST_URL, json });
	}

	@OnClick({ R.id.ll_card_activate, R.id.ib_clear })
	public void myOnClick(View v) {
		switch (v.getId()) {
		case R.id.ll_card_activate:// 跳转到新卡片激活页面
			ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context, NewCardActivateActivity.class);
			break;
		}

	}

}
