package com.yuneec.android.flyingexpert.transfer.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.yuneec.android.flyingexpert.entity.ResourceInfo;
import com.yuneec.android.flyingexpert.library.ImageExportDialog;
import com.yuneec.android.flyingexpert.logic.http.HttpRequest;

public class SimpleImageDownloader{


	private ExecutorService mThreadPool;
	
	private BaseImageDownloader mDownloader;
	
	private ImageExportDialog mProgressBarDialog;
	
	private static final int CORE_THREAD_NUMS = 6;
	
	private String mSuccessInfo;
	
	private Context mContext;
	
	
	public SimpleImageDownloader(Context context, ImageExportDialog progressBarDialog, String successInfo) {
		this.mContext = context;
		this.mDownloader = new BaseImageDownloader(context, 3000, 5000);
		this.mThreadPool = Executors.newFixedThreadPool(CORE_THREAD_NUMS);
		this.mProgressBarDialog = progressBarDialog;
		this.mSuccessInfo = successInfo;
	}

	
	public void read(String imageUri, String savePath) {
		if (mProgressBarDialog != null) {
			mProgressBarDialog.show();
			DownLoadTask downLoadTask = new DownLoadTask(imageUri,savePath);
			new Thread(downLoadTask).start();
		} else {
			throw new IllegalArgumentException("ProgressBarDialog is empty.");
		}
	}
	
	
	
	/*public void readAll(List<String> uris,String savePath) {
		if (mProgressBarDialog != null) {
			mProgressBarDialog.show();
			if (mThreadPool != null) {
				if (uris != null && uris.size() > 0) {
					for (String uri:uris) {
						DownLoadTask downLoadTask = new DownLoadTask(uri,savePath);
						mThreadPool.execute(downLoadTask);
					}
				} else {
					throw new IllegalArgumentException("Download uri is not empty and null.");
				}
			} else {
				throw new IllegalStateException("Downloader init fail.");
			}
		} else {
			throw new IllegalArgumentException("ProgressBarDialog is empty.");
		}
		
	}*/
	
	
	public void readAll(List<ResourceInfo> uris,String savePath) {
		if (mProgressBarDialog != null) {
			mProgressBarDialog.show();
			if (mThreadPool != null) {
				if (uris != null && uris.size() > 0) {
					for (ResourceInfo info:uris) {
						DownLoadTask downLoadTask = new DownLoadTask(HttpRequest.PIC_DOWNLOAD_URL+info.getLink(),savePath);
						mThreadPool.execute(downLoadTask);
					}
				} else {
					throw new IllegalArgumentException("Download uri is not empty and null.");
				}
			} else {
				throw new IllegalStateException("Downloader init fail.");
			}
		} else {
			throw new IllegalArgumentException("ProgressBarDialog is empty.");
		}
		
	}
	
	
	
	private Handler updateHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			mProgressBarDialog.incrementProgressBy(1);
			if (mProgressBarDialog.getProgress() == mProgressBarDialog.getMax()) {
				mProgressBarDialog.dismiss();
				mThreadPool.shutdown();
				Toast.makeText(mContext, mSuccessInfo, 2).show();
			}
			
		}
		
	};
	
	
	
	private class DownLoadTask implements Runnable {

		private String imageUri;
		private String savePath;
		
		public DownLoadTask(String imageUri, String savePath) {
			this.imageUri = imageUri;
			this.savePath = savePath;
		}
		
		@Override
		public void run() {
			
			InputStream inputStream = null;
			FileOutputStream outputStream = null;
			
			try {
				
				File file = new File(savePath+ File.separator+ getFileName(imageUri));
				if (!file.exists()) {
					file.createNewFile();
				}
				
				outputStream = new FileOutputStream(file);
				inputStream= mDownloader.getStream(imageUri, null);
				
				byte[] buffer = new byte[4096];
				int length = -1;
				
				while((length = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}
				updateHandler.obtainMessage().sendToTarget();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
	
	
	private String getFileName(String urlstr) {
		String filename = urlstr.substring(urlstr.lastIndexOf('/') + 1);
		/*
		 * if (filename == null || "".equals(filename.trim())) {// if can not
		 * get the file name for (int i = 0;; i++) { String mine =
		 * conn.getHeaderField(i); if (mine == null) { break; } if
		 * ("content-disposition".equals(conn.getHeaderFieldKey(i)
		 * .toLowerCase())) { Matcher m =
		 * Pattern.compile(".*filename=(.*)").matcher( mine.toLowerCase()); if
		 * (m.find()) { return m.group(1); } } } filename = UUID.randomUUID() +
		 * ".tmp";// random file name }
		 */
		return filename;
	}
	
	

}


