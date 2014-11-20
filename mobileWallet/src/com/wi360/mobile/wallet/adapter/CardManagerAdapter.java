package com.wi360.mobile.wallet.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wi360.mobile.wallet.AddCardActivity;
import com.wi360.mobile.wallet.CardDetailActivity;
import com.wi360.mobile.wallet.MainActivity;
import com.wi360.mobile.wallet.R;
import com.wi360.mobile.wallet.base.MyAsyncTask;
import com.wi360.mobile.wallet.base.MyBaseAdapter;
import com.wi360.mobile.wallet.bean.CardDetailBean;
import com.wi360.mobile.wallet.bean.ResultBean;
import com.wi360.mobile.wallet.bean.ResultBean.CardList;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.EditTextChar;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.UIUtils;

/**
 * 卡片管理中listView的适配器
 * 
 * @author Administrator
 * 
 */
public class CardManagerAdapter extends MyBaseAdapter<CardList, ListView> {

	private TextView title;
	private TextView cardNum;
	private EditTextChar editTextChar;

	public CardManagerAdapter(Context context, List<CardList> lists) {
		super(context, lists);
		editTextChar = new EditTextChar(null);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_layout_card_list, null);
		}

		title = (TextView) convertView.findViewById(R.id.tv_title);
		cardNum = (TextView) convertView.findViewById(R.id.tv_card_number);
		title.setText(lists.get(position).name);
		cardNum.setText(lists.get(position).pan);

		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 汇通卡详情
				String pan = editTextChar.getEditTextReplace(lists.get(position).pan);
				CardDetailBean detailBean = new CardDetailBean(context, pan);
				String json = GsonTools.createGsonString(detailBean);
				// 连接网络,获取数据
				new MyAsyncTask<CardDetailBean>((MainActivity) context, "查询中") {

					@Override
					public String connectNetWorkSuccess(ResponseInfo<String> responseInfo) {
						String msg = null;
						// 连接网络成功
						if (responseInfo.statusCode == 200) {
							String resJson = responseInfo.result;
							// 把返回的数据封装到javaBean中
							resBean = GsonTools.changeGsonToBean(resJson, ResultBean.class);
							// 查询成功跳转到,详情页面
							if (resBean.errcode == 0) {
								// isSuccess = 0;
							} else {
								// isSuccess = 1;
								// 如果token失效
								resBean.pan = lists.get(position).pan;
								msg = resBean.errmsg;
							}
						}
						return msg;
					};

					protected void onPostExecute(String msg) {
						// 若果连接成功resBean不等于null
						if (resBean != null) {
							if (resBean.errcode == 0) {
								ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context,
										CardDetailActivity.class, resBean);
								UIUtils.showToast(context, resBean.errmsg);
								msg = null;
							} else {
								ActivityAnimationUtils.rightToLeftInAnimation((MainActivity) context,
										AddCardActivity.class, resBean);
								msg = resBean.errmsg;
							}
						}
						// 连接失败
						super.onPostExecute(msg);
						// // super.onPostExecute(msg);
						// // 失败
						// if (isSuccess == -1) {
						// // 连接成功,返回错误数据
						// } else if (isSuccess == 1) {
						// // 正常
						// ActivityAnimationUtils.rightToLeftInAnimation((MainActivity)
						// context,
						// AddCardActivity.class, resBean);
						// } else if (isSuccess == 0) {
						// // 跳转到CardDetailActivity,并且执行动画
						// ActivityAnimationUtils.rightToLeftInAnimation((MainActivity)
						// context,
						// CardDetailActivity.class, resBean);
						// }
						// super.onPostExecute(null);
					};

				}.execute(new String[] { Constants.FIND_CARD_DETAIL_URL, json });

			}
		});

		return convertView;
	}
}
