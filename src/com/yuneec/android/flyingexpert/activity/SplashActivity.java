package com.yuneec.android.flyingexpert.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.SharedPreference;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.entity.UserInfo;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.comn.http.HttpRequestManager;
import com.yuneec.android.flyingexpert.logic.comn.http.impl.CheckUpdateRequest;



/**
 * Splash UI
 * @author yongdaimi
 * @remark 
 * @date 2015-3-17下午3:22:42
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class SplashActivity extends BaseActivity {

	
	private CheckUpdateRequest mCheckUpdateRequest;
	
	
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}

	
	@Override
	protected void setContainer() {
		setContentView(R.layout.activity_splash);
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkUpdate();
		// checkUserStatus();
		// checkNetStatus();
	}
	
	
	
	
	private void checkNetStatus() {
		
	}

	
	
	private void loadMainUI() {
		startActivity(new Intent(getApplicationContext(),MainActivity.class));
		finish();
	}

	
	
	
	private void checkUserStatus() {
		UserInfo user = SharedPreference.getUserInfo(getApplicationContext());
		if (user.getUserId().equals("-1")) {
			user.setUserId("5");
			user.setUsername("XXX");
			user.setAddress("XXX");
			user.setContact("15062683794");
			user.setScore("5");
			SharedPreference.setUserInfo(getApplicationContext(), user);
			mApplication.setUser(user);
			loadMainUI();
		} else {
			// TODO loginRequest
			mApplication.setUser(user);
			loadMainUI();
		}
		
	}

	private void checkUpdate() {
		
		mCheckUpdateRequest = new CheckUpdateRequest();
		mCheckUpdateRequest.setmContext(getApplicationContext());
		HttpRequestManager.sendRequest(getApplicationContext(),
				mCheckUpdateRequest, mHandler.obtainMessage(1));
		
	}
	
	


	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int httpCode = msg.getData().getInt(RequestKey.HTTP_CODE);
			switch (msg.what) {
			// check update
			case 1:
				if (httpCode == 200) {
					switch (mCheckUpdateRequest.getResultCode()) {
					case 0:
						loadMainUI();
						break;
					case 2:
						
						break;
					default:
						break;
					}
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
					loadMainUI();
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
					loadMainUI();
				} else if (httpCode == 80002) {
					// showToast(R.string.is_error_network);
					loadMainUI();
				}
				break;
			default:
				break;
			}
			
		}
		
	};
	
	
	
	@Override
	protected void addActivity() {
		mApplication.addActivity(this);
	}
	
	@Override
	protected void initProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
	}

}

