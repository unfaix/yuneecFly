package com.yuneec.android.flyingexpert.logic.rtsp;

import com.yuneec.android.flyingexpert.logic.rtsp.RtspUtil.RequestRunnable;



public class RtspRequestThread extends Thread {
	
	private RequestRunnable runnable;

	public RtspRequestThread(Runnable runnable) {
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
