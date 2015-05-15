package com.yuneec.android.flyingexpert;


import com.yuneec.android.flyingexpert.entity.UserInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * User info
 * @author yongdaimi
 * @remark 
 * @date 2014-10-16 上午09:14:20
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class SharedPreference {

	/*User Info*/
	private static final String USER_INFO = "userInfo";
	private static final String USER_INFO_ID = "userId";
	private static final String USER_INFO_NAME = "username";
	private static final String USER_INFO_PASSWORD = "password";
	private static final String USER_INFO_REALLY_NAME = "reallyName";
	private static final String USER_INFO_APPROVE_STATUS = "approveStatus";
	private static final String USER_INFO_SCORE = "score";
	private static final String USER_INFO_ADDRESS = "address";

	
	
	
	

	/**
	 * get current user info
	 * @param context
	 * @return
	 */
	public static final UserInfo getUserInfo(Context context) {
		UserInfo user = new UserInfo();
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				USER_INFO, Context.MODE_PRIVATE);
		user.setUserId(sharedPreferences.getString(USER_INFO_ID, "-1"));
		user.setUsername(sharedPreferences.getString(USER_INFO_NAME, ""));
		user.setPassword(sharedPreferences.getString(USER_INFO_PASSWORD, ""));
		user.setReallyName(sharedPreferences.getString(USER_INFO_REALLY_NAME, ""));
		user.setApproveStatus(sharedPreferences.getString(USER_INFO_APPROVE_STATUS, "0"));
		user.setScore(sharedPreferences.getString(USER_INFO_SCORE, "0"));
		user.setAddress(sharedPreferences.getString(USER_INFO_ADDRESS, ""));
		return user;
	}

	
	
	
	/**
	 * set current user info
	 * @param context
	 * @param user
	 */
	public static final void setUserInfo(Context context, UserInfo user) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				USER_INFO,Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(USER_INFO_ID,user.getUserId());
		editor.putString(USER_INFO_NAME,user.getUsername());
		editor.putString(USER_INFO_PASSWORD,user.getPassword());
		editor.putString(USER_INFO_REALLY_NAME,user.getReallyName());
		editor.putString(USER_INFO_APPROVE_STATUS,user.getApproveStatus());
		editor.putString(USER_INFO_SCORE,user.getScore());
		editor.putString(USER_INFO_ADDRESS,user.getAddress());
		editor.commit();
	}
	
	
	
	
}
