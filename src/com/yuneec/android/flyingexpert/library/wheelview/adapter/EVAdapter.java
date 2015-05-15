package com.yuneec.android.flyingexpert.library.wheelview.adapter;

import android.content.Context;

public final class EVAdapter extends ArrayWheelAdapter<String> {

	public static final String[] iso = { "-2.0", "-1.5", "-1.0", "-0.5", "0.0","0.5", "1.0", "1.5", "2.0" };

	
	public int getPostionByValue(String value) {
		int position = 0;
		if (value.equals("-2.0")) {
			position = 0;
		} else if (value.equals("-1.5")) {
			position = 1;
		} else if (value.equals("-1.0")) {
			position = 2;
		} else if (value.equals("-0.5")) {
			position = 3;
		} else if (value.equals("0.0")) {
			position = 4;
		} else if (value.equals("0.5")) {
			position = 5;
		} else if (value.equals("1.0")) {
			position = 6;
		} else if (value.equals("1.5")) {
			position = 7;
		}	else if (value.equals("2.0")) {
			position = 8;
		}
		return position;
	}

	public EVAdapter(Context context) {
		this(context, iso);
	}

	public EVAdapter(Context context, String[] items) {
		super(context, items);
	}

}
