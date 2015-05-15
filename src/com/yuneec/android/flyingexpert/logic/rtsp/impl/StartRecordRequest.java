package com.yuneec.android.flyingexpert.logic.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.rtsp.RTSPCommand;
import com.yuneec.android.flyingexpert.logic.rtsp.RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * Start Record Request
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class StartRecordRequest extends RtspRequest {

	
	@Override
	public void createRequestBody() throws JSONException {
		
		LogX.i("test_send", "StartRecordRequest");
		
	}

	
	
	
	@Override
	public void parseResponse() throws JSONException {
		JSONObject rootObject = new JSONObject(responseContent);
		LogX.i("test_receive", responseContent);
		resultCode = Integer.valueOf(rootObject.getInt(RequestKey.RESULT_CODE));
		if (resultCode == 0) {
			
		}
		
	}

	
	@Override
	public String getUrl() {
		return BASE_URL+RTSPCommand.URL_START_RECORD;
	}

}
