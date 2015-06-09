package com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RTSPcommand;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * GetAudioSwitchRequest
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class GetAudioSwitchRequest extends CGO3_RtspRequest {
	
	
	private String audio = "1"; // 0 open the audio
	
	
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
			if (rootObject.has("audio_sw")) {
				audio = rootObject.getString("audio_sw");
			}
		} else {
			errorMsg = responseContent;
		}
		
	}

	
	
	@Override
	public String getUrl() {
		return BASE_URL+CGO3_RTSPcommand.URL_GET_AUDIO_SW.cmd();
    }


	public String getAudio() {
		return audio;
	}

}
