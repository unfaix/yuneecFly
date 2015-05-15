package com.yuneec.android.flyingexpert.transfer.download.adapter;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseListAdapter;
import com.yuneec.android.flyingexpert.transfer.download.db.bean.LoadInfo;
import com.yuneec.android.flyingexpert.util.FileSizeFormatUtil;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * ****************************************************************
 * Download Adapter
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class DownloadAdapter extends BaseListAdapter<LoadInfo> {

	
	private NumberFormat mProgressPercentFormat;
	private OnPauseListener onPauseListener;
	
	private Map<String,ViewHolder> holders = new HashMap<String,ViewHolder>();
	
	public DownloadAdapter(Context context, List<LoadInfo> list) {
		super(context, list);
		mProgressPercentFormat = NumberFormat.getPercentInstance();
		mProgressPercentFormat.setMaximumFractionDigits(0);
	}


	

	public void setOnPauseListener(OnPauseListener onPauseListener) {
		this.onPauseListener = onPauseListener;
	}
	


	public Map<String, ViewHolder> getHolders() {
		return holders;
	}


	@Override
	protected View bindView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item_download, parent, false);
			holder.iv_pic_photo = (ImageView) convertView.findViewById(R.id.iv_pic_photo);
			holder.pb_pic_progress = (ProgressBar) convertView.findViewById(R.id.pb_pic_progress);
			holder.tv_file_name = (TextView) convertView.findViewById(R.id.tv_file_name);
			holder.pb_download_progress = (ProgressBar) convertView.findViewById(R.id.pb_download_progress);
			holder.tv_complete_size = (TextView) convertView.findViewById(R.id.tv_complete_size);
			holder.tv_download_percent = (TextView) convertView.findViewById(R.id.tv_download_percent);
			holder.bt_download_start = (Button) convertView.findViewById(R.id.bt_download_start);
			holder.bt_download_pause = (Button) convertView.findViewById(R.id.bt_download_pause);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		final LoadInfo loadInfo = mList.get(position);
		
		holder.bt_download_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onPauseListener != null) {
					onPauseListener.onStart(v,loadInfo.getUrlstring());
				}
			}
		});
		
		holder.bt_download_pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (onPauseListener != null) {
					onPauseListener.onPause(v,loadInfo.getUrlstring());
				}
			}
		});
		
		holder.iv_pic_photo.setImageResource(R.drawable.cgo3_media);
		holder.tv_file_name.setText("test"+position);
		
		int max = loadInfo.getFileSize();
		int progress = loadInfo.getComplete();
		
		double percent = (double) progress / (double) max;
		
		holder.pb_download_progress.setMax(max);
		holder.pb_download_progress.setProgress(progress);
		holder.tv_download_percent.setText(mProgressPercentFormat.format(percent));
		if (percent == 1.0d) {
			holder.tv_download_percent.setTextColor(mContext.getResources().getColor(R.color.orange));
			holder.tv_download_percent.setText(R.string.download_complete);
		} else {
			holder.tv_download_percent.setTextColor(mContext.getResources().getColor(R.color.green));
			holder.tv_download_percent.setText(mProgressPercentFormat.format(percent));
		}
		holder.tv_complete_size.setText(FileSizeFormatUtil.getFileSize(max));
		holder.tv_file_name.setText(loadInfo.getResourceName());
		holders.put(loadInfo.getUrlstring(), holder);
		return convertView;
	}
	
	
	
	 public class ViewHolder {
		
		ImageView iv_pic_photo;
		ProgressBar pb_pic_progress;
		TextView tv_file_name;
		public ProgressBar pb_download_progress;
		TextView tv_complete_size;
		public TextView tv_download_percent;
		Button bt_download_start;
		Button bt_download_pause;
		
	}
	
	
	
	public interface OnPauseListener {
		void onStart(View v, String url);
		void onPause(View v, String url);
	}
	
	

}
