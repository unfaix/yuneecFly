package com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RTSPcommand;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * Set CameraMode Request
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class SetCameraModeRequest extends CGO3_RtspRequest {


	// 0 manual 1 Auto
	private int mode = 1;
	
	
	@Override
	public void createRequestBody() throws JSONException {
		
		LogX.i("test_send", "SetCameraModeRequest");
		
	}

	
	
	
	@Override
	public void parseResponse() throws JSONException {
		JSONObject rootObject = new JSONObject(responseContent);
		LogX.i("test_receive", responseContent);
		resultCode = Integer.valueOf(rootObject.getInt(RequestKey.RESULT_CODE));
		if (resultCode == 0) {
			
		} else {
			errorMsg = responseContent;
		}
		
	}

	
	
	@Override
	public String getUrl() {
		return BASE_URL+CGO3_RTSPcommand.URL_SET_AE_ENABLE.cmd()+"&mode="+mode;
	}



	public void setMode(int mode) {
		this.mode = mode;
	}

	
	public String getModeStr() {
		String mode = "Auto";
		switch (this.mode) {
		case 0:
			mode = "Manual";
			break;
		case 1:
			mode = "Auto";
			break;
		default:
			break;
		}
		return mode;
	}
	
}
