package com.yuneec.android.flyingexpert.library;


import com.yuneec.android.flyingexpert.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author yongdaimi
 * @remark 
 * @date 2014-12-2 下午04:28:25
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class ProgressDialog extends Dialog {

	private ImageView mLoadingImageView;
	private TextView mLoadingTextView;
	private AnimationDrawable mLoadingAnimationDrawable;

	
	public ProgressDialog(Context context) {
		this(context,R.style.common_progress_dialog);
	}
	
	public ProgressDialog(Context context, int theme) {
		super(context, theme);
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_progress_dialog, null);
		mLoadingImageView = (ImageView) view.findViewById(R.id.loading_animation);
		mLoadingTextView = (TextView) view.findViewById(R.id.loading_text); 
		setContentView(view);
		
	}
	
	
	
	
	@Override
	public void show() {
		super.show();
		mLoadingImageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim));
	}
	
	
	@Override
	public void dismiss() {
		super.dismiss();
		mLoadingImageView.clearAnimation();
	}

	
	public void setMessage(int resId) {
		mLoadingTextView.setText(resId);
	}
	
	public void setMessage(String msg) {
		mLoadingTextView.setText(msg);
	}
	
}
