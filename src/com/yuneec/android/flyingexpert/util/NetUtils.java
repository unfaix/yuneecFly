package com.yuneec.android.flyingexpert.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetUtil
 * @author yongdaimi
 * @date 2014-8-19 下午05:26:26
 */
public class NetUtils {


	/**
	 * check current net status
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMOBILEConnected(context);
		if (wifiConnected==false & mobileConnected==false) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * check WIFI is connected
	 * @param context
	 * @return
	 */
	public static boolean isWIFIConnected(Context context) {
		
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
	/**
	 * check GPRS is connected
	 * @param context
	 * @return
	 */
	public static boolean isMOBILEConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	
}
