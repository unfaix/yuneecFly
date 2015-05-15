package com.yuneec.android.flyingexpert.library;

import java.text.NumberFormat;
import java.util.List;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.entity.ResourceInfo;
import com.yuneec.android.flyingexpert.logic.http.HttpRequest;
import com.yuneec.android.flyingexpert.transfer.download.db.dao.Dao;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * *******************************************************************************
 * ImageExportDialog
 * @Author yongdaimi
 * @Remark 
 * @Date Apr 30, 2015 3:27:26 PM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class ImageExportDialog extends Dialog {

	private Handler mViewUpdateHandler;
	private Context mContext;

	private ProgressBar mProgress;
	// private TextView mProgressNumber;
	private TextView mProgressPercent;
	private TextView mMessageView;

	private String mProgressNumberFormat;

	private NumberFormat mProgressPercentFormat;

	private int mMax;
	private int mProgressVal;
	private int mIncrementBy;

	private CharSequence mMessage;

	private boolean mHasStarted;

	public ImageExportDialog(Context context) {
		this(context, R.style.common_progress_dialog);
	}

	public ImageExportDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	
	private List<ResourceInfo> infos = null;
	
	public void setInfos(List<ResourceInfo> infos) {
		this.infos = infos;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater mInflater = LayoutInflater.from(mContext);
		mViewUpdateHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				/* Update the number and percent */
				int progress = mProgress.getProgress();
				int max = mProgress.getMax();
				double percent = (double) progress / (double) max;
				String format = mProgressNumberFormat;
				// mProgressNumber.setText(String.format(format, progress, max));
				SpannableString tmp = new SpannableString(
						mProgressPercentFormat.format(percent));
				tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
						tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				mProgressPercent.setText(tmp);
				if (progress == max) {
					if (infos != null && infos.size() >0) {
						for(ResourceInfo info:infos) {
							Dao.getInstance(mContext).delete(HttpRequest.PIC_DOWNLOAD_URL+info.getLink());
						}
					}
					if (Build.VERSION.SDK_INT < 19) {
						mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
					} else {
						MediaScannerConnection.scanFile(mContext, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/" + "Camera"}, null, null);
					}
					dismiss();
					Toast.makeText(mContext, R.string.export_success, 2).show();
				}
			}

		};

		View view = mInflater
				.inflate(R.layout.widget_image_export_progress_bar_dialog, null);
		mProgress = (ProgressBar) view
				.findViewById(R.id.common_progress_bar_dialog_progress);
		mProgressPercent = (TextView) view
				.findViewById(R.id.common_progress_bar_dialog_progress_percent);
		/*mProgressNumber = (TextView) view
				.findViewById(R.id.common_progress_bar_dialog_progress_number);*/
		mMessageView = (TextView) view
				.findViewById(R.id.common_progress_bar_dialog_title);
		mProgressNumberFormat = "%d/%d";
		mProgressPercentFormat = NumberFormat.getPercentInstance();
		mProgressPercentFormat.setMaximumFractionDigits(0);
		setContentView(view);

		if (mMax > 0) {
			setMax(mMax);
		}

		if (mProgressVal > 0) {
			setProgress(mProgressVal);
		}

		if (mIncrementBy > 0) {
			incrementProgressBy(mIncrementBy);
		}

		if (mMessage != null) {
			setMessage(mMessage);
		}

		onProgressChanged();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mHasStarted = true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		mHasStarted = false;
	}

	public void setMax(int max) {
		if (mProgress != null) {
			mProgress.setMax(max);
			onProgressChanged();
		} else {
			mMax = max;
		}
	}

	public int getMax() {
		if (mProgress != null) {
			return mProgress.getMax();
		}
		return mMax;
	}

	public void setProgress(int value) {
		if (mHasStarted) {
			mProgress.setProgress(value);
			onProgressChanged();
		} else {
			mProgressVal = value;
		}

	}

	public int getProgress() {
		if (mProgress != null) {
			return mProgress.getProgress();
		}
		return mProgressVal;
	}

	public void setMessage(CharSequence message) {
		if (mProgress != null) {
			mMessageView.setText(message);
		} else {
			mMessage = message;
		}
	}

	public void setMessage(int resId) {
		if (mProgress != null) {
			mMessageView.setText(mContext.getString(resId));
		} else {
			mMessage = mContext.getString(resId);
		}
	}

	public void incrementProgressBy(int diff) {
		if (mProgress != null) {
			mProgress.incrementProgressBy(diff);
			onProgressChanged();
		} else {
			mIncrementBy += diff;
		}
	}

	private void onProgressChanged() {
		mViewUpdateHandler.sendEmptyMessage(0);
	}

}