package com.yuneec.android.flyingexpert.logic.cgo3.rtsp;

import org.json.JSONException;


public abstract class CGO3_RtspRequest{

	
	protected String requestParas = "";

	protected String responseContent = "";

	protected String errorMsg = "";
	
	protected int resultCode = -1;

	protected int requestType;

	
	public static final String BASE_URL = "http://192.168.42.1/cgi-bin/cgi?CMD=";

	
	public static String CONNECTION_STRING = "";

	
	public static String token = "";

	public static String serviceToken ="";

	public boolean valid = true;

	public static String resUrl;


	public abstract void createRequestBody() throws JSONException;

	public abstract void parseResponse() throws JSONException;
	

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
