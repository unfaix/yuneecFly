package com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RTSPcommand;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;


/**
 * ****************************************************************
 * set white blance
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class SetWhiteBlanceRequest extends CGO3_RtspRequest {

	
	private String[] curValue = {};
	
	private int mode = 0; // auto;
	
	
	@Override
	public void createRequestBody() throws JSONException {
		
		LogX.i("test_send", "SetWhiteBlanceRequest");
		
	}

	
	@Override
	public void parseResponse() throws JSONException {
		JSONObject rootObject = new JSONObject(responseContent);
		LogX.i("test_receive", responseContent);
		resultCode = Integer.valueOf(rootObject.getInt(RequestKey.RESULT_CODE));
		/*if (resultCode == 0) {
			curValue = new String[64];
			
			int mode = rootObject.getInt("iq_type");
			int wb = rootObject.getInt("white_balance");
			String ec = rootObject.getString("exposure_value");
			int sharp = rootObject.getInt("sharpness");

			curValue[38] = rootObject.getString("sdfree");
			curValue[39] = rootObject.getString("sdtotal");

			// CurValue[1]="720P";//mVideoResolution = "720p"
			curValue[1] = rootObject.getString("video_mode");
			curValue[2] = "30FPS";// mFrameRate = "30FPS"
			curValue[3] = "WIDE";// mFieldofView="WIDE"
			curValue[4] = "5MP MED";// mPhotoResolution="5MP MED"
			curValue[5] = "SINGLE";// mContinuousShot="SINGLE"
			curValue[6] = "10/1 SEC";// mBurstRate="10/1 SEC"
			curValue[7] = "1 SEC";// mTimeLapse="1 SEC"

			// CurValue[13]="2";//mWhiteBalance="2"
			curValue[13] = CameraUtil.getWhiteBalance(wb);
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

			// CurValue[30]="4.0.36.1";//mWIFIVersion ="4.0.36.1"

			curValue[30] = rootObject.getString("speed_rate");

			curValue[35] = "CGO 3";// mCameraName ="CG3+ Black Edition"
			// CurValue[36]="HD3.11.02.22";//mVersion="HD3.11.02.22"
			curValue[36] = rootObject.getString("fw_ver");
			curValue[37] = rootObject.getString("status");// status
			
		}*/
		
	}

	
	@Override
	public String getUrl() {
		return BASE_URL+CGO3_RTSPcommand.URL_SET_WHITEBLANCE_MODE.cmd()+"&mode="+mode;
	}

	
	public void setMode(int mode) {
		this.mode = mode;
	}


	public String[] getCurValue() {
		return curValue;
	}


	public int getMode() {
		return mode;
	}
	
	
	public String getModeStr() {
		String mode = "Auto";
		switch (this.mode) {
		case 0:
			mode = "Auto";
			break;
		case 1:
			mode = "INCANDESCENT";
			break;
		case 3:
			mode = "Sunset";
			break;
		case 4:
			mode = "sunny";
			break;
		case 5:
			mode = "cloudy";
			break;
		case 7:
			mode = "FLUORESCENT";
			break;
		case 99:
			mode = "lock";
			break;
		default:
			break;
		}
		return mode;
	}
	

}
