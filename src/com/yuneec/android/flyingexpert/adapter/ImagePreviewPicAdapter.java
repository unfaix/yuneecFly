package com.yuneec.android.flyingexpert.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseListAdapter;
import com.yuneec.android.flyingexpert.entity.ResourceInfo;
import com.yuneec.android.flyingexpert.logic.comn.http.HttpRequest;


public class ImagePreviewPicAdapter extends BaseListAdapter<ResourceInfo> {

	private DisplayImageOptions options;
	
	public ImagePreviewPicAdapter(Context context, List<ResourceInfo> list) {
		super(context, list);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.cgo3_blank)
		.showImageForEmptyUri(R.drawable.cgo3_blank)
		.showImageOnFail(R.drawable.cgo3_blank)
		.cacheInMemory(false)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
	}

	
	@Override
	protected View bindView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_photo_preview_pic, parent, false);
			holder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
			holder.pb_progress = (ProgressBar) convertView.findViewById(R.id.pb_progress);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		ImageLoader.getInstance().displayImage(
				HttpRequest.PIC_DOWNLOAD_URL+mList.get(position).getLink(), 
				holder.iv_photo,
				options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						holder.pb_progress.setProgress(0);
						holder.pb_progress.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						holder.pb_progress.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						holder.pb_progress.setVisibility(View.GONE);
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view, int current, int total) {
						holder.pb_progress.setProgress(Math.round(100.0f * current / total));
					}
				}
		);
		return convertView;
	}


	static class ViewHolder {
		ImageView iv_photo;
		ProgressBar pb_progress;
	}
	

}
