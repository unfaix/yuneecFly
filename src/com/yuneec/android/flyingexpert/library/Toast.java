package com.yuneec.android.flyingexpert.library;

import com.yuneec.android.flyingexpert.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


/**
 * *******************************************************************************
 * Toast
 * @Author yongdaimi
 * @Remark 
 * @Date May 11, 2015 2:11:52 PM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class Toast{

	
	private Toast() {
		
	}


	public static final int LENGTH_SHORT = 2;
	public static final int LENGTH_LONG = 5;
	

	
	public static android.widget.Toast makeText(Context context, CharSequence text, int duration) {
		android.widget.Toast toast = android.widget.Toast.makeText(context, text, duration);
		View view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
		toast.setView(view);
		return toast;
	}
	
	
	public static android.widget.Toast makeText(Context context, int resId, int duration) {
		android.widget.Toast toast = android.widget.Toast.makeText(context, resId, duration);
		View view = LayoutInflater.from(context).inflate(R.layout.widget_toast, null);
		toast.setView(view);
		return toast;
	}
	
	

}
