package com.yuneec.android.flyingexpert.logic.cgo4.rtsp.impl;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.yuneec.android.flyingexpert.logic.cgo4.rtsp.CGO4_RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;

/**
 * *******************************************************************************
 * Init Request
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 9, 2015 11:16:32 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class InitRequest extends CGO4_RtspRequest {

	
	
	@Override
	public void createRequestBody() throws XmlPullParserException, IOException {
		LogX.i("test_send", "current request is : "+getClass().getSimpleName());
	}

	
	
	@Override
	public void parseResponse() throws XmlPullParserException, IOException {
		LogX.i("test_receive", responseContent);
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(new StringReader(responseContent));
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name = "";
			switch (eventType) {
			case XmlPullParser.START_TAG:
				name = parser.getName();
				if ("result".equalsIgnoreCase(name)) {
					if (!parser.nextText().equals("ok")) {
						return;
					}
				}
				
				break;
			default:
				break;
			}
			eventType = parser.next();
		}
		
	}

	

	@Override
	public String getUrl() {
		return "http://192.168.54.1/cam.cgi?mode=setsetting&type=mic_level&value=-12dB";
	}
	
}
