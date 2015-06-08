package com.yuneec.android.flyingexpert.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;

/**
 * 获取加速度值
 * 
 * @author Administrator
 * 
 */
public abstract class ShakeSensor implements SensorEventListener {
	// 判断当前用是否在摇晃手机
	// x、y、z加速度的值
	private float lastX;// 上一个点在x轴上的加速度值
	private float lastY;// 上一个点在y轴上的加速度值
	private float lastZ;// 上一个点在z轴上的加速度值

	private int interval = 100;// 采样的时间间隔

	private float shake;// 单次采样的增量值
	private float total;// 汇总所有采样的增量值
	private float switchValue = 200;// 判读用户是否在摇晃手机的阈值

	private long currentTime;// 当前时间的记录
	
	private Vibrator vibrator;//处理震动
	private Context context;
	
	

	public ShakeSensor(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// 获取当前节点的加速度值
		float x = event.values[SensorManager.DATA_X];
		float y = event.values[SensorManager.DATA_Y];
		float z = event.values[SensorManager.DATA_Z];
		// 当获取到采样值，有可能（没有达到我们的采样的间隔）

		if ((System.currentTimeMillis() - currentTime) > interval) {

			// if(lastX==0&&lastY==0&&lastZ==0)
			// {
			// //方式一判断当前是刚开始摇晃
			// }

			if (currentTime == 0) {
				// 方式二判断当前是刚开始摇晃
				lastX = x;
				lastY = y;
				lastZ = z;
				currentTime = System.currentTimeMillis();
			} else {
				// 获取三个轴上的增量值
				float increamentX = Math.abs(lastX - x);
				float increamentY = Math.abs(lastY - y);
				float increamentZ = Math.abs(lastZ - z);

				if (increamentX < 1) {
					increamentX = 0;
				}
				if (increamentY < 1) {
					increamentY = 0;
				}
				if (increamentZ < 1) {
					increamentZ = 0;
				}

				shake = increamentX + increamentY + increamentZ;

				total = +shake;
				if (total > switchValue) {
					// 用户在摇晃手机

					// 生成一注彩票
					//方案一：获取到当前的currentView调用getRandom
					//方案二：利用id
					//方案三：
					createPour();
					// 提示用户（震动一下）
					vibrator();
					// 进行清零的操作
					init();
				} else {
					// 将当前点的值进行保存
					lastX = x;
					lastY = y;
					lastZ = z;
					currentTime = System.currentTimeMillis();
				}

			}
		}

	}
	/**
	 * 生成一注彩票
	 */
	public abstract void createPour();
	
	
	/**
	 * 震动100毫秒
	 */
	private void vibrator() {
		if(vibrator==null)
		{
			vibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		}
		
		vibrator.vibrate(100);
	}

	private void init() {
		lastX = 0;
		lastY = 0;
		lastZ = 0;

		currentTime = 0;
	}

}
