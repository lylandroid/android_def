package com.wi360.mobile.wallet.application;

import android.content.Context;
import android.util.Log;

import com.cmcc.wallet.service.api.CommandID;
import com.cmcc.wallet.service.api.MocamProfile;
import com.cmcc.wallet.service.api.MocamRemoteService;
import com.cmcc.wallet.service.api.MocamRemoteWatcher;
import com.cmcc.wallet.service.api.ServiceConnectionCallback;

/**
 * adb方式查看logcat:$ adb logcat *:v
 * 
 * @author Administrator
 * 
 */
public class SlaveClient {

	private static final String TAG = "SlaveClient";

	Context mcontext;

	public MocamRemoteService mRemoteService;

	public String phone;

	Object mLock = new Object();

	public SlaveClient() {

	}

	/**
	 * 建立与主件的服务通道
	 * 
	 * @param context
	 * @return 2013-7-10 上午9:59:34
	 * @author houmiao.xiong
	 */
	public boolean ConnectService(Context context) {
		mcontext = context;
		synchronized (mLock) {
			// ServiceConnectionCallback - Service连接异常回调接口
			new MocamRemoteService(context, new ServiceConnectionCallback() {
				// 服务断开回调
				@Override
				public void onServiceDisconnected() {// 断开连接
					// TODO Auto-generated method stub
					Log.d(TAG, "onServiceDisconneted");
				}

				// 当主件服务连接并 认证通过 后将回调onServiceConnected方法.
				@Override
				public void onServiceConnected(MocamRemoteService service) {
					// 当service = null 代表连接失败. 原因可能是主件服务不存在，或权限认证失败
					Log.d(TAG, "onServiceConneted");
					if (service != null) {
						mRemoteService = service;
						// phone =
						// mRemoteService.getProfile(MocamProfile.MOCAM_PROFILE_MSISDN);

						registerRemoteWatcher(watcher);
					}

					synchronized (mLock) {
						mLock.notifyAll();
					}
				}
			});
			long t = System.currentTimeMillis();
			try {
				// 200ms都没连上，我们就把他当连不上�?
				mLock.wait(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}
			Log.d(TAG, "----------connectService time:" + (System.currentTimeMillis() - t));
		}
		if (mRemoteService != null) {
			return mRemoteService.isConnected();
		} else {
			return false;
		}
	}

	/**
	 * 注册主件消息监听器
	 * 
	 * @param watcher
	 *            2013-7-10 上午9:59:51
	 * @author houmiao.xiong
	 */
	public void registerRemoteWatcher(MocamRemoteWatcher watcher) {
		if (isReady()) {
			Log.d(TAG, "registerRemoteWatcher");
			mRemoteService.registerRemoteWatcher(watcher);
		}
	}

	/**
	 * 通知主件消息
	 * 
	 * @param commandId
	 * @param transactionID
	 * @param resultCode
	 * @param responseData
	 *            2013-7-10 上午10:01:03
	 * @author houmiao.xiong
	 */
	public void notifyMocamResponse(int commandId, int transactionID, int resultCode, String responseData) {
		if (isReady()) {
			mRemoteService.notifyMocamResponse(commandId, transactionID, resultCode, responseData);
		}
	}

	/**
	 * 请求主件执行任务 辅件调用主件执行任务请求. 主件异步方式执行任务请求. 任务执行响应将异步通过RemoteWatcher 返回
	 * 
	 * @param commandID
	 *            :需要执行的任务ID，任务ID在CommandID中得到定义
	 * @param transactionID
	 *            :请求事务ID
	 * @param data
	 *            :请求参数.以json方式组织，详见《主辅件通讯data 定义文档》
	 * @return 请求到达状态 0 : 请求已正常接收, -1: 请求任务不存在, -2: 请求data参数不正确
	 * @author houmiao.xiong
	 */

	public int postMocamRequest(int commandID, int transactionID, String data) {
		if (isReady()) {
			return mRemoteService.postMocamRequest(commandID, transactionID, data);
		}
		return -1;
	}

	/**
	 * 获取主件资源
	 * 
	 * @param profilename
	 * @return 2013-7-10 上午10:01:46
	 * @author houmiao.xiong
	 */
	public String getProfile(int profilename) {
		if (isReady()) {
			return mRemoteService.getProfile(profilename);
		}
		return null;
	}

	/**
	 * 是否连接主件服务
	 * 
	 * @return 2013-7-10 上午10:02:01
	 * @author houmiao.xiong
	 */
	public boolean isReady() {
		if (mRemoteService != null) {
			return mRemoteService.isConnected();
		}
		return false;
	}

	/**
	 * 断开主件服务 2013-7-10 上午10:02:32
	 * 
	 * @author houmiao.xiong
	 */
	public void shutDown() {
		if (mRemoteService != null) {
			mRemoteService.shutdown();
			mRemoteService = null;
		}
	}

	MocamRemoteWatcher watcher = new MocamRemoteWatcher() {
		// 主件通知辅件执行任务请求.
		// 辅件需异步方式执行任务，并通过MocamRemoteService.NotificationFromSlave将执行结果通知主件
		@Override
		public int onGetMocamRequest(int commandID, int transactionID, String data) {

			Log.d(TAG, "onGetMocamRequest " + commandID);

			if (commandID == CommandID.CMD_LAUNCH_SLAVE_MARKET || commandID == CommandID.CMD_INSTALL_APP
					|| commandID == CommandID.CMD_UNINSTALL_APP || commandID == CommandID.CMD_UPDATE_APP
					|| commandID == CommandID.CMD_APPLY_APP || commandID == CommandID.CMD_LAUNCH_SLAVE_CARD_DETAIL_PAGE) {
				// do mocam CommandID

			} else if (commandID == CommandID.CMD_SHUTDOWN_SLAVE_SERVICE) {
				// shut down
				shutDown();
			}

			return transactionID;
		}

		// 处理主件消息回调. 辅件请求主件任务后，主件将任务执行结果通过该方法回调通知辅件
		@Override
		public void handleMocamResponse(int commandID, int transactionID, int resultCode, String data) {
			// TODO Auto-generated method stub
			// handler mocam response

		}

	};

	private ServiceConnectionCallback getConnetionCallback() {
		return new ServiceConnectionCallback() {

			@Override
			public void onServiceDisconnected() {// 断开连接
				// TODO Auto-generated method stub
				Log.d(TAG, "onServiceDisconneted");
			}

			@Override
			public void onServiceConnected(MocamRemoteService service) {
				// TODO Auto-generated method stub
				if (service != null) {
					Log.d(TAG, "onServiceConneted");
					mRemoteService = service;
					registerRemoteWatcher(watcher);
				}

				synchronized (mLock) {
					mLock.notifyAll();
				}
			}
		};
	}

}
