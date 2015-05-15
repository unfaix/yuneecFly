package com.yuneec.android.flyingexpert.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * **************************************************************** Math Util
 * 
 * @Author yongdaimi
 * @Remark
 * @Date Mar 18, 2015 3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ******************************************************************** 
 */
public class MathUtil {

	public static String getDecimalFormat2(Double val) {

		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(val);

	}

	public static String getDecimalFormat1(Double val) {

		DecimalFormat df = new DecimalFormat("#0.0");
		return df.format(val);

	}

	
	
	public static String round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");

		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return String.valueOf(b.divide(one, scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue());
	}

}
