package com.yuneec.android.flyingexpert.logic.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.rtsp.RTSPCommand;
import com.yuneec.android.flyingexpert.logic.rtsp.RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * Set ISO Request
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class SetISORequest extends RtspRequest {

	
	private String shutterTime = "30";
	private String iso = "100";
	
	
	@Override
	public void createRequestBody() throws JSONException {
		
		LogX.i("test_send", "SetISORequest");
		
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
		return BASE_URL+RTSPCommand.URL_SET_SH_TM_ISO+"&time="+shutterTime+"&value=ISO_"+iso;
	}


	public void setShutterTime(String shutterTime) {
		this.shutterTime = shutterTime;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}


}
