package com.yuneec.android.flyingexpert.logic.cgo3.rtsp;

import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspUtil.RequestRunnable;



public class CGO3_RtspRequestThread extends Thread {
	
	private RequestRunnable runnable;

	public CGO3_RtspRequestThread(Runnable runnable) {
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
