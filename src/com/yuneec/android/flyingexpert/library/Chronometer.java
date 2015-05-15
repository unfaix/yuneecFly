package com.yuneec.android.flyingexpert.library;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;



/**
 * ****************************************************************
 * custom Chronometer
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class Chronometer extends android.widget.Chronometer {

	
	SimpleDateFormat sdf;
	long currentTime;
	
	private long startMilliSecond = 0;
	
	public Chronometer(Context context) {
		super(context);
		
	}

	public Chronometer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public Chronometer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	

	
	
	
	@SuppressLint("SimpleDateFormat")
	private void init() {
	
		sdf = new SimpleDateFormat("HH:mm:ss");
		currentTime = System.currentTimeMillis();
		this.setText("00:00:00");
		this.setOnChronometerTickListener(new MyOnChronometerTickListener());
		
	}


	
	
	
	@Override
	public void start() {
		setBase();
		startMilliSecond = 0;
		currentTime = System.currentTimeMillis();
		super.start();
	}
	
	
	
	
	public void start(long startMilliSecond) {
		this.startMilliSecond = startMilliSecond;
		currentTime = System.currentTimeMillis();
		super.start();
	}
	
	
	
	
	

	@Override
	public void stop() {
		super.stop();
		setBase();
	}

	
	
	public void setBase() {
		setBase(SystemClock.elapsedRealtime());
		setText("00:00:00");
	}
	



	
	
	class MyOnChronometerTickListener implements OnChronometerTickListener {

		@Override
		public void onChronometerTick(android.widget.Chronometer chronometer) {
		
			long tp = System.currentTimeMillis();
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(1970, 1, 1, 0, 0, 0);
			long time = calendar.getTime().getTime();
			
			setText(sdf.format(new Date(time+tp-currentTime+startMilliSecond)));
			
		}
		
	}
	
	
	

}
