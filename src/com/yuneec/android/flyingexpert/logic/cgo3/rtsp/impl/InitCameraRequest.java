package com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RTSPcommand;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspRequest;
import com.yuneec.android.flyingexpert.util.CameraUtil;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * init camera
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class InitCameraRequest extends CGO3_RtspRequest {

	
	private String[] curValue = {};
	
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
			curValue = new String[64];
			int mode = 0;
			int wb = 0;
			int sharp = 0;
			String ec = "";
			if (rootObject.has("iq_type")) {
				mode = rootObject.getInt("iq_type");
			}
			if (rootObject.has("white_balance")) {
				curValue[13] = rootObject.getString("white_balance");
			}
			if (rootObject.has("exposure_value")) {
				ec = rootObject.getString("exposure_value");
			}
			if (rootObject.has("sharpness")) {
				sharp = rootObject.getInt("sharpness");
			}
			if (rootObject.has("sdfree")) {
				curValue[38] = rootObject.getString("sdfree");
			}
			if (rootObject.has("sdtotal")) {
				curValue[39] = rootObject.getString("sdtotal");
			}
			if (rootObject.has("video_mode")) {
				// CurValue[1]="720P";//mVideoResolution = "720p"
				curValue[1] = rootObject.getString("video_mode");
			}
			if (rootObject.has("speed_rate")) {
				// CurValue[30]="4.0.36.1";//mWIFIVersion ="4.0.36.1"
				curValue[30] = rootObject.getString("speed_rate");
			}
			if (rootObject.has("fw_ver")) {
				// CurValue[36]="HD3.11.02.22";//mVersion="HD3.11.02.22"
				curValue[36] = rootObject.getString("fw_ver");
			}
			
			if (rootObject.has("cam_mode")) {
				curValue[45] = rootObject.getString("cam_mode"); // cam_mode  1: video 2:photo
				// get the record status. Only cam_mode value is 1
				if (curValue[45].equals("1")) {
					if (rootObject.has("status")) {
						curValue[37] = rootObject.getString("status");// status record/vf
					} else {
						curValue[37] = "record";
					}
					if (rootObject.has("record_time")) {
						curValue[40] = rootObject.getString("record_time");// status
					} else {
						curValue[40] = "0";
					}
				}
			} else {
				curValue[45] = "1";
			}
			
			if (rootObject.has("ae_enable")) {
				curValue[46] = rootObject.getString("ae_enable"); // AE settings 0:manual 1:audo
			}
			
			if (rootObject.has("shutter_time")) {
				curValue[47] = rootObject.getString("shutter_time"); // ShutterTime value 30~8000
			}
			
			if (rootObject.has("iso_value")) {
				String temp = rootObject.getString("iso_value"); // ISO value ISO_100 to split
				curValue[48] = temp.substring(temp.lastIndexOf("_")+1);
			}
			
			if (rootObject.has("exposure_value")) {
				curValue[49] = rootObject.getString("exposure_value"); // EV value 0.0
			}
			
			
			curValue[2] = "30FPS";// mFrameRate = "30FPS"
			curValue[3] = "WIDE";// mFieldofView="WIDE"
			curValue[4] = "5MP MED";// mPhotoResolution="5MP MED"
			curValue[5] = "SINGLE";// mContinuousShot="SINGLE"
			curValue[6] = "10/1 SEC";// mBurstRate="10/1 SEC"
			curValue[7] = "1 SEC";// mTimeLapse="1 SEC"

			// CurValue[13]="2";//mWhiteBalance="2"
			/*curValue[13] = CameraUtil.getWhiteBalance(wb);*/
			// CurValue[14]="Portrait";//mMode="Portrait"
			curValue[14] = CameraUtil.getMode(mode);
			curValue[15] = "6400";// mISOLimit="6400"
			// CurValue[16]="HIGH";//mSharpness="HIGH"
			curValue[16] = CameraUtil.getSharpness(sharp);
			// CurValue[17]="0.5";//mExposureCompensation="0.5"
			curValue[17] = ec;

			curValue[20] = "6";// mLED="6"
			curValue[21] = "100%";// mSound="100%"
			curValue[22] = "VIDEO";// mDefaultatPowerUp="VIDEO"
			curValue[23] = "NTSC";// mNP="NTSC"

			curValue[29] = "CGO2_10290D";// mWIFIName = "CGO2_10290D"
			curValue[32] = "50%";// mBatteryLevel ="50%"
			curValue[33] = "32G";// mSDCardCapacity="32G"
			curValue[35] = "CGO 3";// mCameraName ="CG3+ Black Edition"
			
		}
		
	}

	
	@Override
	public String getUrl() {
		return BASE_URL+CGO3_RTSPcommand.URL_INIT_CAMERA.cmd();
	}


	
	public String[] getCurValue() {
		return curValue;
	}
	
	
	

}
