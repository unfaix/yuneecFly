package com.yuneec.android.flyingexpert.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.adapter.HueAdaper;
import com.yuneec.android.flyingexpert.adapter.PhotoFormatAdaper;
import com.yuneec.android.flyingexpert.adapter.ResolutionAdapter;
import com.yuneec.android.flyingexpert.adapter.ShareAppAdapter;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.library.AlertDialog;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.library.ShSwitchView;
import com.yuneec.android.flyingexpert.library.ShSwitchView.OnSwitchStateChangeListener;
import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.rtsp.RtspRequestManager;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.FormatSDcardRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.GetAudioSwitchRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.GetHueRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.GetPhotoFormatRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.GetResolutionRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.ResetSettingsRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.SetAudioSwitchRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.SetHueRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.SetPhotoFormatRequest;
import com.yuneec.android.flyingexpert.logic.rtsp.impl.SetResolutionRequest;


/**
 * Settings UI
 * @author yongdaimi
 * @remark 
 * @date 2015-3-17下午3:23:14
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class SettingsActivity extends BaseActivity{

	
	
	private TextView bt_finish;
	private TextView bt_resolution;
	private TextView bt_hue;
	private TextView bt_photo_format;
	private TextView bt_audio_settings;
	private TextView bt_reset;
	private TextView bt_format;
	private TextView bt_share;
	private TextView bt_about;
	
	private LinearLayout settings_container;
	private TextView tv_settings_title;
	
	private View view_resolution;
	private ExpandableListView el_resolution;
	private ListView lv_hue;
	private ListView lv_photo_format;
	
	private View view_hue;
	private View view_photo_format;
	
	private View view_audio;
	private ShSwitchView sv_audio;
	
	private DrawerLayout dl_container;
	private GridView gv_item;
	
	private ResolutionAdapter mResolutionAdapter;
	private HueAdaper mHueAdaper;
	private PhotoFormatAdaper mPhotoFormatAdaper;
	
	private ShareAppAdapter mShareAppAdapter;
	
	private static final List<String> resolution_group = new ArrayList<String>();
	private static final List<List<String>> resolution_child = new ArrayList<List<String>>();
	
	private static final List<String> hue_list = new ArrayList<String>();
	private static final List<String> photo_format_list = new ArrayList<String>();
	
	
	private SetResolutionRequest mSetResolutionRequest;
	private SetHueRequest mSetHueRequest;
	private SetPhotoFormatRequest mSetPhotoFormatRequest;
	private GetResolutionRequest mGetResolutionRequest;
	private GetHueRequest mGetHueRequest;
	private GetPhotoFormatRequest mGetPhotoFormatRequest;
	private GetAudioSwitchRequest mGetAudioSwitchRequest;
	private SetAudioSwitchRequest mSetAudioSwitchRequest;
	private FormatSDcardRequest mFormatSDcardRequest;
	private ResetSettingsRequest mResetSettingsRequest;
	
	
	static {
		
		resolution_group.add("4096x2160");
		resolution_group.add("3840x2160");
		resolution_group.add("2560x1440");
		resolution_group.add("1920x1080");
		
		final ArrayList<String> option1 = new ArrayList<String>();
		option1.add("24p");
		option1.add("25p");
		
		final ArrayList<String> option2 = new ArrayList<String>();
		option2.add("24p");
		option2.add("25p");
		option2.add("30p");
		
		final ArrayList<String> option3 = new ArrayList<String>();
		option3.add("24p");
		option3.add("25p");
		option3.add("30p");
		
		final ArrayList<String> option4 = new ArrayList<String>();
		
		option4.add("24p");
		option4.add("25p");
		option4.add("30p");
		option4.add("48p");
		option4.add("50p");
		option4.add("60p");
		option4.add("120p");
		
		resolution_child.add(option1);
		resolution_child.add(option2);
		resolution_child.add(option3);
		resolution_child.add(option4);
		
		hue_list.add("Nature");
		hue_list.add("Gorgeous");
		hue_list.add("Raw");
		hue_list.add("Night");
		
		photo_format_list.add("jpg");
		photo_format_list.add("dng");
		
	}
	
	
	
	
	
	@Override
	protected void setContainer() {
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_settings1);
	}
	
	
	
	public void setResolutionRequest(String mode) {
		
		mSetResolutionRequest = new SetResolutionRequest();
		mSetResolutionRequest.setVideoMode(mode);
		RtspRequestManager.sendRequest(getApplicationContext(), mSetResolutionRequest, mHandler.obtainMessage(1));
		
	}
	
	
	
	public void setHueRequest(String hueValue) {
		
		mSetHueRequest = new SetHueRequest();
		mSetHueRequest.setMode(hueValue);
		RtspRequestManager.sendRequest(getApplicationContext(), mSetHueRequest, mHandler.obtainMessage(2));
		
	}
	
	
	public void setPhotoFormatRequest(String formatVal) {
		
		mSetPhotoFormatRequest = new SetPhotoFormatRequest();
		mSetPhotoFormatRequest.setValue(formatVal);
		RtspRequestManager.sendRequest(getApplicationContext(), mSetPhotoFormatRequest, mHandler.obtainMessage(3));
		
	}
	
	
	public void setAudioSwitchRequest(String mode) {
		
		mSetAudioSwitchRequest = new SetAudioSwitchRequest();
		mSetAudioSwitchRequest.setMode(mode);
		RtspRequestManager.sendRequest(getApplicationContext(), mSetAudioSwitchRequest, mHandler.obtainMessage(4));
		
	}
	
	
	/*get resolution*/
	public void getResolutionRequest() {
		
		mGetResolutionRequest = new GetResolutionRequest();
		RtspRequestManager.sendRequest(getApplicationContext(), mGetResolutionRequest, mHandler.obtainMessage(5));
		
	}
	
	
	
	/*get hue*/
	public void getHueRequest() {
		
		mGetHueRequest = new GetHueRequest();
		RtspRequestManager.sendRequest(getApplicationContext(), mGetHueRequest, mHandler.obtainMessage(6));
		
	}
	
	
	/*get photo format*/
	public void getPhotoFormatRequest() {
		
		mGetPhotoFormatRequest = new GetPhotoFormatRequest();
		RtspRequestManager.sendRequest(getApplicationContext(), mGetPhotoFormatRequest, mHandler.obtainMessage(7));
		
	}
	
	
	/*get Audio settings*/
	public void getAudioSwitchRequest() {
		
		mGetAudioSwitchRequest = new GetAudioSwitchRequest();
		RtspRequestManager.sendRequest(getApplicationContext(), mGetAudioSwitchRequest, mHandler.obtainMessage(8));
		
	}
	
	/* format sdcard */
	public void formatSDcardRequest() {
		
		mFormatSDcardRequest = new FormatSDcardRequest();
		RtspRequestManager.sendRequest(getApplicationContext(), mFormatSDcardRequest, mHandler.obtainMessage(9));
		
	}
	
	/* reset settings */
	public void resetSettingsRequest() {
		
		mResetSettingsRequest = new ResetSettingsRequest();
		RtspRequestManager.sendRequest(getApplicationContext(), mResetSettingsRequest, mHandler.obtainMessage(10));
		
	}
	
	
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int httpCode = msg.getData().getInt(RequestKey.HTTP_CODE); 
			switch (msg.what) {
			// resolution settings
			case 1:
				if (httpCode == 200) {
					switch (mSetResolutionRequest.getResultCode()) {
					case 0:
						showToast(R.string.resolution_set_success);
						String resolution = mSetResolutionRequest.getVideoMode();
						setCurrentResolution(resolution);
						break;
					default:
						showToast(getString(R.string.resolution_set_failed)+mSetResolutionRequest.getErrorMsg());
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
			// hue settings
			case 2:
				if (httpCode == 200) {
					switch (mSetHueRequest.getResultCode()) {
					case 0:
						showToast(R.string.hue_set_success);
						String mode = mSetHueRequest.getMode();
						setCurrentHue(mode);
						break;
					default:
						showToast(getString(R.string.hue_set_failed)+mSetHueRequest.getErrorMsg());
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
			// photo format settings
			case 3:
				if (httpCode == 200) {
					switch (mSetPhotoFormatRequest.getResultCode()) {
					case 0:
						showToast(R.string.set_success);
						String value = mSetPhotoFormatRequest.getValue();
						setCurrentPhotoFormat(value);
						break;
					default:
						showToast(getString(R.string.set_failed)+mSetPhotoFormatRequest.getErrorMsg());
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
			// setAudioSwitch
			case 4:
				if (httpCode == 200) {
					switch (mSetAudioSwitchRequest.getResultCode()) {
					case 0:
						showToast(R.string.set_success);
						setCurrentAudioSettings(mSetAudioSwitchRequest.getMode());
						break;
					default:
						showToast(R.string.set_failed);
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
			// get resolution
			case 5:
				if (httpCode == 200) {
					switch (mGetResolutionRequest.getResultCode()) {
					case 0:
						String resolution = mGetResolutionRequest.getResolution();
						setCurrentResolution(resolution);
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
			// get hue
			case 6:
				if (httpCode == 200) {
					switch (mGetHueRequest.getResultCode()) {
					case 0:
						String hue = mGetHueRequest.getHue();
						setCurrentHue(hue);
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
			// get photo format
			case 7:
				if (httpCode == 200) {
					switch (mGetPhotoFormatRequest.getResultCode()) {
					case 0:
						String format = mGetPhotoFormatRequest.getFormat();
						setCurrentPhotoFormat(format);
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
			// get audio settings
			case 8:
				if (httpCode == 200) {
					switch (mGetAudioSwitchRequest.getResultCode()) {
					case 0:
						String audio = mGetAudioSwitchRequest.getAudio();
						setCurrentAudioSettings(audio);
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
			// format sdcard
			case 9:
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
			// reset settings
			case 10:
				if (httpCode == 200) {
					switch (mResetSettingsRequest.getResultCode()) {
					case 0:
						showToast(R.string.reset_success);
						break;
					default:
						showToast(R.string.reset_failure);
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
			default:
				break;
			}
		}
		
	};
	
	
	
	
	private void setCurrentResolution(String resolution) {
		
		String[] reg = resolution.split("F");
		String reg_str = reg[0];
		String fps_str = reg[1];
		int groupId = 0;
		int childId = 0;
		if (reg_str.equals("4096x2160")) {
			groupId = 0;
		} else if (reg_str.equals("3840x2160")) {
			groupId = 1;
		} else if (reg_str.equals("2560x1440")) {
			groupId = 2;
		} else if(reg_str.equals("1920x1080")) {
			groupId = 3;
		}
		
		if (fps_str.equals("24")) {
			childId = 0;
		} else if (fps_str.equals("25")) {
			childId = 1;
		} else if (fps_str.equals("30")) {
			childId = 2;
		} else if (fps_str.equals("48")) {
			childId = 3;
		} else if (fps_str.equals("50")) {
			childId = 4;
		} else if (fps_str.equals("60")) {
			childId = 5;
		} else if (fps_str.equals("120")) {
			childId = 6;
		}
		
		if (mResolutionAdapter != null) {
			mResolutionAdapter.setSelectedGroupId(groupId);
			mResolutionAdapter.setSelectedChildId(childId);
			mResolutionAdapter.notifyDataSetChanged();
		}
		
	}
	
	
	protected void setCurrentAudioSettings(String audio) {
		
		if (audio.equals("1")) {
			sv_audio.setOn(false);
		} else if (audio.equals("0")) {
			sv_audio.setOn(true);
		}
		
	}



	private void setCurrentPhotoFormat(String format) {
		
		int selectedId = 0;
		if (format.equals("jpg")) {
			selectedId = 0;
		} else if (format.equals("dng")) {
			selectedId = 1;
		}
		
		if (mPhotoFormatAdaper != null) {
			mPhotoFormatAdaper.setSelectedId(selectedId);
			mPhotoFormatAdaper.notifyDataSetChanged();
		}
		
	}



	private void setCurrentHue(String hue) {
		
		int selectedId = 0;
		if (hue.equals("0")) {
			selectedId = 0;
		} else if (hue.equals("1")) {
			selectedId = 1;
		} else if (hue.equals("2")) {
			selectedId = 2;
		} else if (hue.equals("3")) {
			selectedId = 3;
		}
		
		if (mHueAdaper != null) {
			mHueAdaper.setSelectedId(selectedId);
			mHueAdaper.notifyDataSetChanged();
		}
		
	}



	@Override
	protected void setListener() {
		
		bt_finish.setOnClickListener(this);
		
		bt_resolution.setOnClickListener(this);
		bt_hue.setOnClickListener(this);
		bt_photo_format.setOnClickListener(this);
		bt_audio_settings.setOnClickListener(this);
		bt_reset.setOnClickListener(this);
		bt_format.setOnClickListener(this);
		bt_share.setOnClickListener(this);
		bt_about.setOnClickListener(this);
		
		el_resolution.setOnChildClickListener(new MyOnResolutionChildClickListener());
		lv_hue.setOnItemClickListener(new MyHueOnItemClickListener());
		lv_photo_format.setOnItemClickListener(new MyPhotoFormatOnItemClickListener());
		
		sv_audio.setOnSwitchStateChangeListener(new MyOnSwitchStateChangeListener());
		
	}

	@Override
	protected void init() {
		initUIView();
		initMenu();
		initAdapter();
		showResolutionSettings();
	}


	
	
	
	
	private void initAdapter() {
		
		 mResolutionAdapter = new ResolutionAdapter(resolution_group, resolution_child, getApplicationContext());
		 el_resolution.setAdapter(mResolutionAdapter);
		 
		 mHueAdaper = new HueAdaper(getApplicationContext(), hue_list);
		 lv_hue.setAdapter(mHueAdaper);
		 
		 mPhotoFormatAdaper = new PhotoFormatAdaper(getApplicationContext(), photo_format_list);
		 lv_photo_format.setAdapter(mPhotoFormatAdaper);
		 
		 mShareAppAdapter = new ShareAppAdapter(getApplicationContext());
		 gv_item.setAdapter(mShareAppAdapter);
		 
	}


	private void initUIView() {
		bt_finish = getView(R.id.bt_finish);
		
		dl_container = getView(R.id.dl_container);
		gv_item = getView(R.id.gv_item);
		
		bt_resolution = getView(R.id.bt_resolution);
		bt_hue = getView(R.id.bt_hue);
		bt_photo_format = getView(R.id.bt_photo_format);
		bt_audio_settings = getView(R.id.bt_audio_settings);
		bt_reset = getView(R.id.bt_reset);
		bt_format = getView(R.id.bt_format);
		bt_share = getView(R.id.bt_share);
		bt_about = getView(R.id.bt_about);
		
		
		settings_container = getView(R.id.settings_container);
		tv_settings_title = getView(R.id.tv_settings_title);
	}

	
	

	private void initMenu() {
			
		view_resolution = mInflater.inflate(R.layout.sub_view_resolution, null);
		el_resolution = (ExpandableListView) view_resolution.findViewById(R.id.lv_resolution);
		
		view_hue = mInflater.inflate(R.layout.sub_view_hue, null);
		lv_hue = (ListView) view_hue.findViewById(R.id.lv_hue);
		
		view_photo_format = mInflater.inflate(R.layout.sub_view_photo_format, null);
		lv_photo_format = (ListView) view_photo_format.findViewById(R.id.lv_photo_format);
		
		view_audio = mInflater.inflate(R.layout.sub_view_audio_settings, null);
		sv_audio = (ShSwitchView) view_audio.findViewById(R.id.sv_audio);
		sv_audio.setOn(false);
		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getResolutionRequest();
	}
	
	
	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bt_finish:
			finish();
			break;
		// resolution settings
		case R.id.bt_resolution:
			showResolutionSettings();
			break;
		case R.id.bt_hue:
			getHueRequest();
			showHueSettings();
			break;
		case R.id.bt_photo_format:
			getPhotoFormatRequest();
			showPhotoFormatSettings();
			break;
		// Record Audio settings
		case R.id.bt_audio_settings:
			getAudioSwitchRequest();
			showAudioSettings();
			break;
		case R.id.bt_reset:
			showResetTipsDialog();
			break;
		case R.id.bt_format:
			showFormatTipsDialog();
			break;
		case R.id.bt_share:
			dl_container.openDrawer(Gravity.RIGHT);
			break;
		case R.id.bt_about:
			startActivity(new Intent(getApplicationContext(),AboutActivity.class));
			break;

		default:
			break;
		}
		
		
	}


	
	
	


	private class MyOnResolutionChildClickListener implements OnChildClickListener {

		@Override
		public boolean onChildClick(ExpandableListView arg0, View view,
				int groupPosition, int childPosition, long arg4) {
			
			switch (groupPosition) {
			// 4096*2160
			case 0:
				switch (childPosition) {
				// 24p
				case 0:
					setResolutionRequest("4096x2160F24");
					break;
				// 25p
				case 1:
					setResolutionRequest("4096x2160F25");
					break;
				default:
					break;
				}
				break;
			// 3840*2160
			case 1:
				switch (childPosition) {
				// 24p
				case 0:
					setResolutionRequest("3840x2160F24");
					break;
				// 25p
				case 1:
					setResolutionRequest("3840x2160F25");
					break;
				// 30p
				case 2:
					setResolutionRequest("3840x2160F30");
					break;
				default:
					break;
				}
				break;
			// 2560x1440
			case 2:
				switch (childPosition) {
				// 24p
				case 0:
					setResolutionRequest("2560x1440F24");
					break;
				// 25p
				case 1:
					setResolutionRequest("2560x1440F25");
					break;
				// 30p
				case 2:
					setResolutionRequest("2560x1440F30");
					break;
				default:
					break;
				}
				break;
			// 1920x1080
			case 3:
				switch (childPosition) {
				// 24p
				case 0:
					setResolutionRequest("1920x1080F24");
					break;
				// 25p
				case 1:
					setResolutionRequest("1920x1080F25");
					break;
				// 30p
				case 2:
					setResolutionRequest("1920x1080F30");
					break;
				// 48p
				case 3:
					setResolutionRequest("1920x1080F48");
					break;
				// 50p
				case 4:
					setResolutionRequest("1920x1080F50");
					break;
				// 60p
				case 5:
					setResolutionRequest("1920x1080F60");
					break;
				// 120p
				case 6:
					setResolutionRequest("1920x1080F120");
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			
			return false;
		}
		
	}
	
	
	
	private class MyHueOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			switch (position) {
			// nature 0
			case 0:
				setHueRequest("0");
				break;
			// gorgeous 1  1 0
			case 1:
				setHueRequest("1");
				break;
			// raw 2
			case 2:
				setHueRequest("2");
				break;
			// Light 3
			case 3:
				setHueRequest("3");		
				break;
			default:
				break;
			}
			
		}
		
	}
	
	
	private class MyPhotoFormatOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			switch (position) {
			// jpg 
			case 0:
				setPhotoFormatRequest("jpg");
				break;
			// dng
			case 1:
				setPhotoFormatRequest("dng");
				break;
			default:
				break;
			}
			
		}
		
	}
	
	
	
	private class MyOnSwitchStateChangeListener implements OnSwitchStateChangeListener {

		@Override
		public void onSwitchStateChange(boolean isOn) {
			
			if (isOn) {
				setAudioSwitchRequest("0");
			} else {
				setAudioSwitchRequest("1"); // 1 close
			}
			
		}
		
	}
	
	
	
	private void showResetTipsDialog() {
		settings_container.removeAllViews();
		bt_resolution.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_hue.setBackgroundResource(R.drawable.com_item_bg_selector);
		final AlertDialog alertDialog = new AlertDialog(SettingsActivity.this);
		alertDialog.setMessage(R.string.reset_tips);
		alertDialog.setCancelable(true);
		alertDialog.setPositiveButton(getString(R.string.confirm), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
				resetSettingsRequest();
			}
		});
		
		alertDialog.setNegativeButton(getString(R.string.cancel), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}
	
	
	
	private void showFormatTipsDialog() {
		settings_container.removeAllViews();
		bt_resolution.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_hue.setBackgroundResource(R.drawable.com_item_bg_selector);
		final AlertDialog alertDialog = new AlertDialog(SettingsActivity.this);
		alertDialog.setMessage(R.string.format_tips);
		alertDialog.setCancelable(true);
		alertDialog.setPositiveButton(getString(R.string.confirm), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
				formatSDcardRequest();
			}
		});
		
		alertDialog.setNegativeButton(getString(R.string.cancel), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}
	
	
	
	
	private void showResolutionSettings() {
		
		settings_container.removeAllViews();
		settings_container.addView(view_resolution);
		tv_settings_title.setText(R.string.resolution_settings);
		
		bt_resolution.setBackgroundColor(getResources().getColor(R.color.gray75));
		bt_hue.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_photo_format.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_audio_settings.setBackgroundResource(R.drawable.com_item_bg_selector);
		
	}

	
	private void showHueSettings() {
		
		settings_container.removeAllViews();
		settings_container.addView(view_hue);
		tv_settings_title.setText(R.string.hue_settings);
		
		bt_hue.setBackgroundColor(getResources().getColor(R.color.gray75));
		bt_resolution.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_photo_format.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_audio_settings.setBackgroundResource(R.drawable.com_item_bg_selector);
	}
	
	
	
	private void showPhotoFormatSettings() {
		settings_container.removeAllViews();
		settings_container.addView(view_photo_format);
		tv_settings_title.setText(R.string.photo_settings);
		
		bt_photo_format.setBackgroundColor(getResources().getColor(R.color.gray75));
		bt_resolution.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_hue.setBackgroundResource(R.drawable.com_item_bg_selector);
		
		bt_audio_settings.setBackgroundResource(R.drawable.com_item_bg_selector);
	}
	
		
	private void showAudioSettings() {
		
		settings_container.removeAllViews();
		settings_container.addView(view_audio);
		tv_settings_title.setText(R.string.audio_settings);
		
		sv_audio.setOn(false);
		bt_audio_settings.setBackgroundColor(getResources().getColor(R.color.gray75));
		bt_photo_format.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_resolution.setBackgroundResource(R.drawable.com_item_bg_selector);
		bt_hue.setBackgroundResource(R.drawable.com_item_bg_selector);
		
	}
	

	@Override
	protected void addActivity() {
		mApplication.addActivity(this);
	}
	
	@Override
	protected void initProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
	}


}