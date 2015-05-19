package com.yuneec.android.flyingexpert.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.settings.AppConfig;

/**
 * *******************************************************************************
 * Share to somewhere
 * @Author yongdaimi
 * @Remark 
 * @Date May 18, 2015 3:34:51 PM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class ShareUtil {

	
	private static final int THUMB_SIZE = 150;
	
	public static void share2WeChat(Context context, IWXAPI api) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://www.yuneec.com";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "CGO tools";
		msg.description = "click me to download the new version";
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.logo);
		Bitmap thumb = Bitmap
				.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
		msg.thumbData = Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}
	
	
	public static void share2WeChatFriend(Context context, IWXAPI api) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://www.yuneec.com";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "CGO tools";
		msg.description = "click me to download the new version";
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.logo);
		Bitmap thumb = Bitmap
				.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
		msg.thumbData = Util.bmpToByteArray(thumb, true);
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}
	
	
	private static String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	

}
