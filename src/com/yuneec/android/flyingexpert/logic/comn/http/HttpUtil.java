package com.yuneec.android.flyingexpert.logic.comn.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.settings.AppConfig;
import com.yuneec.android.flyingexpert.util.LogX;
import com.yuneec.android.flyingexpert.util.SystemUtil;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;


public class HttpUtil {

	/**
	 * 
	 * @param context
	 * @param request
	 * @param message
	 */
	public static void sendRequest(Context context, HttpRequest request,
			Message message) {
		request.valid = true;
		RequestRunnable runnable = new RequestRunnable(context, request, message);
		HttpRequestThread requestThread = new HttpRequestThread(runnable);
		requestThread.sendToServer();

	}

	public static class RequestRunnable implements Runnable {
		private HttpRequest request;
		private Message message;
		private Context context;
		private boolean flag = true;

		public RequestRunnable(Context context, HttpRequest request,
				Message message) {
			this.request = request;
			this.message = message;
			this.context = context;
		}

		@Override
		public void run() {
			try {
				request.createRequestBody();
				HttpParams httpParameters = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
				HttpConnectionParams.setSoTimeout(httpParameters, 50000);
				HttpClient httpClient = new DefaultHttpClient(httpParameters);
				HttpPost post = new HttpPost(request.getUrl());
				
				post.addHeader(RequestKey.TOKEN, HttpRequest.token);
				post.addHeader("platform", "android");
				post.addHeader("version", SystemUtil.getAppVersionName(context));
				//post.addHeader("serviceToken", HttpRequest.serviceToken);
				LogX.i("url", ":" + request.getUrl());
				
				StringEntity paras = new StringEntity(request.getRequestParas(), HTTP.UTF_8);
				post.setEntity(paras);
				HttpResponse response = httpClient.execute(post);
				int code = response.getStatusLine().getStatusCode();
				switch (code) {
				case 200:

					LogX.i("HttpUtil-length====", response.getEntity()
							.getContentLength() + "");
					request.responseContent = EntityUtils.toString(
							response.getEntity(), HTTP.UTF_8);
					LogX.i("responseContent", ":" + request.responseContent);
					request.parseResponse();
					checkAuth(context, request.getResultCode(), flag);
					flag = false;
					break;
				default:
					break;
				}

				if (null != message) {
					Bundle data = message.getData();
					data.putInt(RequestKey.HTTP_CODE, code);
					if (request.isValid()) {
						message.sendToTarget();
					} else {
						request.setResultCode(861018);
						message.sendToTarget();
					}

				}
			} catch (JSONException e) {
				e.printStackTrace();
				if (null != message) {
					sendExeption(message, 80001);
				}
			} catch (IOException e) {
				e.printStackTrace();
				if (null != message) {
					sendExeption(message, 80002);
				}
			}
		}
	}

	
	/**
	 * 
	 * @param message
	 * @param code
	 */
	public static void sendExeption(Message message, int code) {
		if (message != null) {
			Bundle data = message.getData();
			data.putInt(RequestKey.HTTP_CODE, code);
			message.sendToTarget();
		}
	}

	private static void checkAuth(Context context, int resultCode, boolean flag) {
		if (resultCode == 9) {
			if (flag) {
				context.sendBroadcast(new Intent(
						AppConfig.ACTION_MESSAGE_RELOGIN));
			}

		}
	}

}
