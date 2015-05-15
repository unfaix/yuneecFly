package com.yuneec.android.flyingexpert.transfer.download;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.yuneec.android.flyingexpert.transfer.download.db.bean.DownloadInfo;
import com.yuneec.android.flyingexpert.transfer.download.db.bean.LoadInfo;
import com.yuneec.android.flyingexpert.transfer.download.db.dao.Dao;
import com.yuneec.android.flyingexpert.util.LogX;

/**
 * **************************************************************** 
 * DownloadHelper
 * @Author yongdaimi
 * @Remark
 * @Date Mar 18, 2015 3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ******************************************************************** 
 */
public class DownloadHelper {

	public static final String TAG = "DownloadHelper";

	/* Download Thread Nums */
	public static final int THREAD_COUNT = 4;

	private Context context;

	public DownloadHelper(Context context) {
		this.context = context;
	}

	
	 public class SaveTask implements Runnable {

		private String urlstr;
		private CyclicBarrier mCyclicBarrier;

		public SaveTask(String urlstr, CyclicBarrier cb) {
			this.urlstr = urlstr;
			this.mCyclicBarrier = cb;
		}

		@Override
		public void run() {
			try {
				if (isFirst(urlstr)) {
					URL url = new URL(urlstr);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(5000);
					connection.setRequestMethod("GET");
					int fileSize = connection.getContentLength();
					
					File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"DCIM"+File.separator+"Camera");
					if (!folder.exists()) {
						folder.mkdirs();
					}
					
					File file = new File(folder,getFileName(urlstr));
					if (!file.exists()) {
						file.createNewFile();
					}
					
					// Native Access File
					RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
					accessFile.setLength(fileSize);
					accessFile.close();
					// connection.disconnect();
					// Write Database.
					int range = fileSize / THREAD_COUNT;
					List<DownloadInfo> infos = new ArrayList<DownloadInfo>();
					for (int i = 0; i < THREAD_COUNT - 1; i++) {
						DownloadInfo info = new DownloadInfo(i, i * range, (i + 1)* range - 1, 0, urlstr, file.getAbsolutePath());
						infos.add(info);
					}
					DownloadInfo info = new DownloadInfo(THREAD_COUNT - 1,(THREAD_COUNT - 1) * range, fileSize - 1, 0, urlstr,file.getAbsolutePath());
					infos.add(info);
					Dao.getInstance(context).saveInfos(infos);
					LogX.i(TAG, Thread.currentThread().getName()+" has complete.");
				}
				LogX.i(TAG, Thread.currentThread().getName()+" is already add.");
				mCyclicBarrier.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

	/**
	 * get loadinfo
	 * 
	 * @return loadInfos
	 */
	public List<LoadInfo> getLoadInfos() {
		List<String> urls = Dao.getInstance(context).getExistUrls();
		List<LoadInfo> loadInfos = new ArrayList<LoadInfo>();
		if (urls != null && urls.size() > 0) {
			for (String urlstr : urls) {
				List<DownloadInfo> infos = Dao.getInstance(context).getInfos(
						urlstr);
				int size = 0;
				int compeleteSize = 0;
				if (infos != null && infos.size() > 0) {
					for (DownloadInfo info : infos) {
						compeleteSize += info.getCompeleteSize();
						size += info.getEndPos() - info.getStartPos() + 1;
					}
				}
				LoadInfo loadInfo = new LoadInfo(size, compeleteSize, urlstr,
						getFileName(urlstr));
				loadInfos.add(loadInfo);
			}
		}
		return loadInfos;
	}

	
	public LoadInfo getLoadinfo(String urlstr) {
		List<DownloadInfo> infos = Dao.getInstance(context).getInfos(urlstr);
		int size = 0;
		int compeleteSize = 0;
		if (infos != null && infos.size() > 0) {
			for (DownloadInfo info : infos) {
				compeleteSize += info.getCompeleteSize();
				size += info.getEndPos() - info.getStartPos() + 1;
			}
		}
		LoadInfo loadInfo = new LoadInfo(size, compeleteSize, urlstr,getFileName(urlstr));
		return loadInfo;
	}
	
	
	public List<DownloadInfo> getDownloadInfos(String url) {
		return Dao.getInstance(context).getInfos(url);
	}

	public boolean isFirst(String urlstr) {
		return Dao.getInstance(context).isHasInfors(urlstr);
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
