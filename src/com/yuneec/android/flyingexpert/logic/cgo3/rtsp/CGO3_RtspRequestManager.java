package com.yuneec.android.flyingexpert.logic.cgo3.rtsp;



import android.content.Context;
import android.os.Message;

public class CGO3_RtspRequestManager {

	/**
	 * 
	 * @param context
	 * @param request
	 * @param message
	 */
	public static void sendRequest(Context context, CGO3_RtspRequest request,
			Message message) {
		CGO3_RtspUtil.sendRequest(context, request, message);
	}
	
	
}
