package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;



import android.content.Context;
import android.os.Message;

public class CGO4_RtspRequestManager {

	/**
	 * 
	 * @param context
	 * @param request
	 * @param message
	 */
	public static void sendRequest(Context context, CGO4_RtspRequest request,
			Message message) {
		CGO4_RtspUtil.sendRequest(context, request, message);
	}
	
	
}
