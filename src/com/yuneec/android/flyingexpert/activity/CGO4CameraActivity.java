package com.yuneec.android.flyingexpert.activity;

import tv.danmaku.ijk.media.widget.VideoView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.logic.cgo4.rtsp.CGO4_RtspRequestManager;
import com.yuneec.android.flyingexpert.logic.cgo4.rtsp.impl.SetMicrophoneVolumeRequest;

public class CGO4CameraActivity extends BaseActivity {

	
	private static final String mVideoPath = "rtsp://192.168.73.254:8557/PSIA/Streaming/channels/2?videoCodecType=H.264";
	
	
	private VideoView vv_scene;
	private SetMicrophoneVolumeRequest mSetMicrophoneVolumeRequest;
	
	
	
	
	
	private void setMicrophoneVolumeRequest() {
		
		mSetMicrophoneVolumeRequest = new SetMicrophoneVolumeRequest();
		CGO4_RtspRequestManager.sendRequest(getApplicationContext(), mSetMicrophoneVolumeRequest,
				mHandler.obtainMessage(1));
		
	}
	
	
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				
				break;

			default:
				break;
			}
		}
		
		
	};
	
	
	@Override
	protected void setContainer() {
		setContentView(R.layout.activity_cgo4_camera);
	}

	
	
	@Override
	protected void init() {
		vv_scene = getView(R.id.vv_scene);
		

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		updateVideoViewStatus();
		setMicrophoneVolumeRequest();
	}
	
	
	
	private void updateVideoViewStatus() {
		
		vv_scene.setVideoPath(mVideoPath);
		vv_scene.requestFocus();
		vv_scene.start();
	
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
