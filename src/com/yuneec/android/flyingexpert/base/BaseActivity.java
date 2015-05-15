package com.yuneec.android.flyingexpert.base;



import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.yuneec.android.flyingexpert.ConstantValue;
import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.manager.ViewManager;
import com.yuneec.android.flyingexpert.settings.AppConfig;
import com.yuneec.android.flyingexpert.settings.MyApplication;
import com.yuneec.android.flyingexpert.util.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;


/**
 * UI Frame
 * @author yongdaimi
 * @remark 
 * @date 2014-10-9 上午09:51:21
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public abstract class BaseActivity extends Activity 
	implements OnClickListener,OnItemClickListener{
	
	protected MyApplication mApplication;
	protected LayoutInflater mInflater;
	protected ProgressDialog mProgressDialog;
	protected DisplayImageOptions mImageOptions;
	protected Context mContext;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mApplication = MyApplication.getInstance();
		mContext = mApplication.getContext();
		mInflater = LayoutInflater.from(this);
		setContainer();
		init();
		initProgressDialog();
		setListener();
		addActivity();
		initImageOptions();
	}
	
	
	/**
	 * init UI content
	 */
	protected abstract void setContainer();
	
	
	/**
	 * init view
	 */
	protected abstract void init();
	
	
	/**
	 * init UI listener
	 */
	protected abstract void setListener();
	
	
	/**
	 * global regist
	 */
	protected abstract void addActivity();
	
	
	/**
	 * init progressDialog
	 */
	protected abstract void initProgressDialog();
	
	
	private Handler baseHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstantValue.PROMPT_PROGRESS_SHOW:
				assert mProgressDialog != null;
				ViewManager.showSimpleProgressDialog(mProgressDialog,mProgressMsgId);
				break;
			case ConstantValue.PROMPT_PROGRESS_CLOSE:
				assert mProgressDialog != null;
				ViewManager.closeProgressDialog(mProgressDialog);
				break;
			default:
				break;
			}
		}
		
	};
	
	
	private int mProgressMsgId;
	
	/**
	 * show ProgressDialog
	 * @param resId
	 */
	protected void showProgressDialog(int resId) {
		this.mProgressMsgId = resId;
		baseHandler.sendEmptyMessage(ConstantValue.PROMPT_PROGRESS_SHOW);
	}
	
	/**
	 * close ProgressDialog
	 */
	protected void closeProgressDialog() {
		baseHandler.sendEmptyMessage(ConstantValue.PROMPT_PROGRESS_CLOSE);
	}
	
	
	
	Toast mToast = null;
	
	
	/**
	 * show Toast
	 * @param resId
	 */
	protected void showToast(final int resId) {
		// ViewManager.showToast(getApplicationContext(), resId);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					if (mToast == null) {
						mToast = new Toast(mContext);
						mToast.setView(LayoutInflater.from(mContext).inflate(R.layout.widget_toast, null));
						mToast.setText(resId);
						mToast.setDuration(Toast.LENGTH_SHORT);
					} else {
						mToast.setText(resId);
					}
					mToast.show();
				}
			});
	}
	
	
	/**
	 * show Toast
	 * @param resId
	 */
	protected void showToast(final String str) {
		// ViewManager.showToast(getApplicationContext(), resId);
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (mToast == null) {
					mToast = new Toast(mContext);
					mToast.setView(LayoutInflater.from(mContext).inflate(R.layout.widget_toast, null));
					mToast.setText(str);
					mToast.setDuration(Toast.LENGTH_SHORT);
				} else {
					mToast.setText(str);
				}
				mToast.show();
			}
		});
	}
	
	
	
	
	
	/**
	 * find UI element id
	 * @param <T>
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends View> T getView(int id) {
		return (T)findViewById(id);
	}
	
	
	
	@Override
	public void onClick(View v) {
		
	}
	
	
	/**
	 * Init pic argus
	 */
	protected void initImageOptions() {
		initImageOptions(new FadeInBitmapDisplayer(300));
	}
	
	protected void initImageOptions(BitmapDisplayer displayer) {
		mImageOptions =  new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.cgo3_blank)
		.showImageForEmptyUri(R.drawable.cgo3_blank)
		.showImageOnFail(R.drawable.cgo3_blank)
		.cacheInMemory(false)
		.cacheOnDisk(true)
		.considerExifParams(true) 
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(displayer)
		.build();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	
	
	protected void startAnimActivity(Intent intent) {
		startActivity(intent);
		overridePendingTransition(R.anim.popview_sheet_dismiss,R.anim.popview_sheet_show);
	}
	
	protected void startAnimActivityForResult(Intent intent, int requestCode) {
		startActivityForResult(intent,requestCode);
		overridePendingTransition(R.anim.popview_sheet_dismiss,R.anim.popview_sheet_show);
	}
	
	protected void finishAnimActivity() {
		finish();
		overridePendingTransition(R.anim.popview_sheet_dismiss,R.anim.popview_sheet_show);
	}

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mToast != null) {
			mToast.cancel();
		}
		// overridePendingTransition(R.anim.right2left_enter,R.anim.left2right_exit);
	}
	
}
