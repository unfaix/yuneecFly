package com.yuneec.android.flyingexpert.logic.rtsp;



import android.content.Context;
import android.os.Message;

public class RtspRequestManager {

	/**
	 * 
	 * @param context
	 * @param request
	 * @param message
	 */
	public static void sendRequest(Context context, RtspRequest request,
			Message message) {
		RtspUtil.sendRequest(context, request, message);
	}
	
	
}
