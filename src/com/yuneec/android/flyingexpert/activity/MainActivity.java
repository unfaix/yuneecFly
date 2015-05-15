package com.yuneec.android.flyingexpert.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.library.DynamicWeatherCloudyView;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.util.NetUtils;
import com.yuneec.android.flyingexpert.util.SystemUtil;



/**
 * Main UI
 * @author yongdaimi
 * @remark 
 * @date 2015-3-17 下午2:11:56
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class MainActivity extends BaseActivity {

	
	private FrameLayout flLayout;
	private TextView tv_flight;
	private TextView tv_study;
	private TextView tv_resource;
	private TextView tv_login;
	private TextView tv_status;
	
	
	
	@Override
	protected void setContainer() {
		setContentView(R.layout.activity_main);
	}

	
	@Override
	protected void setListener() {
		
		tv_flight.setOnClickListener(this);
		tv_study.setOnClickListener(this);
		tv_resource.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		
	}

	@Override
	protected void init() {
		
		initBackGround();
		initView();
		
	}

	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_flight:
			startActivity(new Intent(getApplicationContext(),CameraActivity.class));
			break;
		case R.id.tv_study:
			showToast(R.string.building);
			break;
		case R.id.tv_resource:
			startActivity(new Intent(getApplicationContext(),ImagePreviewActivity.class));
			break;
		case R.id.tv_login:
			showToast(R.string.building);
			break;

		default:
			break;
		}
	}
	
	
	
	private void initView() {
		
		tv_status = getView(R.id.tv_status);
		tv_flight = getView(R.id.tv_flight);
		tv_study = getView(R.id.tv_study);
		tv_resource = getView(R.id.tv_resource);
		tv_login = getView(R.id.tv_login);
		
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		updateNetWorkStatus();
	}
	
	
	
	private void updateNetWorkStatus() {
		
		if (NetUtils.isWIFIConnected(getApplicationContext())) {
			 String wifiName = SystemUtil.getConnectionWIFIName(getApplicationContext());
			 tv_status.setText("Conected: "+wifiName);
			 tv_status.setTextColor(Color.WHITE);
			 tv_flight.setClickable(true);
		} else {
			 tv_status.setText("Network Error");
			 tv_status.setTextColor(Color.RED);
			 tv_flight.setClickable(false);
		}
		
	}



	
	private void initBackGround() {
		
		flLayout = getView(R.id.fllayout);
		DynamicWeatherCloudyView view1=new DynamicWeatherCloudyView(this,
				 R.drawable.yjjc_h_a2, -150, 40,20);
				 DynamicWeatherCloudyView view4=new DynamicWeatherCloudyView(this,
				 R.drawable.yjjc_h_a5, 0, 60,30);
				 DynamicWeatherCloudyView view2=new DynamicWeatherCloudyView(this,
				 R.drawable.yjjc_h_a3, 280, 80,50);
				 DynamicWeatherCloudyView view3=new DynamicWeatherCloudyView(this,
				 R.drawable.yjjc_h_a4, 140, 130,40);
				
				 flLayout.addView(view1);
				 flLayout.addView(view2);
				 flLayout.addView(view3);
				 flLayout.addView(view4);
				 view1.move();
				 view2.move();
				 view3.move();
				 view4.move();
		
	}



	private long lastBackPressed;
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			long currentBackPressed = System.currentTimeMillis();
			if (currentBackPressed - lastBackPressed > 1500) {
				showToast(R.string.back_tips);
			} else {
				mApplication.exit();
			}
			lastBackPressed = currentBackPressed;
			break;
		default:
			break;
		}
		return false;
	}
	
	
	
	
	@Override
	protected void addActivity() {
		mApplication.addActivity(this);
	}

	
	@Override
	protected void initProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
	}
    
    
}
