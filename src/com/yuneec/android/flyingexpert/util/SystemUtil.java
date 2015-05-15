package com.yuneec.android.flyingexpert.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

/**
 * SystemUtil 
 * @author yongdaimi
 * @tip 
 * @date 2014-8-29 下午04:21:32
 */
public class SystemUtil {

	
	/**
	 * getVersionCode
	 * @param context
	 * @return
	 */
	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	
	
	/**
	 * getVersionName;
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "beta";
	}
	
	
	
	/**
	 * getAvaiMemory
	 * @param context
	 * @return
	 */
	public final static long getAvailMem(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		android.app.ActivityManager.MemoryInfo outInfo = new android.app.ActivityManager.MemoryInfo();
		am.getMemoryInfo(outInfo);
		long availMem = outInfo.availMem; // 单位是byte
		return availMem;
	}

	/**
	 * getAvaiMemoryOfMB
	 * @param context
	 * @return 单位：MB
	 */
	public final static int getAvailMemOfMB(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		android.app.ActivityManager.MemoryInfo outInfo = new android.app.ActivityManager.MemoryInfo();
		am.getMemoryInfo(outInfo);
		int availMem = (int) (outInfo.availMem) / 1024 / 1024; // 单位是byte
		return availMem;
	}

	
	
	
	/*
	 * checkBlueTooth
	 */
	public static boolean checkBlueTooth() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothAdapter != null) {
			return true;
		}
		return false;
	}
	
	
	/*
	 * getUsableSpace for SDcard
	 */
	public static final int getUsableSpace() {
		if (hasSDCard()) {
			long currentSpace = Environment.getExternalStorageDirectory()
					.getUsableSpace();
			int usableSpace = (int) (currentSpace / 1024 / 1024);
			return usableSpace;
		}
		return 0;
	}
	
	
	/**
	 * get disk cache directory
	 * @param context
	 * @param uniqueName 
	 *  For example：bitmap for picture  music for mp3  txt ..
	 * @return
	 */
	public static String getDiskCacheDir(Context context, String uniqueName) {

		String cachePath = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = Environment.getExternalStorageDirectory().getPath()
					+ "/Android/data/" + context.getPackageName()
					+ File.separator + "cache/" + uniqueName;
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath;
	}
	
	
	
	
	/**
	 * getCurrentMaxRunningMemory
	 * @return
	 */
	public static int getAppMaxRunningMemory() {
//		ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//		int mMaxMemory = mActivityManager.getMemoryClass();
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		return maxMemory;
	}
	
	
	/**
	 * check SDcard has mounted
	 * @return
	 */
	public static boolean hasSDCard() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * calculate file nums
	 * @param path
	 * @return
	 */
	public static int calculateTotalCountFromFolder(String path) {
		File fileDirectory = new File(path);
		if (fileDirectory.isDirectory()) {
			return fileDirectory.listFiles().length;
		}
		return 0;
	}
	
	
	/*
	 * delete file
	 */
	public static boolean deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				return file.delete();
			} else if (file.isDirectory()) {
				File[] delFiles = file.listFiles();
				for (int i = 0; i < delFiles.length; i++) {
					deleteFile(delFiles[i]);
				}
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * delete multi files
	 * @param filePaths
	 */
	public static void deleteMultiFile(List<String> filePaths) {
		for (String path : filePaths) {
			File tempFile = new File(path);
			deleteFile(tempFile);
		}
	}
	
	
	/**
	 * copy file
	 * @param srcPath source poi
	 * @param desPath des poi
	 */
	public static void copyFileToTargetFolder(String srcPath, String desPath) {
		
		File tempFile = new File(srcPath);
		if (!tempFile.exists()) {
			return;
		}
		
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(tempFile));
			outputStream = new BufferedOutputStream(new FileOutputStream(new File(desPath)));
			int len = 0;
			byte[] bytes = new byte[1024];
			while((len = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes,0,len);
				outputStream.flush();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	/**
	 * dip2px
	 * @param mActivity
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	
	
	
	/**
	 * get file size
	 * @param file current directory
	 * @return
	 */
	public static double getDirSize(File file) {     
        // check file exists
        if (file.exists()) {     
            // calculate file fize
            if (file.isDirectory()) {     
                File[] children = file.listFiles();     
                double size = 0;     
                for (File f : children)     
                    size += getDirSize(f);     
                return size;     
            } else {
            	// return fize for "MB"
                double size = (double) file.length() / 1024 / 1024;        
                return size;     
            }     
        } else {     
            // System.out.println("path is error");     
            return 0.0;     
        }     
    }
	
	
	
	
	/**
	 * get cache size
	 * @param file
	 * @return
	 */
	public static String getCacheSize(File file) {
		java.text.DecimalFormat   df   =new  java.text.DecimalFormat("#.00");
		String result  = "";
		double dirSize = getDirSize(file);
		if (dirSize == 0.0 || dirSize == 0) {
			result =  "0";
		} else if (dirSize > 0 && dirSize < 1){
			result = "0"+df.format(dirSize);
		} else {
			result = df.format(dirSize);
		}
		return result;
	}
	
	
	/**
	 * get WIFI info
	 * @param context
	 * @return
	 */
	public static String getConnectionWIFIName(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo connectionInfo = wifiManager.getConnectionInfo();
		return connectionInfo.getSSID();
	}
	
	
	
}
