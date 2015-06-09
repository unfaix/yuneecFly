package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

/**
 * *******************************************************************************
 * CGO4 RTSP base request
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 8, 2015 10:22:21 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public abstract class CGO4_RtspRequest {

	
	protected String requestParas = "";

	protected String responseContent = "";

	protected String errorMsg = "";
	
	protected int resultCode = -1;

	protected int requestType;

	
	public static final String BASE_URL = "http://192.168.54.1/cam.cgi?mode=";

	
	public static String CONNECTION_STRING = "";

	
	public static String token = "";

	public static String serviceToken ="";

	public boolean valid = true;

	public static String resUrl;


	public abstract void createRequestBody() throws XmlPullParserException, IOException;

	public abstract void parseResponse() throws XmlPullParserException, IOException;
	

	public abstract String getUrl();

	public String getRequestParas() {
		return requestParas;
	}


	public int getRequestType() {
		return requestType;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public void cancelRequest() {
		valid = false;
	}

	public void activeRequest() {
		valid = true;
	}

	public boolean isValid() {
		return valid;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	

}
