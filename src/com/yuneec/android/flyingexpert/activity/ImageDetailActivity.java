package com.yuneec.android.flyingexpert.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Bitmap;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.FailReason;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.entity.ResourceInfo;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.logic.http.HttpRequest;

/**
 * ****************************************************************
 * ImageDetailActivity
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class ImageDetailActivity extends BaseActivity implements OnPageChangeListener{

	
	private TextView bt_finish;
	private TextView tv_title;
	private ViewPager vp_image;
	private ViewPagerAdapter mAdapter;
	
	private int currentPosition;
	private List<ResourceInfo> mThumbUris;
	
	
	
	@Override
	protected void setContainer() {
		setContentView(R.layout.activity_image_detail);
	}

	
	
	@Override
	protected void init() {
		
		bt_finish = getView(R.id.bt_finish);
		tv_title = getView(R.id.tv_title);
	}

	@Override
	protected void setListener() {
		bt_finish.setOnClickListener(this);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getBundleExtra("bundle");
		currentPosition = bundle.getInt("position", 0);
		mThumbUris = (List<ResourceInfo>) bundle.getSerializable("thumbUris");
		vp_image = getView(R.id.vp_image);
		mAdapter = new ViewPagerAdapter();
		vp_image.setAdapter(mAdapter);
		vp_image.setCurrentItem(currentPosition);
		vp_image.setOnPageChangeListener(this);
		vp_image.setEnabled(false);
		// set current item pageIndex and total nums.
		tv_title.setText((currentPosition + 1) + "/" + mThumbUris.size());
	}
	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bt_finish:
			finish();
			break;

		default:
			break;
		}
	}
	
	
	
	
	private class ViewPagerAdapter extends PagerAdapter {

		
		@Override
		public int getCount() {
			return mThumbUris.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return  arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View)object;
			container.removeView(view);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View imageLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item_photo_detail, container,false);
			assert imageLayout != null;
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.iv_photo);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.pb_progress);
			ImageLoader.getInstance().displayImage(HttpRequest.PIC_DOWNLOAD_URL+mThumbUris.get(position).getLink(),
					imageView, mImageOptions, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					android.widget.Toast.makeText(getApplicationContext(), message, 2).show();
					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
				}
			});

			container.addView(imageLayout, 0);
			return imageLayout;
		}
		
		
	}
	
	
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int currentPage) {
		tv_title.setText((currentPage + 1) + "/" + mThumbUris.size());
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
