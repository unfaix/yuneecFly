package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

/**
 * *******************************************************************************
 *  CGO4 RTSPcommand
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 9, 2015 12:06:01 PM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public enum CGO4RTSPcommand {

	
	/** Get Microphone Volume*/
	GET_MICROPHONE_VOLUME("getsetting","mic_level",false),
	
	/** Set Microphone Volume*/
	SET_MICROPHONE_VOLUME("setsetting","mic_level",false),
	
	/** Take Photo*/
	TAKE_PHOTO("camcmd","capture",true),
	
	/** Start Record*/
	START_RECORD("camcmd","video_recstart",true),
	
	/** Stop Record*/
	STOP_RECORD("camcmd","video_recstop",true),
	
	/** Reset Arguments*/
	RESET_ARGUS("setsetting","systemmenu","reset"),
	
	/** Set Time*/
	SET_TIME("setsetting","clock",false),
	
	/** Format SDcard*/
	FORMAT_SDCARD("editcmd","format","sd"),
	
	/** Get Format Process*/
	GET_FORMAT_PROCESS("getprogress","format",false),
	
	/** Start Send UDP Data*/
	START_SEND_UDP_DATA("startstream","port",true),
	
	/** Stop Send UDP Data*/
	STOP_SEND_UDP_DATA("stopstream"),
	
	/** AF/AE Lock On*/
	AE_LOCK_ON("camctrl","af_ae_lock","on"),
	
	/** AF/AE Lock Off*/
	AE_LOCK_OFF("camctrl","af_ae_lock","off"),
	
	/** Adjust Focus Increase*/
	ADJUST_FOCUS_INCREASE("camcmd","wide-normal",true),
	
	/** Adjust Focus Decrease*/
	ADJUST_FOCUS_DECREASE("camcmd","tele-normal",true),
	
	/** Manual Adjust Focus Increase*/
	CAMCTRL_ADJUST_FOCUS_INCREASE("camctrl","focus","wide-normal"),
	
	/** Manual Adjust Focus Decrease*/
	CAMCTRL_ADJUST_FOCUS_DECREASE("camctrl","focus","tele-normal"),
	
	/** Stop Focus Change*/
	STOP_FOCUS_CHANGE("camcmd","zoomstop",true),
	
	/** INIT*/
	INIT("camcmd","recmode",true),
	
	/** Programe Shift Entry*/
	PROGRAME_SHIFT_ENTRY("camctrl","program_shift","entry"),
	
	/** Programe Shift Exit*/
	PROGRAME_SHIFT_EXIT("camctrl","program_shift","exit"),
	
	/** Programe Shift Down*/
	PROGRAME_SHIFT_DOWN("camctrl","program_shift","down"),
	
	/** Programe Shift Up*/
	PROGRAME_SHIFT_UP("camctrl","program_shift","up"),
	
	/** Get SCENE INFO*/
	GET_SCENE_INFO("getinfo","lens",false),
	
	/** Get Status*/
	GET_STATUS("getstate"),
	
	/** Exposure Value*/
	SET_EXPOSURE_VALUE("setsetting","exposure",false),
	
	/** Get White Balance*/
	GET_WHITE_BALANCE("getsetting","whitebalance",false),
	
	/** Set White Balance*/
	SET_WHITE_BALANCE("setsetting","whitebalance",false),
	
	/** Get ISO*/
	GET_ISO_VALUE("getsetting","iso",false),
	
	/** Set ISO*/
	SET_ISO_VALUE("setsetting","iso",false),
	
	/** Shutter Speed*/
	SET_SHUTTER_SPEED("setsetting","shtrspeed",false),
	
	/** Aperture*/
	SET_APERTURE("setsetting","remote_rec_mode",false),
	
	/** Get Mode*/
	GET_MODE("getsetting","remote_rec_mode",false);
	
	
	
	private final String cmd;
	
	
 	private CGO4RTSPcommand(String mode) {
 		this.cmd = mode;
 	}
 	
 	
 	private CGO4RTSPcommand(String mode, String typeORvalue, boolean hasValue) {
 		if (hasValue) {
 			this.cmd = mode+"&value="+typeORvalue;
		} else {
			this.cmd = mode+"&type="+typeORvalue;
		}
 	}
 	
 	
 	private CGO4RTSPcommand(String mode, String type, String value) {
 		this.cmd = mode+"&type="+type+"&value="+value;
 	}
 	
 	
 	
 	
 	public String cmd() {return cmd;};
 	
	
}