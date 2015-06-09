package com.yuneec.android.flyingexpert.logic.cgo3.rtsp;

/**
 * ****************************************************************
 *  the RTSP command
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public enum CGO3_RTSPcommand {

	/** init camera */
	URL_INIT_CAMERA("INDEX_PAGE"),
	
	/** set white blance */
	URL_SET_WHITEBLANCE_MODE("SET_WHITEBLANCE_MODE"),
	
	/** stop record */
	URL_STOP_RECORD("STOP_RECORD"),
	
	/** start record */
	URL_START_RECORD("START_RECORD"),
	
	/** set resolution */
	URL_SET_VIDEO_MODE("SET_VIDEO_MODE"),
	
	/** set hue */
	URL_SET_IQ_TYPE("SET_IQ_TYPE"),
	
	/** get space free */
	URL_GET_SPACE_FREE("GET_SPACE_FREE"),
	
	/** set exposure value */
	URL_SET_EXPOSURE_VALUE("SET_EXPOSURE_VALUE"),
	
	/** set iso&shuttime */
	URL_SET_SH_TM_ISO("SET_SH_TM_ISO"),
	
	/** set cameraMode */
	URL_SET_AE_ENABLE("SET_AE_ENABLE"),
	
	/** take photo */
	URL_TAKE_PHOTO("TAKE_PHOTO"),
	
	/** set photo format */
	URL_SET_PHOTO_FORMAT("SET_PHOTO_FORMAT"),
	
	/** get current version */
	URL_GET_FW_VERSION("GET_FW_VERSION"),
	
	/** get resolution */
	URL_GET_VIDEO_MODE("GET_VIDEO_MODE"),
	
	/** get hue */
	URL_GET_IQ_TYPE("GET_IQ_TYPE"),
	
	/** get photo format */
	URL_GET_PHOTO_FORMAT("GET_PHOTO_FORMAT"),
	
	/** set audio switch */
	URL_SET_AUDIO_SW("SET_AUDIO_SW"),
	
	/** get audio switch */
	URL_GET_AUDIO_SW("GET_AUDIO_SW"),
	
	/** set current time */
	URL_SET_TIME("SET_TIME"),
	
	/** format sdcard */
	URL_FORMAT_SDCARD("FORMAT_CARD"),
	
	/** reset settings */
	URL_RESET_SETTINGS("RESET_DEFAULT"),
	
	/** get current status */
	URL_GET_STATUS("GET_STATUS"),
	
	/** get camera mode */
	URL_GET_CAMERA_MODE("GET_CAM_MODE"),
	
	/** set camera mode */
	URL_SET_CAMERA_MODE("SET_CAM_MODE");
	
	
	
	private final String cmd;
		
	private CGO3_RTSPcommand(String cmd) {
		this.cmd = cmd;
	}

	public String cmd() { return cmd; }
	
	
}
