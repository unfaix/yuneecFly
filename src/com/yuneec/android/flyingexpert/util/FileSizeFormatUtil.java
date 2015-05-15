package com.yuneec.android.flyingexpert.util;

/**
 * ****************************************************************
 * FileSize format util
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class FileSizeFormatUtil {

	
	private static final int GB_SIZE = 1024*1024*1024;
	private static final int MB_SIZE = 1024*1024;
	private static final int KB_SIZE = 1024;
	
	
	public static String getFileSize(int fileSize) {
		String result = "";
		if (fileSize < MB_SIZE) {
			result = MathUtil.getDecimalFormat1((double)(fileSize / KB_SIZE)) + "KB"; 
		} else if (fileSize > MB_SIZE && fileSize < GB_SIZE) {
			result = MathUtil.getDecimalFormat1((double)(fileSize / MB_SIZE)) + "MB";
		} else if (fileSize > GB_SIZE) {
			result = MathUtil.getDecimalFormat1((double)(fileSize / GB_SIZE)) + "GB";
		}
		return result;
	}
	
	
	

}
