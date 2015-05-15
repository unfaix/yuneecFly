package com.yuneec.android.flyingexpert.util;

/**
 * ****************************************************************
 * the camera util
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class CameraUtil {

	
	
	public static String getWhiteBalance(int wb) {
		String ret = "null";
		if (wb == 0)
			ret = "Auto";
		else if (wb == 1)
			ret = "Ncandescent";
		else if (wb == 4)
			ret = "Sunny";
		else if (wb == 5)
			ret = "Cloudy";
		else if (wb == 7)
			ret = "Fluorescent";
		else if (wb == 10)
			ret = "Custom";

		return ret;
	}
	

	
	public static String getMode(int mode) {
		String ret = "null";
		if (mode == 0)
			ret = "Cool";
		else if (mode == 1)
			ret = "Warm";
		return ret;

	}
	
	
	
	
	public static String getSharpness(int num) {

		String ret = "null";

		if (num == 3)
			ret = "Low";
		else if (num == 6)
			ret = "Middle";
		else
			ret = "High";
		return ret;

	}
	
	

}
