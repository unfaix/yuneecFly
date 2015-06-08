package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

import com.yuneec.android.flyingexpert.logic.cgo4.rtsp.CGO4_RtspUtil.RequestRunnable;



public class CGO4_RtspRequestThread extends Thread {
	
	private RequestRunnable runnable;

	public CGO4_RtspRequestThread(Runnable runnable) {
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
