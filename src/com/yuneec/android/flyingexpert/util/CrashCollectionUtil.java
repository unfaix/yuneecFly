package com.yuneec.android.flyingexpert.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.yuneec.android.flyingexpert.settings.MyApplication;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;



/**
 * CrashCollectionUtil
 * @author yongdaimi
 * @remark 
 * @date 2015-3-2 上午11:25:35
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class CrashCollectionUtil implements UncaughtExceptionHandler {

	
	private static final String TAG = "ERRORINFO";
	
	private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();
	
	private CrashCollectionUtil(){};
	
	private Context mContext;
	
	private static CrashCollectionUtil instance = new CrashCollectionUtil();
	
	
	public static CrashCollectionUtil getInstance() {
		return instance;
	}
	
	
	public void setCustomCrashHandler(Context context) {
		mContext = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// save current exception info
		saveErrorInfo2SD(mContext,ex);
		// give tips to user
		showToast(mContext, "...@_@...");
		try {
			thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// exit app
		MyApplication.getInstance().exit();
	}


	/**
	 *  save errorinfo to SDcard
	 * @param ex
	 */
	private String saveErrorInfo2SD(Context context, Throwable ex) {
		String fileName = null;
		StringBuffer sb = new StringBuffer();
		
		for (Map.Entry<String, String> entry : collectDeviceInfo(context).entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append(":").append(value).append("\n");
		}
		
		sb.append(collectExceptionInfo(ex));
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File dir = new File(SDCARD_ROOT+File.separator+"crash"+File.separator);
			if (!dir.exists()) {
				dir.mkdir();
			}
			fileName = dir.toString()+File.separator+paserTime(System.currentTimeMillis())+".log";
			try {
				FileOutputStream fos = new FileOutputStream(fileName);
				fos.write(sb.toString().getBytes());
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
	
	
	
	/**
	 * get current system info(the Android version, App version, Telephone version, Telephone argus
	 */
	private HashMap<String, String> collectDeviceInfo(Context context) {
		HashMap<String, String> map = new HashMap<String, String>();
		PackageManager mPackageManager = context.getPackageManager();
		PackageInfo mPackageInfo = null;
		try {
			mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		map.put("versionName", mPackageInfo.versionName);
		map.put("versionCode", mPackageInfo.versionCode+"");
		// Version 
		map.put("MODEL", Build.MODEL+""); // Hardware info
		map.put("SDK_INT", Build.VERSION.SDK_INT+""); // Android version
		map.put("BRAND", Build.BRAND); // Android relase name
		map.put("PRODUCT", Build.PRODUCT); // Company
		map.put("DISPLAY", Build.DISPLAY); // Telephone Screen argus
		map.put("HARDWARE", Build.FINGERPRINT); // Hardware name
		return map;
	}
	
	
	
	private String collectExceptionInfo(Throwable throwable) {
		StringWriter mStringWriter = new StringWriter();
		PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
		throwable.printStackTrace(mPrintWriter);
		mPrintWriter.close();
		Log.e(TAG, mStringWriter.toString());
		return mStringWriter.toString();
	}

	
	/**
	 * format time
	 * @param milliseconds
	 * @return
	 */
	private String paserTime(long milliseconds) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");  
        TimeZone.setDefault(tz);  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
        String times = format.format(new Date(milliseconds));  
        return times;  
	}
	
	
	
	private void showToast(final Context context, final String msg) {
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						Looper.prepare();
						Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
						Looper.loop();
					}
				}
		).start();
	}
	
}
