package com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RTSPcommand;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * Get current version Request
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class GetVersionRequest extends CGO3_RtspRequest {

	
	private String model = "";
	private String yuneec = "";
	
	@Override
	public void createRequestBody() throws JSONException {
		
		LogX.i("test_send", "current request is : "+getClass().getSimpleName());
		
	}

	
	
	
	@Override
	public void parseResponse() throws JSONException {
		JSONObject rootObject = new JSONObject(responseContent);
		LogX.i("test_receive", responseContent);
		resultCode = Integer.valueOf(rootObject.getInt(RequestKey.RESULT_CODE));
		if (resultCode == 0) {
			if (rootObject.has("model")) {
				model = rootObject.getString("model");
			}
			if (rootObject.has("YUNEEC_ver")) {
				yuneec = rootObject.getString("YUNEEC_ver");
			}
		}
		
	}

	
	@Override
	public String getUrl() {
		return BASE_URL+CGO3_RTSPcommand.URL_GET_FW_VERSION.cmd();
	}



	public String getModel() {
		return model;
	}


	public String getYuneec() {
		return yuneec;
	}
	

}
