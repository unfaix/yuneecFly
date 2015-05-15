package com.yuneec.android.flyingexpert.logic.rtsp;

/**
 * ****************************************************************
 *  the RTSP command
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public interface RTSPCommand {

	
	
	// init camera
	public static final String URL_INIT_CAMERA = "INDEX_PAGE";
	
	// set white blance
	public static final String URL_SET_WHITEBLANCE_MODE = "SET_WHITEBLANCE_MODE";
	
	// stop record
	public static final String URL_STOP_RECORD = "STOP_RECORD";
	
	// stop record
	public static final String URL_START_RECORD = "START_RECORD";
	
	// set resolution
	public static final String URL_SET_VIDEO_MODE = "SET_VIDEO_MODE";
	
	// set hue
	public static final String URL_SET_IQ_TYPE = "SET_IQ_TYPE";
	
	// get space free
	public static final String URL_GET_SPACE_FREE = "GET_SPACE_FREE";
	
	// set exposure value
	public static final String URL_SET_EXPOSURE_VALUE = "SET_EXPOSURE_VALUE";
	
	// set iso&shuttime
	public static final String URL_SET_SH_TM_ISO = "SET_SH_TM_ISO";
	
	// set cameraMode
	public static final String URL_SET_AE_ENABLE = "SET_AE_ENABLE";
	
	// take photo
	public static final String URL_TAKE_PHOTO = "TAKE_PHOTO";
	
	// set photo format
	public static final String URL_SET_PHOTO_FORMAT = "SET_PHOTO_FORMAT";
	
	// get current version
	public static final String URL_GET_FW_VERSION = "GET_FW_VERSION";
	
	// get resolution
	public static final String URL_GET_VIDEO_MODE = "GET_VIDEO_MODE";
	
	// get hue
	public static final String URL_GET_IQ_TYPE = "GET_IQ_TYPE";
	
	// get photo format
	public static final String URL_GET_PHOTO_FORMAT = "GET_PHOTO_FORMAT";
	
	// set audio switch
	public static final String URL_SET_AUDIO_SW = "SET_AUDIO_SW";
	
	// get audio switch
	public static final String URL_GET_AUDIO_SW = "GET_AUDIO_SW";
	
	// set current time
	public static final String URL_SET_TIME = "SET_TIME";
	
	// format sdcard
	public static final String URL_FORMAT_SDCARD = "FORMAT_CARD";
	
	// reset settings
	public static final String URL_RESET_SETTINGS = "RESET_DEFAULT";
	
	// get current status
	public static final String URL_GET_STATUS = "GET_STATUS";
	
	// get camera mode
	public static final String URL_GET_CAMERA_MODE = "GET_CAM_MODE";
	
	// set camera mode
	public static final String URL_SET_CAMERA_MODE = "SET_CAM_MODE";
	
	
	
}
