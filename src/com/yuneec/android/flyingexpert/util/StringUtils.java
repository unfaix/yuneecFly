package com.yuneec.android.flyingexpert.util;

/**
 * Apache commons-lang 3
 * @author yongdaimi
 * @remark
 * @date 2015-2-12 下午04:00:58
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class StringUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

}