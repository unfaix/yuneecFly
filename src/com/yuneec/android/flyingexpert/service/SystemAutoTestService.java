package com.yuneec.android.flyingexpert.service;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yuneec.android.flyingexpert.activity.SystemTestCameraActivity;
import com.yuneec.android.flyingexpert.util.LogX;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * *******************************************************************************
 * System Test Service
 * @Author yongdaimi
 * @Remark 
 * @Date May 21, 2015 10:26:03 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class SystemAutoTestService extends Service {

	
	private static final String TAG = "test_system_service";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		LogX.i(TAG, "System Test has created.");
		queue = new LinkedBlockingQueue<Thread>();
		mThreadPool = Executors.newSingleThreadScheduledExecutor();
	
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		return new MyBinder();
	}

	
	
	
	/**
	 * the linked queue is a safe's containerj.
	 */
	private LinkedBlockingQueue<Thread> queue = null;
	
	private ScheduledExecutorService mThreadPool = null;
	
	private LinkedList<String> resolutionList = null;
	
	// private LinkedList<Integer> recordLengthList = null;
	
	private SystemTestCameraActivity cameraActivity;
	

	{
		
		resolutionList = new LinkedList<String>();
		// recordLengthList = new LinkedList<Integer>();
		
		resolutionList.offer("4096x2160F24");
		resolutionList.offer("4096x2160F25");
		resolutionList.offer("3840x2160F24");
		resolutionList.offer("3840x2160F25");
		resolutionList.offer("3840x2160F30");
		resolutionList.offer("2560x1440F24");
		resolutionList.offer("2560x1440F25");
		resolutionList.offer("2560x1440F30");
		resolutionList.offer("1920x1080F24");
		resolutionList.offer("1920x1080F25");
		resolutionList.offer("1920x1080F30");
		resolutionList.offer("1920x1080F48");
		resolutionList.offer("1920x1080F50");
		resolutionList.offer("1920x1080F60");
		resolutionList.offer("1920x1080F120");
		
//		recordLengthList.offer(5);
//		recordLengthList.offer(15);
//		recordLengthList.offer(30);
		
	}
	
	
	public class MyBinder extends Binder {
		
		
		public void setInstance(SystemTestCameraActivity activity) {
			cameraActivity = activity;
		}
		
		
		private void init() {
			
			
			
			Thread step00_setResolution =  new Thread() {

				@Override
				public void run() {
					if (resolutionList != null) {
						if (resolutionList.size() == 0) {
							sendBroadcast(new Intent(SystemTestCameraActivity.ACTION_RESOLUTION_HAS_COMPLETED));
						} else if (resolutionList.size() > 0) {
							cameraActivity.setResolutionRequest(resolutionList.poll());
						}
					}
					
				}
				
			};
			
			
			Thread step0_modifyCameraStatus = new Thread() {

				@Override
				public void run() {
					cameraActivity.setCurrentCameraModeRequest(1);
				}
				
			};
			
			Thread step1_startRecord = new Thread() {

				@Override
				public void run() {
					cameraActivity.startRecordRequest();
				}
			};
			
			Thread step2_setWB = new Thread() {

				@Override
				public void run() {
					Random  random = new Random();
					int[] wbMode = {0,1,3,4,5,7,99};
					cameraActivity.setWhiteBlanceRequest(wbMode[random.nextInt(wbMode.length-1)]);
				}
			};
			
			Thread step3_setAEenable = new Thread() {

				@Override
				public void run() {
					cameraActivity.setCameraModeRequest(1); // 1 Auto
				}
			};
			
		
			Thread step4_setEVvalue = new Thread() {

				@Override
				public void run() {
					Random  random = new Random();
					String[] ev = {"-2.0","-1.5","-1.0","-0.5","0.0","0.5","1.0","1.5","2.0"};
					cameraActivity.setExposureValueRequest(ev[random.nextInt(ev.length-1)]);
				}
			};
			
			
			Thread step5_setAEenable = new Thread() {

				@Override
				public void run() {
					cameraActivity.setCameraModeRequest(0); // 0 Manual
				}
			};
			
			
			Thread step6_setISOvalue = new Thread() {
				Random  random = new Random();
				String[] iso = { "100", "150", "200", "300", "400","600", "800", "1600", "3200", "6400"};
				String[] shutterTime = { "30", "60", "125", "250", "500",
						"1000", "2000", "4000", "8000" };
				@Override
				public void run() {
					cameraActivity.setISORequest(iso[random.nextInt(iso.length-1)], shutterTime[random.nextInt(shutterTime.length-1)]);
				}
			};
			
			
			Thread step7_setColor = new Thread() {
				Random  random = new Random();
				String[] color = {"0","1","2","3"};
				@Override
				public void run() {
					cameraActivity.setHueRequest(color[random.nextInt(color.length-1)]);
				}
				
			};
			
			
			Thread step8_setAudioSwitch = new Thread() {
				Random  random = new Random();
				String[] value = {"0","1"};
				@Override
				public void run() {
					cameraActivity.setAudioSwitchRequest(value[random.nextInt(value.length-1)]);
				}
				
			};
			
			
			Thread step9_setPhotoFormat = new Thread() {
				Random  random = new Random();
				String[] value = {"jpg","dng"};
				@Override
				public void run() {
					cameraActivity.setPhotoFormatRequest(value[random.nextInt(value.length-1)]);
				}
				
			};
			
			
			Thread step10_checkRecordTime = new Thread() {

				@Override
				public void run() {
					cameraActivity.checkRecordTime();
				}
				
			};
			
			
			Thread step11_stopRecord = new Thread() {

				@Override
				public void run() {
					cameraActivity.stopRecordRequest();
				}
				
			};
			
			Thread step12_modifyCameraStatus = new Thread() {
				@Override
				public void run() {
					cameraActivity.setCurrentCameraModeRequest(2);
				}
			};
			
			
			Thread step13_takePhoto = new Thread() {

				@Override
				public void run() {
					cameraActivity.takePhotoRequest();
				}
				
			};
			
			
			// offer...
			queue.offer(step00_setResolution);
			queue.offer(step0_modifyCameraStatus);
			queue.offer(step1_startRecord);
			queue.offer(step2_setWB);
			queue.offer(step3_setAEenable);
			queue.offer(step4_setEVvalue);
			queue.offer(step5_setAEenable);
			queue.offer(step6_setISOvalue);
			queue.offer(step7_setColor);
			queue.offer(step8_setAudioSwitch);
			queue.offer(step9_setPhotoFormat);
			queue.offer(step10_checkRecordTime);
			queue.offer(step11_stopRecord);
			queue.offer(step12_modifyCameraStatus);
			queue.offer(step13_takePhoto);
			
		}
		
		
		public void execute() {
			if (queue != null && queue.size() > 0) {
				mThreadPool.schedule(queue.poll(),7, TimeUnit.SECONDS);
			}
		}
		
		public void reset() {
			clear();
			init();
		}
		
		public void clear() {
			queue.clear();
		}
		
	}
	
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mThreadPool != null) {
			mThreadPool.shutdown();
		}
		if (queue != null) {
			queue.clear();
			queue = null;
		}
		LogX.i(TAG, "System Test has destoryed.");
	}
	

}
