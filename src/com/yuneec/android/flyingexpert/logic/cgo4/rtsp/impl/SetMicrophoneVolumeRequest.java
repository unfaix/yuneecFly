package com.yuneec.android.flyingexpert.logic.cgo4.rtsp.impl;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.yuneec.android.flyingexpert.logic.cgo4.rtsp.CGO4RTSPcommand;
import com.yuneec.android.flyingexpert.logic.cgo4.rtsp.CGO4_RtspRequest;
import com.yuneec.android.flyingexpert.util.LogX;

public class SetMicrophoneVolumeRequest extends CGO4_RtspRequest {

	
	private String time = "";
	
	private String whiteBlance;
	
	


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
					if (parser.nextText().equals("ok")) {
						resultCode = 0;
					} else {
						resultCode = -1;
					}
				}
				
				// TODO something...
				break;
			default:
				break;
			}
			eventType = parser.next();
		}
		
	}

	
	
	
	public String getTime() {
		return time;
	}


	@Override
	public String getUrl() {
		return BASE_URL+CGO4RTSPcommand.SET_WHITE_BALANCE.cmd()+whiteBlance;
	}
	
}
