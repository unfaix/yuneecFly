package com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RTSPcommand;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * Set Audio Switch Request
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class SetAudioSwitchRequest extends CGO3_RtspRequest {

	
	private String mode = "1";
	
	
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
			if (rootObject.has("param")) {
				
			}
		} else {
			errorMsg = responseContent;
		}
		
	}

	
	
	
	@Override
	public String getUrl() {
		return BASE_URL+CGO3_RTSPcommand.URL_SET_AUDIO_SW.cmd()+"&mode="+mode;
    }



	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}

	
	public String getModeStr() {
		String resultMode = "非静音";
		if (this.mode.equals("0")) {
			resultMode = "非静音";
		} else if (this.mode.equals("1")) {
			resultMode = "静音";
		}
		return resultMode;
	}

}
