package com.yuneec.android.flyingexpert.activity;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.rtsp.RtspRequestManager;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.GetVersionRequest;
import com.yuneec.android.flyingexpert.util.SystemUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

/**
 * About UI
 * @author yongdaimi
 * @remark
 * @date 2014-11-9 下午04:45:41
 * @company Copyright (C) PaoPao.Inc. All Rights Reserved.
 */
public class AboutActivity extends BaseActivity {


	private TextView tv_version_name;
	private TextView tv_model_name;
	private TextView tv_yuneec_name;

	private GetVersionRequest mGetVersionRequest;

	@Override
	protected void setContainer() {
		setContentView(R.layout.activity_about);
	}

	
	
	@Override
	protected void setListener() {

	}

	
	
	
	@Override
	protected void init() {

		tv_version_name = getView(R.id.tv_version_name);
		tv_model_name = getView(R.id.tv_model_name);
		tv_yuneec_name = getView(R.id.tv_yuneec_name);
		tv_version_name.setText(SystemUtil.getAppVersionName(this));

	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getVersionRequest();
	}

	
	
	public void getVersionRequest() {

		mGetVersionRequest = new GetVersionRequest();
		RtspRequestManager.sendRequest(getApplicationContext(),mGetVersionRequest, mHandler.obtainMessage(1));

	}

	
	
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int httpCode = msg.getData().getInt(RequestKey.HTTP_CODE);
			switch (msg.what) {
			// getVersionRequest
			case 1:
				if (httpCode == 200) {
					switch (mGetVersionRequest.getResultCode()) {
					case 0:
						tv_model_name.setText("Model:"+mGetVersionRequest.getModel());
						tv_yuneec_name.setText("Yuneec Version:"+mGetVersionRequest.getYuneec());
						break;

					default:
						break;
					}
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;

			default:
				break;
			}

		}

	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		
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
