package com.yuneec.android.flyingexpert.library.wheelview.adapter;

import android.content.Context;

public final class ISOAdapter extends ArrayWheelAdapter<String> {

	public static final String[] iso = { "100", "150", "200", "300", "400",
			"600", "800", "1600", "3200", "6400" };

	public ISOAdapter(Context context) {
		this(context, iso);
	}

	public ISOAdapter(Context context, String[] items) {
		super(context, items);
	}

	public int getPostionByValue(String value) {
		int position = 0;
		if (value.equals("100")) {
			position = 0;
		} else if (value.equals("150")) {
			position = 1;
		} else if (value.equals("200")) {
			position = 2;
		} else if (value.equals("300")) {
			position = 3;
		} else if (value.equals("400")) {
			position = 4;
		} else if (value.equals("600")) {
			position = 5;
		} else if (value.equals("800")) {
			position = 6;
		} else if (value.equals("1600")) {
			position = 7;
		} else if (value.equals("3200")) {
			position = 8;
		} else if (value.equals("6400")) {
			position = 9;
		}
		return position;
	}

}
