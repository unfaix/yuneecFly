package com.yuneec.android.flyingexpert.logic.comn.http;

import com.yuneec.android.flyingexpert.logic.comn.http.HttpUtil.RequestRunnable;



public class HttpRequestThread extends Thread {
	
	private RequestRunnable runnable;

	public HttpRequestThread(Runnable runnable) {
		super();
		this.runnable = (RequestRunnable) runnable;
	}

	public void sendToServer() {
		this.start();
	}

	@Override
	public void run() {
		runnable.run();
	}

}
