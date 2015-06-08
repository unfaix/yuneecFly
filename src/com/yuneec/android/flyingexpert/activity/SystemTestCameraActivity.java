package com.yuneec.android.flyingexpert.activity;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import tv.danmaku.ijk.media.widget.VideoView;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.animation.AnimationUtils;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.HighlightAnimation;
import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.library.AlertDialog;
import com.yuneec.android.flyingexpert.library.Chronometer;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.CGO3_RtspRequestManager;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.FormatSDcardRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.GetCurrentStatusRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.GetSDcardFreeRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.InitCameraRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetAudioSwitchRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetCameraModeRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetCurrentCameraModeRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetCurrentTimeRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetExposureValueRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetHueRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetISORequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetPhotoFormatRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetResolutionRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.SetWhiteBlanceRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.StartRecordRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.StopRecordRequest;
import com.yuneec.android.flyingexpert.logic.cgo3.rtsp.impl.TakePhotoRequest;
import com.yuneec.android.flyingexpert.service.SystemAutoTestService;
import com.yuneec.android.flyingexpert.service.SystemAutoTestService.MyBinder;
import com.yuneec.android.flyingexpert.util.CommonUtils;
import com.yuneec.android.flyingexpert.util.LogX;
import com.yuneec.android.flyingexpert.util.MathUtil;
import com.yuneec.android.flyingexpert.library.wheelview.AbstractWheel;
import com.yuneec.android.flyingexpert.library.wheelview.OnWheelChangedListener;
import com.yuneec.android.flyingexpert.library.wheelview.OnWheelClickedListener;
import com.yuneec.android.flyingexpert.library.wheelview.OnWheelScrollListener;
import com.yuneec.android.flyingexpert.library.wheelview.WheelVerticalView;
import com.yuneec.android.flyingexpert.library.wheelview.adapter.ISOAdapter;
import com.yuneec.android.flyingexpert.library.wheelview.adapter.EVAdapter;
import com.yuneec.android.flyingexpert.library.wheelview.adapter.ShutterTimeAdapter;


/**
 * ****************************************************************************
 * Camera UI
 * @Author yongdaimi
 * @Remark Chronometer format "H:MM:SS" schedual 10 seconds
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 *****************************************************************************
 */
public class SystemTestCameraActivity extends BaseActivity {

	
	private VideoView vv_scene;
	private 	RelativeLayout bt_settings;
	private ImageView iv_scene;
	
	
	private TextView tv_space;
	private Button bt_record;
	private TextView tv_record_duration;
	private Chronometer ch_record_time;
	private ImageView iv_home;
	private ImageView iv_album;
	private Button bt_shoot_or_take;
	
	
	
	private LinearLayout bt_wb;
	private LinearLayout bt_iso;
	private LinearLayout ll_record_duration;
	
	private ImageView iv_wb;
	private ImageView iv_sun;
	private ImageView iv_iso;
	private TextView tv_shutter_time;
	private WheelVerticalView wv_ev_value;
	
	
    // Time changed flag
    private boolean timeChanged = false;
    // Time scrolled flag
    private boolean timeScrolled = false;
    
	
    //----------------------------------
    //	    System Test
    //----------------------------------
    private TextView tv_test_status;
    private Button bt_start_test;
    private Button bt_end_test;
    private Button bt_format_sdcard;
    private TextView tv_time_status;
    private TextView tv_resolution_status;
    
    
    //----------------------------------
    //	    WB menu options
    //----------------------------------
	private View menu_sub_wb;
	private Button bt_wb_awb;
	private Button bt_wb_lock;
	private Button bt_wb_daylight;
	
	private Button bt_wb_cloudy;
	private Button bt_wb_flucrescent;
	private Button bt_wb_incandescent;
	private Button bt_wb_sunset;
	private Button bt_wb_custom;
	
	
	//-----------------------------------------
    //	    ISO EV menu options
    //-----------------------------------------
	private View menu_sub_iso_ev;
	private ImageView iv_iso_ev_mode;
	
	
	
	//-------------------------------------------
    //	    ISO Manual menu options
    //--------------------------------------------
	private View menu_sub_iso_manual;
	private ImageView iv_iso_manual_mode;
	private WheelVerticalView wv_iso_value;
	private WheelVerticalView wv_shutter_time_value;
	
	
	
    //------------------------- Camera Global arguments ---------------------
	private int cameraMode = 1; // 2 Take Photo 1.Video
	private int wbMode = 0; // auto lock sunny ...
	
	private int aeMode = 1; // 1.auto 0.manual
	
	private String ev_value = "0";
	private String iso_value = "100";
	private String shutterTime_value = "30";
	//------------------------- Camera Global arguments ---------------------
	
	
	private InitCameraRequest mInitCameraRequest;
	private SetWhiteBlanceRequest mSetWhiteBlanceRequest;
	private StopRecordRequest mStopRecordRequest;
	private StartRecordRequest mStartRecordRequest;
	private GetSDcardFreeRequest mGetSDcardFreeRequest;
	private SetExposureValueRequest mSetExposureValueRequest;
	private SetISORequest mSetISORequest;
	private SetCameraModeRequest mSetCameraModeRequest;
	private TakePhotoRequest mTakePhotoRequest;
	private SetCurrentTimeRequest mSetCurrentTimeRequest;
	private GetCurrentStatusRequest mGetCurrentStatusRequest;
	private SetCurrentCameraModeRequest mSetCurrentCameraModeRequest;
	
	private SetPhotoFormatRequest mSetPhotoFormatRequest;
	private SetHueRequest mSetHueRequest;
	private SetAudioSwitchRequest mSetAudioSwitchRequest;
	private FormatSDcardRequest mFormatSDcardRequest;
	private SetResolutionRequest mSetResolutionRequest;
	
	
	private static final String mVideoPath = "rtsp://192.168.42.1/stream1"; // videoPath
	private String[] curValue = {};
	
	
	private SoundPool mSoundPool;
	private ScheduledExecutorService mSingleThreadScheduledExecutor = null;
	private static final int INTERVAL = 10; // ThreadPool schedule interval
	
	
	
	@Override
	protected void setContainer() {
		setContentView(R.layout.system_test_activity_camera);
	}

	
	
	@Override
	protected void init() {
		
		 initUIView();
		 initMenuView();
		 
	}




	private void initWBMenu() {
		
		/* WB */
		menu_sub_wb = getView(R.id.menu_sub_wb);
		bt_wb_awb = getView(R.id.bt_wb_awb);
		bt_wb_lock = getView(R.id.bt_wb_lock);
		bt_wb_daylight = getView(R.id.bt_wb_daylight);
		bt_wb_cloudy = getView(R.id.bt_wb_cloudy);
		bt_wb_flucrescent = getView(R.id.bt_wb_flucrescent);
		bt_wb_incandescent = getView(R.id.bt_wb_incandescent);
		bt_wb_sunset = getView(R.id.bt_wb_sunset);
		bt_wb_custom = getView(R.id.bt_wb_custom);
		
		
		bt_wb_awb.setOnClickListener(new WBOnClickListener());
		bt_wb_lock.setOnClickListener(new WBOnClickListener());
		bt_wb_daylight.setOnClickListener(new WBOnClickListener());
		bt_wb_cloudy.setOnClickListener(new WBOnClickListener());
		bt_wb_flucrescent.setOnClickListener(new WBOnClickListener());
		bt_wb_incandescent.setOnClickListener(new WBOnClickListener());
		bt_wb_sunset.setOnClickListener(new WBOnClickListener());
		bt_wb_custom.setOnClickListener(new WBOnClickListener());
		
	}

	
	
	
	private void initMenuView() {
		
		bt_wb = getView(R.id.bt_wb);
		bt_iso = getView(R.id.bt_iso);
		
		iv_wb = getView(R.id.iv_wb);
		iv_sun = getView(R.id.iv_sun);
		iv_iso = getView(R.id.iv_iso);
		
		tv_shutter_time = getView(R.id.tv_shutter_time);
		
		initAdapter();
		
		initWBMenu();
		initEvMenu();
		initIsoMenu();
		
	}



	private void initAdapter() {
		
		evAdapter = new EVAdapter(getApplicationContext());
		isoAdapter = new ISOAdapter(getApplicationContext());
		shutterTimeAdapter = new ShutterTimeAdapter(getApplicationContext());
		
	}



	private void initIsoMenu() {
		menu_sub_iso_manual = getView(R.id.menu_sub_iso_manual);
		iv_iso_manual_mode = getView(R.id.iv_iso_manual_mode);
		
		wv_iso_value = getView(R.id.wv_iso_value);
		/*mISOAdapter.setItemResource(R.layout.wheel_text_centered_dark_back);
		mISOAdapter.setItemTextResource(R.id.text);*/
		
		wv_iso_value.setViewAdapter(isoAdapter);
		wv_iso_value.setCyclic(false);
		
		wv_shutter_time_value = getView(R.id.wv_shutter_time_value);
		wv_shutter_time_value.setViewAdapter(shutterTimeAdapter);
		wv_shutter_time_value.setCyclic(false);
		
		
		addChangingListener(wv_iso_value, "wv_iso_value");
		 
		OnWheelChangedListener iso_wheelListener = new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;
                    timeChanged = false;
                } else {
                	mSoundPool.play(mWheelSound, 1, 1, 0, 0, 1);
                }
            }
        };
        
        wv_iso_value.addChangingListener(iso_wheelListener);
        
        OnWheelClickedListener iso_click = new OnWheelClickedListener() {
            public void onItemClicked(AbstractWheel wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        
        wv_iso_value.addClickingListener(iso_click);
        
        
        OnWheelScrollListener iso_scrollListener = new OnWheelScrollListener() {
        	
            public void onScrollingStarted(AbstractWheel wheel) {
                timeScrolled = true;
            }
            public void onScrollingFinished(AbstractWheel wheel) {
                timeScrolled = false;
                timeChanged = true;
                timeChanged = false;
              
                int currentItem = wheel.getCurrentItem();
                LogX.i("test", "onScrollingFinished"+"wv_iso_value:current item is:"+currentItem);
                iso_value = ISOAdapter.iso[currentItem];
                setISORequest(iso_value,shutterTime_value);
            }
			
        };
		
        wv_iso_value.addScrollingListener(iso_scrollListener);
	    
	    
        
		addChangingListener(wv_shutter_time_value, "wv_shutter_time_value");
		 
		OnWheelChangedListener shutter_time_wheelListener = new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;
                    timeChanged = false;
                } else {
                	mSoundPool.play(mWheelSound, 1, 1, 0, 0, 1);
                }
            }
        };
        
        wv_shutter_time_value.addChangingListener(shutter_time_wheelListener);
        
        OnWheelClickedListener shutter_time_click = new OnWheelClickedListener() {
            public void onItemClicked(AbstractWheel wheel, int itemIndex) {
            	wheel.setCurrentItem(itemIndex, true);
            }
        };
        
         wv_shutter_time_value.addClickingListener(shutter_time_click);
        
        
        OnWheelScrollListener shutter_time_scrollListener = new OnWheelScrollListener() {
        	
            public void onScrollingStarted(AbstractWheel wheel) {
                timeScrolled = true;
            }
            public void onScrollingFinished(AbstractWheel wheel) {
                timeScrolled = false;
                timeChanged = true;
                timeChanged = false;
              
                int currentItem = wheel.getCurrentItem();
                LogX.i("test", "onScrollingFinished"+"wv_shutter_time_value:current item is:"+currentItem);
                shutterTime_value = ShutterTimeAdapter.iso[currentItem];
                setISORequest(iso_value,shutterTime_value);
            }
        };
		
        wv_shutter_time_value.addScrollingListener(shutter_time_scrollListener);
	    
	}


	
	
	//-----------------------------------------
    //	          Adapter Value
    //-----------------------------------------
	
	private EVAdapter evAdapter;
	private ISOAdapter isoAdapter;
	private ShutterTimeAdapter shutterTimeAdapter;
	

	/**
	 * init EV menu
	 */
	private void initEvMenu() {
		
		menu_sub_iso_ev = getView(R.id.menu_sub_iso_ev);
		iv_iso_ev_mode = getView(R.id.iv_iso_ev_mode);
		wv_ev_value = getView(R.id.wv_ev_value);
		
		/*mISOEvAdapter.setItemResource(R.layout.wheel_text_centered_dark_back);
		mISOEvAdapter.setItemTextResource(R.id.text);*/
		
		wv_ev_value.setViewAdapter(evAdapter);
		wv_ev_value.setCyclic(false);
		
		addChangingListener(wv_ev_value, "iso_ev_list");
		
		OnWheelChangedListener ev_wheelListener = new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;
                    timeChanged = false;
                } else {
                	mSoundPool.play(mWheelSound, 1, 1, 0, 0, 1);
                }
            }
        };
        
        wv_ev_value.addChangingListener(ev_wheelListener);
        
        OnWheelClickedListener ev_click = new OnWheelClickedListener() {
            public void onItemClicked(AbstractWheel wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        
        wv_ev_value.addClickingListener(ev_click);
        
        OnWheelScrollListener ev_scrollListener = new OnWheelScrollListener() {
        	
            public void onScrollingStarted(AbstractWheel wheel) {
                timeScrolled = true;
            }
            public void onScrollingFinished(AbstractWheel wheel) {
                timeScrolled = false;
                timeChanged = true;
                timeChanged = false;
              
                int currentItem = wheel.getCurrentItem();
                LogX.i("test", "onScrollingFinished"+"wv_ev_value:current item is:"+currentItem);
                ev_value = EVAdapter.iso[currentItem];
        		setExposureValueRequest(ev_value);
            }
        };
		
	    wv_ev_value.addScrollingListener(ev_scrollListener);
	    
	}





	private void initUIView() {
		
		vv_scene = getView(R.id.vv_scene);
		bt_settings = getView(R.id.bt_settings);
		iv_scene = getView(R.id.iv_scene);
		
		tv_space = getView(R.id.tv_space);
		bt_record = getView(R.id.bt_record);
		iv_home = getView(R.id.iv_home);
		iv_album = getView(R.id.iv_album);
		bt_shoot_or_take = getView(R.id.bt_shoot_or_take);
		
		ll_record_duration = getView(R.id.ll_record_duration);
		tv_record_duration = getView(R.id.tv_record_duration);
		ch_record_time = getView(R.id.ch_record_time);
		
		/*------------------------------------> System Test <-------------------------------------------*/
		tv_test_status = getView(R.id.tv_test_status); 
		bt_start_test = getView(R.id.bt_start_test);
		bt_end_test = getView(R.id.bt_end_test);
		bt_format_sdcard = getView(R.id.bt_format_sdcard);
		tv_time_status = getView(R.id.tv_time_status);
		tv_resolution_status = getView(R.id.tv_resolution_status);
		/*------------------------------------> System Test <-------------------------------------------*/
	}

	@Override
	protected void setListener() {
		
		/*bt_settings.setOnClickListener(this);
		bt_wb.setOnClickListener(this);
		bt_iso.setOnClickListener(this);
		bt_record.setOnClickListener(this);
		iv_iso_ev_mode.setOnClickListener(this);
		iv_iso_manual_mode.setOnClickListener(this);
		iv_home.setOnClickListener(this);
		iv_album.setOnClickListener(this);
		bt_shoot_or_take.setOnClickListener(this);*/
		
		
		/*------------------------------------> System Test <-------------------------------------------*/
		bt_start_test.setOnClickListener(new MyTestOnClickListener());
		bt_end_test.setOnClickListener(new MyTestOnClickListener());
		bt_format_sdcard.setOnClickListener(new MyTestOnClickListener());
		/*------------------------------------> System Test <-------------------------------------------*/
		
	}


	
	

	
	private class MyTestOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_start_test:
				if (!CommonUtils.isFastDoubleClick()) {
					if (binder == null) {
						bindService(new Intent(getApplicationContext(),SystemAutoTestService.class),conn,BIND_AUTO_CREATE);
					} else {
						restartTest();
					}
				} else {
					showToast(R.string.can_not_click);
				}
				break;
			case R.id.bt_end_test:
				showStopRecordWarningDialog();
				break;
			case R.id.bt_format_sdcard:
				showFormatWarningDialog();
				break;
			default:
				break;
			}
			
		}
		
	} 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initRequest();
		initThreadPool();
		initSoundPool();
		initReceiver();
	}
	
	
	
	
	private void initReceiver() {
		mEndRecordBroadCastReceiver = new EndRecordBroadCastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_END_RECORD);
		intentFilter.addAction(ACTION_SDCARD_HAS_FULL);
		intentFilter.addAction(ACTION_RESOLUTION_HAS_COMPLETED);
		intentFilter.addAction(ACTION_RECORD_HAS_COMPLETED);
		registerReceiver(mEndRecordBroadCastReceiver, intentFilter);
	}



	private void initSoundPool() {
		mSoundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
		mWheelSound = mSoundPool.load(getApplicationContext(), R.raw.da, 0);
		mCameraClickSound = mSoundPool.load(getApplicationContext(), R.raw.camera_click,0);
		mVideoRecordSound = mSoundPool.load(getApplicationContext(), R.raw.video_record,0);
		mVideoPauseSound = mSoundPool.load(getApplicationContext(), R.raw.video_pause,0);
	}



	private void initThreadPool() {
		
		mSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		mSingleThreadScheduledExecutor.scheduleAtFixedRate(new AsynchronousUpdateThread(),10, INTERVAL, TimeUnit.SECONDS);
		
	}


	
	
	/**
	 * Name:AsynchronousUpdateThread
	 * Task: 1.update the sdcard avaispace 2.update the record status
	 */
	private class AsynchronousUpdateThread implements Runnable {

		@Override
		public void run() {
			// getFreeSDcardRequest();
			getCurrentStatusRequest();
		}
		
	}
	
	
	
	

	@Override
	protected void onResume() {
		super.onResume();
		// getSDcardSpace();
		
	}
	
	
	
	
	
	private void initRequest() {
		
		mInitCameraRequest = new InitCameraRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mInitCameraRequest, mHandler.obtainMessage(1));
		
	}
	
	
	
	public void setWhiteBlanceRequest(int mode) {
		
		mSetWhiteBlanceRequest = new SetWhiteBlanceRequest();
		mSetWhiteBlanceRequest.setMode(mode);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetWhiteBlanceRequest, mHandler.obtainMessage(5));
		
	}
	
	

	public void stopRecordRequest() {
		
		mSoundPool.play(mVideoPauseSound, 1, 1, 0, 0, 1);
		mStopRecordRequest = new StopRecordRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mStopRecordRequest, mHandler.obtainMessage(2));
		
	}
	
	public void stopRecordRequest1() {
		
		mSoundPool.play(mVideoPauseSound, 1, 1, 0, 0, 1);
		mStopRecordRequest = new StopRecordRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mStopRecordRequest, mHandler.obtainMessage(19));
		
	}
	
	public void stopRecordRequest2() {
		
		mSoundPool.play(mVideoPauseSound, 1, 1, 0, 0, 1);
		mStopRecordRequest = new StopRecordRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mStopRecordRequest, mHandler.obtainMessage(20));
		
	}
	
	public void startRecordRequest() {
		
		mSoundPool.play(mVideoRecordSound, 1, 1, 0, 0, 1);
		mStartRecordRequest = new StartRecordRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mStartRecordRequest, mHandler.obtainMessage(3));
		
	}
	
	
	private void getFreeSDcardRequest() {
		
		mGetSDcardFreeRequest = new GetSDcardFreeRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mGetSDcardFreeRequest, mHandler.obtainMessage(4));
		
	}
	
	
	public void setExposureValueRequest(String value) {
		
		mSetExposureValueRequest = new SetExposureValueRequest();
		mSetExposureValueRequest.setMode(value);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetExposureValueRequest, mHandler.obtainMessage(6));
		
	}
	
	
	public void setISORequest(String iso_v, String shut_v) {
		
		mSetISORequest = new SetISORequest();
		mSetISORequest.setIso(iso_v);
		mSetISORequest.setShutterTime(shut_v);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetISORequest, mHandler.obtainMessage(7));
		
	}
	
	
	public void setCameraModeRequest(int mode) {
		
		mSetCameraModeRequest = new SetCameraModeRequest();
		mSetCameraModeRequest.setMode(mode);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetCameraModeRequest, mHandler.obtainMessage(8));
		
	}
	
	
	private void setCurrentTimeRequest() {
		
		mSetCurrentTimeRequest = new SetCurrentTimeRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetCurrentTimeRequest, mHandler.obtainMessage(10));
		
	}
	
	private void getCurrentStatusRequest() {
		
		mGetCurrentStatusRequest = new GetCurrentStatusRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mGetCurrentStatusRequest, mHandler.obtainMessage(11));
		
	}
	
	
	public void setCurrentCameraModeRequest(int mode) {
		
		mSetCurrentCameraModeRequest = new SetCurrentCameraModeRequest(mode);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetCurrentCameraModeRequest, mHandler.obtainMessage(12));
		
	}
	
	
	/* **********************************  New Add ************************************ */
	public void setHueRequest(String hueValue) {
		
		mSetHueRequest = new SetHueRequest();
		mSetHueRequest.setMode(hueValue);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetHueRequest, mHandler.obtainMessage(13));
		
	}
	
	public void setPhotoFormatRequest(String formatVal) {
		
		mSetPhotoFormatRequest = new SetPhotoFormatRequest();
		mSetPhotoFormatRequest.setValue(formatVal);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetPhotoFormatRequest, mHandler.obtainMessage(14));
		
	}
	
	
	public void setAudioSwitchRequest(String mode) {
		
		mSetAudioSwitchRequest = new SetAudioSwitchRequest();
		mSetAudioSwitchRequest.setMode(mode);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetAudioSwitchRequest, mHandler.obtainMessage(15));
		
	}
	
	
	public void formatSDcardRequest() {
		
		mFormatSDcardRequest = new FormatSDcardRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mFormatSDcardRequest, mHandler.obtainMessage(16));
	
	}
	
	
	public void formatSDcardRequest1() {
		
		mFormatSDcardRequest = new FormatSDcardRequest();
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mFormatSDcardRequest, mHandler.obtainMessage(18));
		
	}
	
	
	public void setResolutionRequest(String mode) {
		
		mSetResolutionRequest = new SetResolutionRequest();
		mSetResolutionRequest.setVideoMode(mode);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mSetResolutionRequest, mHandler.obtainMessage(21));
		
	}
	
	
	/**
	 * check current record time length is to require.
	 * @return value
	 */
	public void checkRecordTime() {
		mHandler.obtainMessage(17).sendToTarget();
	}
	
	/**
	 * check current record time length is to require according the limit value
	 * @param string
	 */
	private void checkRecordTime(String time) {
		long seconds = Long.parseLong(time);
		if (seconds >= specifyRecordLength * 60) {
			sendStopBroadCast();
		}
	}
	
	
	/**
	 * check SDcard status by freeSpace
	 * @param freeSpace
	 */
	private void checkSDcardStatus(double freeSpace) {
		double smartValue = freeSpace / 1024;
		if (smartValue < 500 && cameraMode == 1 && curValue[37].equals("vf")) {
			sendSDcardFullBroadCast();
		}
		
	}
	
	
	/**
	 * send the stop record's broadcast to end the record.
	 */
	private void sendStopBroadCast() {
		sendBroadcast(new Intent(ACTION_END_RECORD));
	}

	/**
	 * send sdcard has full broadcast.
	 */
	private void sendSDcardFullBroadCast() {
		sendBroadcast(new Intent(ACTION_SDCARD_HAS_FULL));
	}
	
	
	public static final String ACTION_END_RECORD = "com.yuneec.android.action.end.record";
	public static final String ACTION_SDCARD_HAS_FULL = "com.yuneec.android.action.sdcard.full";
	public static final String ACTION_RESOLUTION_HAS_COMPLETED = "com.yuneec.android.action.resolution.completed";
	public static final String ACTION_RECORD_HAS_COMPLETED = "com.yuneec.android.action.record.completed";
	
	
	
	private EndRecordBroadCastReceiver mEndRecordBroadCastReceiver;
	
	private class EndRecordBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_END_RECORD)) {
				Message msg17 = updateTestStatusHandler.obtainMessage(17);
				msg17.obj = "第"+mAtomicInteger.intValue()+"次循环----> 当前系统录像时长已达"+specifyRecordLength+"分钟，允许执行下一条操作...";
				msg17.arg1 = 0;
				msg17.arg2 = 0;
				msg17.sendToTarget();
			} else if (intent.getAction().equals(ACTION_SDCARD_HAS_FULL)) {
				if (binder != null) {
					// 1. clear the queue 
					binder.clear();
					// 2. send the format request.
					stopRecordRequest1();
				} 
			} else if (intent.getAction().equals(ACTION_RESOLUTION_HAS_COMPLETED)) {
				Message msg22 = updateTestStatusHandler.obtainMessage(22);
				msg22.obj = "本轮测试已完毕";
				msg22.arg1 = 0;
				msg22.sendToTarget();
			} else if (intent.getAction().equals(ACTION_RECORD_HAS_COMPLETED)) {
				
			}
			
		}
		
	}
	
	
	/* **********************************  New Add ************************************ */
	

	/**
	 * Take Photo
	 */
	public void takePhotoRequest() {
		
		mSoundPool.play(mCameraClickSound, 1, 1, 0, 0, 1);
		/*new HighlightAnimation(iv_scene)
		.setColor(Color.WHITE)
		.setListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation animation) {
				updateVideoViewStatus();
			}
		}).animate();*/
		cameraMode = 2;
		mTakePhotoRequest = new TakePhotoRequest();
		showProgressDialog(R.string.waiting);
		CGO3_RtspRequestManager.sendRequest(getApplicationContext(), mTakePhotoRequest, mHandler.obtainMessage(9));
		
	}
	
	
	
	private AtomicInteger mAtomicInteger = new AtomicInteger(1);
	
	
	/**
	 * mainHandler
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int httpCode = msg.getData().getInt(RequestKey.HTTP_CODE); 
			switch (msg.what) {
			// init camera request
			case 1:
				if (httpCode == 200) {
					switch (mInitCameraRequest.getResultCode()) {
					case 0:
						curValue = mInitCameraRequest.getCurValue();
						if (curValue.length != 0) {
							setCurrentTimeRequest();
							updateVideoViewStatus();
							cameraMode = Integer.parseInt(curValue[45]);
							updateCameraStatus();
							double freeSpace = 0d;
							try {
								freeSpace = Double.parseDouble(curValue[38]);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
							updateSDcardStatus(freeSpace);
						}
						break;
					default:
						showToast(getResources().getString(R.string.is_error_init)+mInitCameraRequest.getErrorMsg());
						break;
					}
					
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) { 
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			// stop Record request
			case 2:
				if (httpCode == 200) {
					Message msg2 = updateTestStatusHandler.obtainMessage(2);
					switch (mStopRecordRequest.getResultCode()) {
					case 0:
						curValue[37] = "vf";
						updateRecordingStatus(curValue[37]);
						showToast(R.string.record_stop_success);
						msg2.obj = "第"+mAtomicInteger.intValue()+"次循环---->本次录像时长已达"+specifyRecordLength+"分钟，正在尝试结束...,结束成功";
						msg2.arg1 = 0;
						break;
					default:
						showToast(R.string.record_stop_failed);
						// updateRecordingStatus("vf");
						msg2.obj = "第"+mAtomicInteger.intValue()+"次循环---->本次录像时长已满，结束失败";
						msg2.arg1 = -1;
						break;
					}
					msg2.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			// start record request
			case 3:
				if (httpCode == 200) {
					Message msg1 = updateTestStatusHandler.obtainMessage(1);
					switch (mStartRecordRequest.getResultCode()) {
					case 0:
						curValue[37] = "record";
						curValue[40] = "0";
						updateRecordingStatus(curValue[37]);
						showToast(R.string.record_start_success);
						msg1.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在开始录像...";
						msg1.arg1 = 0;
						break;
					default:
						showToast(R.string.record_start_failed);
						
						msg1.obj = "第"+mAtomicInteger.intValue()+"次循环---->启动录像失败";
						msg1.arg1 = -1;
						break;
					}
					msg1.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			case 4:
				if (httpCode == 200) {
					switch (mGetSDcardFreeRequest.getResultCode()) {
					case 0:
						double freeSpace = (mGetSDcardFreeRequest.getFreeSpace()) / 1024 / 1024;
						if (freeSpace < 1) {
							tv_space.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cgo3_sdcard_warning, 0, 0, 0);
							tv_space.setTextColor(Color.RED);
						} else {
							tv_space.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cgo3_sdcard, 0, 0, 0);
							tv_space.setTextColor(Color.WHITE);
						}
						tv_space.setText(MathUtil.getDecimalFormat2(freeSpace)+"GB");
						break;
					default:
						break;
					}
					
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			// set white blance
			case 5:
				if (httpCode == 200) {
					Message msg2 = updateTestStatusHandler.obtainMessage(2);
					switch (mSetWhiteBlanceRequest.getResultCode()) {
					case 0:
						msg2.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在设置白平衡,当前模式为："+mSetWhiteBlanceRequest.getModeStr()+"";
						msg2.arg1 = 0; 
						break;
					default:
						msg2.obj = "第"+mAtomicInteger.intValue()+"次循环---->设置白平衡失败";
						msg2.arg1 = -1; 
						break;
					}
					msg2.sendToTarget();
					
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			// SetExposureValueRequest
			case 6:
				if (httpCode == 200) {
					Message msg4 = updateTestStatusHandler.obtainMessage(4);
					switch (mSetExposureValueRequest.getResultCode()) {
					case 0:
						msg4.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在设置曝光补偿,当前值为："+mSetExposureValueRequest.getMode()+"";
						msg4.arg1= 0;
						break;
					default:
						showToast(getString(R.string.set_failed)+mSetExposureValueRequest.getErrorMsg());
						msg4.obj = "第"+mAtomicInteger.intValue()+"次循环---->设置曝光补偿失败";
						msg4.arg1= -1;
						break;
					}
					msg4.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			// setIsoRequest
			case 7:
				if (httpCode == 200) {
					Message msg5 = updateTestStatusHandler.obtainMessage(5);
					switch (mSetISORequest.getResultCode()) {
					case 0:
						msg5.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在设置ISO及快门时间，值分别为："+mSetISORequest.getIso()+","+mSetISORequest.getShutterTime()+"";
						msg5.arg1= 0;
						break;
					default:
						showToast(getString(R.string.set_failed)+mSetISORequest.getErrorMsg());
						msg5.obj = "第"+mAtomicInteger.intValue()+"次循环---->设置ISO及快门时间失败";
						msg5.arg1= -1;
						break;
					}
					msg5.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			// setCameraModeRequest
			case 8:
				if (httpCode == 200) {
					Message msg3 = updateTestStatusHandler.obtainMessage(3);
					switch (mSetCameraModeRequest.getResultCode()) {
					case 0:
						msg3.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在修改当前操作模式，当前操作模式为："+mSetCameraModeRequest.getModeStr()+"";
						msg3.arg1 = 0;
						break;
					default:
						showToast(getString(R.string.set_failed)+mSetCameraModeRequest.getErrorMsg());
						msg3.obj = "第"+mAtomicInteger.intValue()+"次循环---->修改当前操作模式失败";
						msg3.arg1 = -1;
						break;
					}
					msg3.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			// Take photo
			case 9:
				if (httpCode == 200) {
					Message msg9 = updateTestStatusHandler.obtainMessage(9);
					switch (mTakePhotoRequest.getResultCode()) {
					case 0:
						closeProgressDialog();
						showToast(R.string.taking_success);
						msg9.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在根据所选照片格式进行拍照...拍照完成";
						msg9.arg1 = 0;
						mAtomicInteger.incrementAndGet();
						break;
					default:
						closeProgressDialog();
						showToast(getString(R.string.taking_failure)+mTakePhotoRequest.getErrorMsg());
						msg9.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在根据所选照片格式进行拍照...拍照失败";
						msg9.arg1 = -1;
						break;
					}
					msg9.sendToTarget();
				} else if (httpCode == 500) {
					closeProgressDialog();
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					closeProgressDialog();
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					closeProgressDialog();
					showToast(R.string.is_error_network);
				}
				break;
			// Get current status
			case 11:
				if (httpCode == 200) {
					switch (mGetCurrentStatusRequest.getResultCode()) {
					case 0:
						updateSDcardStatus(mGetCurrentStatusRequest.getFreeSpace());
						if (curValue.length > 0) {
							cameraMode = mGetCurrentStatusRequest.getCameraMode();
							curValue[37] = mGetCurrentStatusRequest.getStatus();
							curValue[40] = mGetCurrentStatusRequest.getRecordTime();
							curValue[46] = mGetCurrentStatusRequest.getAeMode();
							curValue[49] = mGetCurrentStatusRequest.getEv_value();
							curValue[48] = mGetCurrentStatusRequest.getIso_value();
							curValue[47] = mGetCurrentStatusRequest.getShutter_value();
							curValue[13] = mGetCurrentStatusRequest.getWbMode();
							updateCameraStatus();
							checkRecordTime(curValue[40]);
							checkSDcardStatus(mGetCurrentStatusRequest.getFreeSpace());
						}
						break;
					default:
						/*curValue[37] = "vf";*/
						updateRecordingStatus("vf");
						break;
					}
				} else if (httpCode == 500) {
					closeProgressDialog();
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					closeProgressDialog();
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					closeProgressDialog();
					showToast(R.string.is_error_network);
				} 
				break;
			// setCurrentCameraMode
			case 12:
				if (httpCode == 200) {
					final Message msg12 = updateTestStatusHandler.obtainMessage(12);
					switch (mSetCurrentCameraModeRequest.getResultCode()) {
					case 0:
						cameraMode = mSetCurrentCameraModeRequest.getMode();
						updateCameraModeStatus();
						showToast(R.string.set_success);
						String temp;
						if (cameraMode == 1) {
							temp = "录像";
						} else {
							temp = "拍照";
						}
						msg12.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在将当前相机模式切换成"+temp+"模式,切换成功";
						msg12.arg1 = 0;
						break;
					default:
						showToast(R.string.set_failed);
						msg12.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在切换相机当前模式,切换失败";
						msg12.arg1 = -1;
						break;
					}
					/*new Handler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							msg12.sendToTarget();
						}
					}, 5000);*/
					msg12.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
				break;
			// setHueRequest
			case 13:
				if (httpCode == 200) {
					Message msg13 = updateTestStatusHandler.obtainMessage(13);
					switch (mSetHueRequest.getResultCode()) {
					case 0:
						showToast(R.string.hue_set_success);
						msg13.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在设置相机场景...，当前场景为："+mSetHueRequest.getModeStr()+"";
						msg13.arg1 = 0;
						break;
					default:
						showToast(getString(R.string.hue_set_failed)+mSetHueRequest.getErrorMsg());
						msg13.obj = "第"+mAtomicInteger.intValue()+"次循环---->设置相机场景失败";
						msg13.arg1 = -1;
						break;
					}
					msg13.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
				break;
			// mSetPhotoFormatRequest
			case 14:
				if (httpCode == 200) {
					Message msg14 = updateTestStatusHandler.obtainMessage(14);
					switch (mSetPhotoFormatRequest.getResultCode()) {
					case 0:
						showToast(R.string.set_success);
						msg14.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在设置照片格式...，当前照片模式为："+mSetPhotoFormatRequest.getValue()+"";
						msg14.arg1 = 0;
						break;
					default:
						showToast(getString(R.string.set_failed)+mSetPhotoFormatRequest.getErrorMsg());
						msg14.obj = "第"+mAtomicInteger.intValue()+"次循环---->设置照片模式失败";
						msg14.arg1 = -1;
						break;
					}
					msg14.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
				break;
			// mSetAudioSwitchRequest
			case 15:
				if (httpCode == 200) {
					Message msg15 = updateTestStatusHandler.obtainMessage(15);
					switch (mSetAudioSwitchRequest.getResultCode()) {
					case 0:
						showToast(R.string.set_success);
						msg15.obj = "第"+mAtomicInteger.intValue()+"次循环---->正在设置当前音频开关...，当前音频开关为："+mSetAudioSwitchRequest.getModeStr()+"";
						msg15.arg1 = 0;
						break;
					default:
						showToast(R.string.set_failed);
						msg15.obj = "第"+mAtomicInteger.intValue()+"次循环---->设置音频开关失败";
						msg15.arg1 = -1;
						break;
					}
					msg15.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
				break;
			// mFormatSDcardRequest
			case 16:
				if (httpCode == 200) {
					Message msg16 = updateTestStatusHandler.obtainMessage(16);
					switch (mFormatSDcardRequest.getResultCode()) {
					case 0:
						showToast(R.string.format_success);
						msg16.obj = "第"+mAtomicInteger.intValue()+"次循环----> 系统检测到T卡将满,正在尝试重新格式化T卡...";
						msg16.arg1 = 0;
						break;
					default:
						showToast(R.string.format_failure);
						msg16.obj = "第"+mAtomicInteger.intValue()+"次循环----> T卡格式化失败";
						msg16.arg1 = -1;
						break;
					}
					msg16.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
				break;
			// check record time
			case 17:
				Message msg17 = updateTestStatusHandler.obtainMessage(17);
				long seconds = Long.parseLong(curValue[40]);
				if (seconds < specifyRecordLength * 60) {
					msg17.obj = "第"+mAtomicInteger.intValue()+"次循环----> 系统录像时长未满"+specifyRecordLength+"分钟，正在等待系统录像完成...";
					msg17.arg1 = 0;
					msg17.arg2 = -1;
				} else {
					msg17.obj = "第"+mAtomicInteger.intValue()+"次循环----> 验证录像时长出现错误";
					msg17.arg1 = -1;
				}
				msg17.sendToTarget();
				break;
			case 18:
				if (httpCode == 200) {
					switch (mFormatSDcardRequest.getResultCode()) {
					case 0:
						showToast(R.string.format_success);
						break;
					default:
						showToast(R.string.format_failure);
						break;
					}
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
				break;
			// stop record request
			case 19:
				if (httpCode == 200) {
					 formatSDcardRequest();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
			case 20:
				if (httpCode == 200) {
					switch (mStopRecordRequest.getResultCode()) {
					case 0:
						curValue[37] = "vf";
						updateRecordingStatus(curValue[37]);
						showToast(R.string.record_stop_success);
						break;
					default:
						showToast(R.string.record_stop_failed);
						// updateRecordingStatus("vf");
						break;
					}
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				} 
				break;
			case 21:
				if (httpCode == 200) {
					Message msg21 = updateTestStatusHandler.obtainMessage(21);
					switch (mSetResolutionRequest.getResultCode()) {
					case 0:
						showToast(R.string.resolution_set_success);
						tv_resolution_status.setText(mSetResolutionRequest.getVideoMode());
						tv_time_status.setText(""+specifyRecordLength+"分钟");
						msg21.obj = "第"+mAtomicInteger.intValue()+"次循环----> 正在设置系统分辨率,当前值为："+mSetResolutionRequest.getVideoMode()+"";
						msg21.arg1 = 0;
						break;
					default:
						showToast(getString(R.string.resolution_set_failed)+mSetResolutionRequest.getErrorMsg());
						msg21.obj = "第"+mAtomicInteger.intValue()+"次循环----> 正在设置系统分辨率失败";
						msg21.arg1 = -1;
						break;
					}
					msg21.sendToTarget();
				} else if (httpCode == 500) {
					showToast(R.string.is_error_500);
				} else if (httpCode == 80001) {
					showToast(R.string.is_error_json);
				} else if (httpCode == 80002) {
					showToast(R.string.is_error_network);
				}
				break;
			default:
				break;
			}
			
		}

		
	};
	
	

	
	
	/**
	 * Specify the Record Time length. TimeUnit: Seconds
	 */
	private int specifyRecordLength = 5;
	
	
	//------------------------------------------------------------------------------
    //	    					 Update Current Status                      
    //------------------------------------------------------------------------------
	
	/**
	 * this method is to update the last camera status. Following:
	 * 1. the recording status(is recording).
	 * 2. camera mode(photo mode or camera mode).
	 * 3. last properties(AE or Manuaml , ISO value, Shutter Time value, Ev value, WB options ...).
	 */
	private final void updateCameraStatus() {
		
		// step1. update camera status
		updateCameraModeStatus();
		if (cameraMode == 1) updateRecordingStatus(curValue[37]);
		// step2. update last settings info
		wbMode  = Integer.parseInt(curValue[13]); // white balance
		aeMode = Integer.parseInt(curValue[46]);
		updateEVStatus(aeMode);
		updateEVitemStatus(curValue[49]);
		updateISOitemStatus(curValue[48]);
		updateShutterTimeitemStatus(curValue[47]);
	}
	
	


	private void updateRecordingStatus(String status) {
		
		bt_shoot_or_take.setBackgroundResource(R.drawable.cgo3_shoot);
		cameraMode = 1; // modify current operate mode is 1;
		
		if (status.equals("record")) {
			bt_record.setBackgroundResource(R.drawable.cgo3_record_stop);
			bt_shoot_or_take.setClickable(false);
			bt_settings.setClickable(false); 
			long seconds = Long.parseLong(curValue[40]);
			ll_record_duration.setVisibility(View.VISIBLE);
			tv_record_duration.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.breath_anim));
			ch_record_time.start(seconds*1000);
		} else {
			if (ll_record_duration.getVisibility() == View.VISIBLE) {
				bt_record.setBackgroundResource(R.drawable.com_record_selector);
				bt_shoot_or_take.setClickable(true);
				bt_settings.setClickable(true); 
				tv_record_duration.clearAnimation();
				ch_record_time.stop();
				ll_record_duration.setVisibility(View.GONE);
			}
		}
		
	}
	
	
	
	/**
	 * update camera mode.
	 */
	private void updateCameraModeStatus() {
		switch (cameraMode) {
		case 2:
			bt_shoot_or_take.setBackgroundResource(R.drawable.cgo3_take);
			break;
		case 1:
			bt_shoot_or_take.setBackgroundResource(R.drawable.cgo3_shoot);
			break;
		}
	}
	
	
	
	/**
	 * update sdcard status.
	 * @param freeValue
	 */
	private void updateSDcardStatus(double freeValue) {

		double freeSpace = freeValue / 1024 / 1024;
		if (freeSpace == 0) {
			tv_space.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cgo3_sdcard_warning, 0, 0, 0);
			tv_space.setTextColor(Color.RED);
			showSpaceLackDialog(0);
		} else if (freeSpace > 0 && freeSpace <1) {
			tv_space.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cgo3_sdcard_warning, 0, 0, 0);
			tv_space.setTextColor(Color.RED);
			if (freeSpace > 0.5 && freeSpace<0.6) showSpaceLackDialog(1);
			if (freeSpace > 0.8 && freeSpace<0.9) showSpaceLackDialog(1);
			
		} else {
			tv_space.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cgo3_sdcard, 0, 0, 0);
			tv_space.setTextColor(Color.WHITE);
		}
		tv_space.setText(MathUtil.getDecimalFormat2(freeSpace)+"GB");
		
	}
	
	
	

	/**
	 * update the WB button status
	 * @param isSelected
	 */
	private void updateWBStatus(boolean isSelected) {
		
		if (isSelected) {
			iv_wb.setImageResource(R.drawable.cgo3_wb_pressed);
			iv_sun.setImageResource(R.drawable.cgo3_sensitometry_pressed);
		} else {
			iv_wb.setImageResource(R.drawable.cgo3_wb);
			iv_sun.setImageResource(R.drawable.cgo3_sensitometry);
		}
		
	}
	
	
	/**
	 * update the ISO/EV button font.
	 * @param ae_enable
	 */
	private void updateEVStatus(int ae_enable) {
		
		switch (ae_enable) {
		// 0 Manual
		case 0:
			tv_shutter_time.setText(getString(R.string.manual));
			break;
		// 1 Auto
		case 1:
			tv_shutter_time.setText(getString(R.string.auto));
			break;
		default:
			break;
		}
		
	}
	
	
	/**
	 * update the ISO/EV button status
	 * @param isSelected
	 */
	private void updateEVStatus(boolean isSelected) {
		
		if (isSelected) {
			tv_shutter_time.setTextColor(Color.RED);
			iv_iso.setImageResource(R.drawable.cgo3_contrast_pressed);
		} else {
			tv_shutter_time.setTextColor(Color.WHITE);
			iv_iso.setImageResource(R.drawable.cgo3_contrast);
		}
		
	}
	
	
	/**
	 * update EV item status.
	 * @param value
	 */
	private void updateEVitemStatus(String value) {
		
		int position = evAdapter.getPostionByValue(value);
		wv_ev_value.setCurrentItem(position);
		
	}
	
	
	
	/**
	 * update EV item status.
	 * @param value
	 */
	private void updateISOitemStatus(String value) {
		
		int position = isoAdapter.getPostionByValue(value);
		wv_iso_value.setCurrentItem(position);
		
	}
	
	
	
	/**
	 * update ShutterTime item status.
	 * @param value
	 */
	private void updateShutterTimeitemStatus(String value) {
		
		int position = shutterTimeAdapter.getPostionByValue(value);
		wv_shutter_time_value.setCurrentItem(position);
		
	}
	
	
	
	/*private void updateRecordingStatus1(String status) {
		// record status
		if (status.equals("vf")) {
			if (ll_record_duration.getVisibility() == View.VISIBLE) {
				bt_record.setBackgroundResource(R.drawable.com_record_selector);
				bt_shoot_or_take.setClickable(true);
				bt_settings.setClickable(true); 
				tv_record_duration.clearAnimation();
				ch_record_time.stop();
				ll_record_duration.setVisibility(View.GONE);
				if (curValue.length > 0) curValue[37] = "vf";
			}
		}
	}*/

	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		// Record Take photo Or Record
		case R.id.bt_record:
			switch (cameraMode) {
			// Take photo
			case 2:
				takePhotoRequest();
				break;
			// Video
			case 1:
				if (curValue.length > 0) {
					if (curValue[37].equals("record")) {
						stopRecordRequest();
					} else {
						startRecordRequest();
					}
				} else {
					showToast(R.string.record_start_failed);
				}
				break;
			default:
				break;
			}
			break;
		// Return Home Activity
		case R.id.iv_home:
			finish();
			break;
		// Album
		case R.id.iv_album:
			startActivity(new Intent(getApplicationContext(),ImagePreviewActivity.class));
			break;
		// Take or Shoot
		case R.id.bt_shoot_or_take:
			if (cameraMode == 2) {
				setCurrentCameraModeRequest(1);
			} else if (cameraMode == 1) {
				setCurrentCameraModeRequest(2);
			}
			break;
		// settings
		case R.id.bt_settings:
			startActivityForResult(new Intent(getApplicationContext(),SettingsActivity.class),1003);
			break;
		// WB
		case R.id.bt_wb:
			menu_sub_iso_ev.setVisibility(View.GONE);
			menu_sub_iso_manual.setVisibility(View.GONE);
			updateEVStatus(aeMode);
			updateEVStatus(false);
			// get history's white blance mode value.
		 	if (menu_sub_wb.getVisibility() == View.VISIBLE) {
		 		menu_sub_wb.setVisibility(View.GONE);
		 		updateWBStatus(false);
			} else {
				menu_sub_wb.setVisibility(View.VISIBLE);
				updateWBStatus(true);
				initWhiteBlanceStatus(wbMode);
			}
			break;
		// ISO
		case R.id.bt_iso:
			menu_sub_wb.setVisibility(View.GONE);
			updateWBStatus(false);
			// get history's ISO EV mode value.
			if (menu_sub_iso_ev.getVisibility() == View.VISIBLE || menu_sub_iso_manual.getVisibility() ==  View.VISIBLE) {
				menu_sub_iso_ev.setVisibility(View.GONE);
				menu_sub_iso_manual.setVisibility(View.GONE);
				updateEVStatus(false);
			} else {
				switch (aeMode) {
				// manual
				case 0:
					menu_sub_iso_manual.setVisibility(View.VISIBLE);
					
					break;
				// auto
				case 1:
					menu_sub_iso_ev.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
				updateEVStatus(true);
			}
			break;
		// ISO switch Auto or Manual Auto
		case R.id.iv_iso_ev_mode:
			aeMode = 0;
			tv_shutter_time.setText(getString(R.string.manual));
			setCameraModeRequest(aeMode);
			if (menu_sub_iso_ev.getVisibility() == View.VISIBLE) {
				menu_sub_iso_ev.setVisibility(View.GONE);
				menu_sub_iso_manual.setVisibility(View.VISIBLE);
			}
			break;
		// ISO switch Auto or Manual Manual
		case R.id.iv_iso_manual_mode:
			aeMode = 1;
			tv_shutter_time.setText(getString(R.string.auto));
			setCameraModeRequest(aeMode);
			if (menu_sub_iso_manual.getVisibility() == View.VISIBLE) {
				menu_sub_iso_manual.setVisibility(View.GONE);
				menu_sub_iso_ev.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}
	

	
	

	private void showSpaceLackDialog(int i) {
		if (flag == 0) {
			final AlertDialog alertDialog = new AlertDialog(SystemTestCameraActivity.this,0);
			alertDialog.setMessage((i == 0) ? (R.string.sdcard_error_tips) : (R.string.space_lack_tips));
			alertDialog.setCancelable(true);
			alertDialog.setPositiveButton((i == 0) ? getString(R.string.yes) : getString(R.string.i_know), new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					alertDialog.dismiss();
					flag = 0;
				}
			});
			flag = 1;
		}
	}
	
	private int flag = 0;
	
	
	
	private void showFormatWarningDialog() {
		final AlertDialog alertDialog = new AlertDialog(SystemTestCameraActivity.this);
		alertDialog.setMessage(R.string.format_sdcard_tips);
		alertDialog.setCancelable(true);
		alertDialog.setPositiveButton(getString(R.string.confirm_format), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
				formatSDcardRequest1();
			}
		});
		alertDialog.setNegativeButton(getString(R.string.cancel_format), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}
	
	
	private void showStopRecordWarningDialog() {
		final AlertDialog alertDialog = new AlertDialog(SystemTestCameraActivity.this);
		alertDialog.setMessage(R.string.stop_record_tips);
		alertDialog.setCancelable(true);
		alertDialog.setPositiveButton(getString(R.string.i_know), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
				stopRecordRequest2();
			}
		});
		alertDialog.setNegativeButton(getString(R.string.cancel_format), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}

	
	
	/**
	 * init old white blance item status
	 * @param oldWhiteBlanceValue
	 */
	private void initWhiteBlanceStatus(int oldWhiteBlanceValue) {
		switch (oldWhiteBlanceValue) {
		// auto
		case 0:
			bt_wb_awb.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_awb);
			break;
		// INCANDESCENT
		case 1:
			bt_wb_incandescent.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_incandescent);
			break;
		// Sunset
		case 3:
			bt_wb_sunset.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_sunset);
			break;
		// sunny
		case 4:
			bt_wb_daylight.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_daylight);
			break;
		// cloudy
		case 5:
			bt_wb_cloudy.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_cloudy);
			break;
		// FLUORESCENT
		case 7:
			bt_wb_flucrescent.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_flucrescent);
			break;
		// custom
		case 10:
			bt_wb_custom.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_custom);
			break;
		// lock
		case 99:
			bt_wb_lock.setBackgroundResource(R.drawable.shape_red_rectangle);
			oldSelectedItemId = getResourceId(bt_wb_lock);
			break;
		default:
			break;
		}
		
	}


	private int getResourceId(View view) {
		return view.getId();
	}
	
	

	 //-----------------------------------------------------------------------
    //	    						 SOUND
    //------------------------------------------------------------------------
	private int oldSelectedItemId = 0;
	private int mWheelSound;
	private int mCameraClickSound;
	private int mVideoRecordSound;
	private int mVideoPauseSound;
	
	
	
	private class WBOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.bt_wb_awb:
				wbMode = 0;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_awb);
				break;
			case R.id.bt_wb_lock:
				wbMode = 99;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_lock);
				break;
			case R.id.bt_wb_daylight:
				wbMode = 4;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_daylight);
				break;
			case R.id.bt_wb_cloudy:
				wbMode = 5;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_cloudy);
				break;
			case R.id.bt_wb_flucrescent:
				wbMode = 7;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_flucrescent);
				break;
			case R.id.bt_wb_incandescent:
				wbMode = 1;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_incandescent);
				break;
			case R.id.bt_wb_sunset:
				wbMode = 3;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_sunset);
				break;
			case R.id.bt_wb_custom:
				wbMode = 10;
				setWhiteBlanceRequest(wbMode);
				modifyItemStatus(R.id.bt_wb_custom);
				break;
			default:
				break;
			}
		}

	}
	
	
	
	/**
	 * modify selected item's status
	 */
	private void modifyItemStatus(int resId) {
		
		Button oldItem =  (Button)getView(oldSelectedItemId);
		oldItem.setBackgroundResource(R.drawable.com_pop_list_item_selector);
		Button newItem = (Button)getView(resId);
		newItem.setBackgroundResource(R.drawable.shape_red_rectangle);
		oldSelectedItemId = resId;
		
	}
	
	
	
	
	private void updateVideoViewStatus() {
		
		vv_scene.setVideoPath(mVideoPath);
		vv_scene.requestFocus();
		vv_scene.start();
	
	}


	
	
	 /**
     * Adds changing listener for spinnerwheel that updates the spinnerwheel label
     * @param wheel the spinnerwheel
     * @param label the spinnerwheel label
     */
    private void addChangingListener(final AbstractWheel wheel, final String label) {
        wheel.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                //spinnerwheel.setLabel(newValue != 1 ? label + "s" : label);
            }
        });
    }
	
	

	@Override
	protected void addActivity() {
		mApplication.addActivity(this);
	}
	
	
	@Override
	protected void initProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
	}

	
	
	
	private Handler updateTestStatusHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String message = (String)msg.obj;
			if (msg.arg1 == 0) {
				tv_test_status.setTextColor(Color.GREEN);
				tv_test_status.setText(message);
				if (msg.what == 9) {
					restartTest();
					return;
				}
				if (msg.what == 16) {
					restartTest();
					return;
				}
				if (msg.what == 17) {
					if (msg.arg2 == -1) {
						return;
					}
				}
				if (msg.what == 22) {
					return;
				}
				binder.execute();
			} else if (msg.arg1 == -1) {
				tv_test_status.setTextColor(Color.RED);
				tv_test_status.setText(message);
			}
		}
		
	};
	 
	
	
	
	private SystemAutoTestService.MyBinder binder = null;
	
	
	
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			
			binder = (MyBinder) iBinder;
			binder.setInstance(SystemTestCameraActivity.this);
			startTest();
		}
	};
	
	
	
	/**
	 * core Method, start Test Process.
	 */
	private void startTest() {
		binder.reset();
		binder.execute();
	}
	
	
	private void restartTest() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startTest();
			}
		}, 7000);
	}

	
	private void unRegisterBroadCastReceiver() {
		unregisterReceiver(mEndRecordBroadCastReceiver);
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mSingleThreadScheduledExecutor != null) 
			mSingleThreadScheduledExecutor.shutdown();
		vv_scene.stopPlayback();
		if (binder != null) {
			unbindService(conn);
		}
		unRegisterBroadCastReceiver();
	}

	

}
