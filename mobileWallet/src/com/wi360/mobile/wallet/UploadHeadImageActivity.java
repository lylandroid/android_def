package com.wi360.mobile.wallet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wi360.mobile.wallet.base.BaseActivity;
import com.wi360.mobile.wallet.bean.UpdateUserInfoBean;
import com.wi360.mobile.wallet.home.MyPager;
import com.wi360.mobile.wallet.interfaces.MyRequestCallBack;
import com.wi360.mobile.wallet.utils.ActivityAnimationUtils;
import com.wi360.mobile.wallet.utils.BitMapUtils;
import com.wi360.mobile.wallet.utils.Constants;
import com.wi360.mobile.wallet.utils.GsonTools;
import com.wi360.mobile.wallet.utils.MyHttpUtils;
import com.wi360.mobile.wallet.utils.RoundHeadImageUtil;
import com.wi360.mobile.wallet.utils.SharedPreferencesUtils;
import com.wi360.mobile.wallet.utils.StringUtils;
import com.wi360.mobile.wallet.utils.UIUtils;
import com.wi360.mobile.wallet.view.LoadDialog;

/**
 * 上传头像
 * 
 * @author Administrator
 * 
 */
public class UploadHeadImageActivity extends BaseActivity implements OnClickListener {

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	// 创建一个以当前时间为名称的文件
	private Bitmap photoBitmap;
	/** title头 */
	@ViewInject(R.id.txt_title)
	private TextView txt_title;

	@ViewInject(R.id.iv_head)
	private ImageView iv_head;

	@ViewInject(R.id.bt_select_head)
	private Button btn;

	@ViewInject(R.id.bt_save)
	private Button bt_save;

	@ViewInject(R.id.et_nick_name)
	private EditText et_nick_name;

	@ViewInject(R.id.tv_show_nick_name)
	private TextView tv_show_nick_name;

	private String def_nick_name;

	/**
	 * 是否选头像
	 */
	private boolean is_upload_head = false;
	/**
	 * 改头像已经保存过了
	 */
	private boolean is_save_head = false;
	private Dialog laodDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	// 初始化控件
	@Override
	public void initView() {
		View view = View.inflate(this, R.layout.layou_upload_head, null);
		ViewUtils.inject(this, view);
		setContentView(view);
		def_nick_name = context.getResources().getString(R.string.def_nick_name);
		txt_title.setText("上传头像");
		ib_back = (LinearLayout) findViewById(R.id.ib_back);
		ib_back.setVisibility(View.VISIBLE);

		// 按返回按钮的时候销毁activity和返回键一样的工作
		ib_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myOnKeyDown();
			}
		});

		// 为ImageButton和Button添加监听事件
		iv_head.setOnClickListener(this);
		btn.setOnClickListener(this);
		bt_save.setOnClickListener(this);
		tv_show_nick_name.setText(SharedPreferencesUtils.getString(context, Constants.SHOW_NICK_NAME, def_nick_name));

		RoundHeadImageUtil.getRoundHeadImageUI(this, iv_head, true);
	}

	// 点击事件
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_head:
			showDialog();
			break;
		case R.id.bt_select_head:
			showDialog();
			break;
		case R.id.bt_save:
			saveHead();
			break;
		}

	}

	/**
	 * 保存头像
	 */
	private void saveHead() {
		if (!is_upload_head) {
			failureDialog(UploadHeadImageActivity.this, "你还没有选择上传的头像");
			is_upload_head = false;
		} else {
			is_upload_head = true;

			// false正在保存
			if (!is_save_head) {
				is_save_head = true;

				// 上传头像
				UpdateUserInfoBean bean = new UpdateUserInfoBean(UploadHeadImageActivity.this);
				bean.userInfo.headPic = BitMapUtils.Bitmap2StrByBase64(photoBitmap);
				// 如果有填写昵称
				final String nickName = et_nick_name.getText().toString().trim();
				if (!StringUtils.isEmpty(nickName)) {
					bean.userInfo.name = nickName;
				}

				String json = GsonTools.createGsonString(bean);
				laodDialog = LoadDialog.createLoadingDialog(context, "上传中...");
				laodDialog.show();
				MyHttpUtils.sendPost(Constants.UPDATE_USER_INFO_URL, json, null, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						laodDialog.dismiss();
						String res = responseInfo.result;
						System.out.println(res);
						UIUtils.showToast(context, "头像上传成功");
						// 如果有填写昵称
						if (!StringUtils.isEmpty(nickName)) {
							SharedPreferencesUtils.saveString(context, Constants.SHOW_NICK_NAME, nickName);
						}
						// 上传成功头像保存到本地
						{
							// 保存头像
							RoundHeadImageUtil.saveBitMapLock(UploadHeadImageActivity.this, photoBitmap, headLockFile);

							SharedPreferencesUtils.saveBoolean(UploadHeadImageActivity.this,
									Constants.is_save_head_state_name, true);
							// 上传成功后, 更新fragment中的头像
							{
								View view = MyPager.view;
								ImageView iv_head = (ImageView) view.findViewById(R.id.iv_head);
								iv_head.setImageBitmap(photoBitmap);
								TextView nick_name = (TextView) view.findViewById(R.id.tv_show_nick_name);
								if (!StringUtils.isEmpty(nickName)) {
									nick_name.setText(nickName);
								}
							}
						}// end本地保存头像
						is_save_head = false;
						myOnKeyDown();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						laodDialog.dismiss();
						failureDialog(context, "服务器忙,请稍后再试");
						is_save_head = false;
					}
				});

			} else {
				failureDialog(context, "请不要重复保存");
			}

		}
	}

	// 提示对话框方法
	private void showDialog() {
		new AlertDialog.Builder(this).setTitle("头像设置").setPositiveButton("拍照", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				// 调用系统的拍照功能
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 指定调用相机拍照后照片的储存路径
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempHeadLockFile));
				startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
			}
		}).setNegativeButton("相册", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
			}
		}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			startPhotoZoom(Uri.fromFile(tempHeadLockFile), 180);
			break;

		case PHOTO_REQUEST_GALLERY:
			if (data != null)
				startPhotoZoom(data.getData(), 180);
			break;

		case PHOTO_REQUEST_CUT:
			if (data != null)
				setPicToView(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			photoBitmap = bundle.getParcelable("data");
			photoBitmap = RoundHeadImageUtil.squareToRoundBitMap(photoBitmap);
			iv_head.setImageBitmap(photoBitmap);
			is_upload_head = true;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean initData() {
		return false;
	}

	@Override
	public boolean myOnKeyDown() {
		ActivityAnimationUtils.leftToRightOutAnimation(UploadHeadImageActivity.this, MainActivity.class);
		return false;
	}

}