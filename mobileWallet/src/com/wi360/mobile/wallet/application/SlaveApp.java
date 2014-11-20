package com.wi360.mobile.wallet.application;

import android.app.Application;
import android.util.Log;

public class SlaveApp extends Application{
	
	private static final String TAG = "SlaveApp";
	
	private SlaveClient remoteClient;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "UnionApp onCreate");
		
		remoteClient = new SlaveClient();
		remoteClient.ConnectService(this);
		
		
	}
	
	public SlaveClient getRemoteClient(){
		if(remoteClient == null){
			remoteClient = new SlaveClient();
		}
		return remoteClient;	
		
	}
	
	
	public void exitApp(){
		if(remoteClient != null){
			remoteClient.shutDown();
			remoteClient = null;
		}
		
		System.exit(0);
	}
	
	
	
	
	

}
