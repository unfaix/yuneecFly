package com.yuneec.android.flyingexpert.logic.http;

import org.json.JSONException;


public abstract class HttpRequest{

	protected String requestParas = "";

	protected String responseContent;

	protected int resultCode = -1;

	protected int requestType;

	public static final String BASE_URL = "http://58.210.39.226:121/Business.ashx"; // Update Url
	
	public static final String PIC_DOWNLOAD_URL = "http://192.168.42.1/100MEDIA/"; // pic download url
	
	
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

}
