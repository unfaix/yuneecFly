package com.yuneec.android.flyingexpert.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.entity.ResourceInfo;
import com.yuneec.android.flyingexpert.logic.comn.http.HttpRequest;


/**
 * ****************************************************************
 * ImagePreviewListAdapter
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class ImagePreviewListAdapter extends BaseAdapter {

	
	private int type = 0; // 0 image 1 media
	
	private DisplayImageOptions options;
	
	private List<List<ResourceInfo>> mList;
	
	private LayoutInflater mInflater;
	
	public ImagePreviewListAdapter(Context context, List<List<ResourceInfo>> list) {
		this.mList = list;
		this.mInflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.cgo3_blank)
		.showImageForEmptyUri(R.drawable.cgo3_blank)
		.showImageOnFail(R.drawable.cgo3_blank)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
	}

	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_photo_preview_list, parent, false);
			holder.iv_pic_photo = (ImageView) convertView.findViewById(R.id.iv_pic_photo);
			holder.pb_pic_progress = (ProgressBar) convertView.findViewById(R.id.pb_pic_progress);
			holder.tv_pic_name = (TextView) convertView.findViewById(R.id.tv_pic_name);
			holder.tv_pic_date = (TextView) convertView.findViewById(R.id.tv_pic_date);
			holder.tv_pic_size = (TextView) convertView.findViewById(R.id.tv_pic_size);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ResourceInfo resource = mList.get(type).get(position);
		
		holder.tv_pic_name.setText(resource.getName());
		holder.tv_pic_date.setText(resource.getCreateDate());
		holder.tv_pic_size .setText(resource.getSize());
		
		if (type == 0) {
			ImageLoader.getInstance().displayImage(
					HttpRequest.PIC_DOWNLOAD_URL+resource.getLink(), 
					holder.iv_pic_photo,
					options,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							holder.pb_pic_progress.setProgress(0);
							holder.pb_pic_progress.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
							holder.pb_pic_progress.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							holder.pb_pic_progress.setVisibility(View.GONE);
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri, View view, int current, int total) {
							holder.pb_pic_progress.setProgress(Math.round(100.0f * current / total));
						}
					}
			);
		} else if (type ==1) {
			holder.iv_pic_photo.setImageResource(R.drawable.cgo3_media);
		}
		
		return convertView;
	}


	static class ViewHolder {
		ImageView iv_pic_photo;
		ProgressBar pb_pic_progress;
		TextView tv_pic_name;
		TextView tv_pic_date;
		TextView tv_pic_size;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}



	@Override
	public int getCount() {
		return mList.get(type).size();
	}




	@Override
	public Object getItem(int position) {
		return mList.get(type).get(position);
	}




	@Override
	public long getItemId(int position) {
		return position;
	}
	
	

}
