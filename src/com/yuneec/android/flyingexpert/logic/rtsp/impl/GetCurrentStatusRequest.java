package com.yuneec.android.flyingexpert.logic.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.rtsp.RTSPCommand;
import com.yuneec.android.flyingexpert.logic.rtsp.RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * GetCurrentStatusRequest
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class GetCurrentStatusRequest extends RtspRequest {

	
	private double freeSpace = 0d;
	private String status = "vf";
	private String recordTime = "0";
	private int cameraMode = 1;
	private String aeMode = "0";
	private String ev_value = "0.0";
	private String iso_value = "ISO_100";
	private String shutter_value = "60";
	private String wbMode = "0";
	
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
			if (rootObject.has("sdfree")) {
				freeSpace = Double.parseDouble(rootObject.getString("sdfree"));
			}
			
			if (rootObject.has("status")) {
				status = rootObject.getString("status");
			}
			
			if (rootObject.has("record_time")) {
				recordTime = rootObject.getString("record_time");
			}
			
			if (rootObject.has("cam_mode")) {
				try {
					cameraMode = Integer.parseInt(rootObject.getString("cam_mode"));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			
			if (rootObject.has("ae_enable")) {
				aeMode = rootObject.getString("ae_enable");
			}
			
			if (rootObject.has("exposure_value")) {
				ev_value = rootObject.getString("exposure_value");
			}
			
			if (rootObject.has("iso_value")) {
				String temp = rootObject.getString("iso_value"); // ISO value ISO_100 to split
				iso_value = temp.substring(temp.lastIndexOf("_")+1);
			}
			
			if (rootObject.has("shutter_time")) {
				shutter_value = rootObject.getString("shutter_time");
			}
			
			if (rootObject.has("white_balance")) {
				wbMode = rootObject.getString("white_balance");
			}
			
		} else {
			errorMsg = responseContent;
		}
		
	}

	
	
	@Override
	public String getUrl() {
		return BASE_URL+RTSPCommand.URL_GET_STATUS;
	}


	public double getFreeSpace() {
		return freeSpace;
	}


	public String getStatus() {
		return status;
	}


	public String getRecordTime() {
		return recordTime;
	}


	public int getCameraMode() {
		return cameraMode;
	}


	public String getAeMode() {
		return aeMode;
	}


	public String getEv_value() {
		return ev_value;
	}


	public String getIso_value() {
		return iso_value;
	}


	public String getShutter_value() {
		return shutter_value;
	}

	public String getWbMode() {
		return wbMode;
	}
	

}
