package com.yuneec.android.flyingexpert.logic.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.rtsp.RTSPCommand;
import com.yuneec.android.flyingexpert.logic.rtsp.RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;
import com.yuneec.android.flyingexpert.util.TimeUtil;


/**
 * ****************************************************************
 * SetCurrentTimeRequest
 * @Author yongdaimi
 * @Remark This request is to set current time to the camera.
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class SetCurrentTimeRequest extends RtspRequest {

	
	private static final String format_str = "yyyy-MM-dd_HH:mm:ss";
	
	private String final_date = "";
	
	
	public SetCurrentTimeRequest() {
		super();
		final_date = TimeUtil.getCurrentTime(format_str);
	}




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
			
		}
		
	}

	
	@Override
	public String getUrl() {
		return BASE_URL+RTSPCommand.URL_SET_TIME+"&time="+final_date;
	}

}
