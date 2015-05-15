package com.yuneec.android.flyingexpert.library.wheelview.adapter;

import android.content.Context;

/**
 * ****************************************************************
 * ShutterTimeAdapter
 * @Author yongdaimi
 * @Remark
 * @Date Mar 18, 2015 3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ******************************************************************** 
 */
public final class ShutterTimeAdapter extends ArrayWheelAdapter<String> {

	public static final String[] iso = { "30", "60", "125", "250", "500",
			"1000", "2000", "4000", "8000" };

	public ShutterTimeAdapter(Context context) {
		this(context, iso);
	}

	public ShutterTimeAdapter(Context context, String[] items) {
		super(context, items);
	}

	public int getPostionByValue(String value) {
		int position = 0;
		if (value.equals("30")) {
			position = 0;
		} else if (value.equals("60")) {
			position = 1;
		} else if (value.equals("125")) {
			position = 2;
		} else if (value.equals("250")) {
			position = 3;
		} else if (value.equals("500")) {
			position = 4;
		} else if (value.equals("1000")) {
			position = 5;
		} else if (value.equals("2000")) {
			position = 6;
		} else if (value.equals("4000")) {
			position = 7;
		} else if (value.equals("8000")) {
			position = 8;
		}
		return position;
	}

}
