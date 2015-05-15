package com.yuneec.android.flyingexpert.util;


import com.yuneec.android.flyingexpert.settings.AppConfig;

import android.util.Log;

public final class LogX {

	public static void i(String tag, String message) {
		if (AppConfig.developerMode) {
			Log.i(tag, message);
		}
	}
}
