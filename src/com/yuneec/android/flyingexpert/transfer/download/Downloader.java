package com.yuneec.android.flyingexpert.transfer.download;
 
 import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yuneec.android.flyingexpert.transfer.download.db.bean.DownloadInfo;
import com.yuneec.android.flyingexpert.transfer.download.db.dao.Dao;
import com.yuneec.android.flyingexpert.util.LogX;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
 
 public class Downloader {
	 
     private Handler mHandler;// msg handler 
     private Context context;
     private static final int INIT = 1;// define download status
     private static final int DOWNLOADING = 2;
     private static final int PAUSE = 3;
     private int state = INIT;
     private static final int CORE_THREAD_NUMS = 8;
     
     private static final String TAG = "Downloader";
     
     private ExecutorService mThreadPool;
     
     
     public Downloader(Context context, Handler mHandler) {
         this.mHandler = mHandler;
         this.context = context;
         mThreadPool = Executors.newFixedThreadPool(CORE_THREAD_NUMS);
     }
     
     /**
      * check is downloading
      */
     public boolean isdownloading() {
         return state == DOWNLOADING;
     }
 

     /**
      * use thread to download
      */
     public void download(List<DownloadInfo> infos) {
         if (infos != null) {
             if (state == DOWNLOADING)
                 return;
             state = DOWNLOADING;
             for (DownloadInfo info : infos) {
            	 mThreadPool.execute(new MyThread(info.getThreadId(), info.getStartPos(),
                         info.getEndPos(), info.getCompeleteSize(),
                         info.getUrl(),info.getSavePath()));
             }
         }
     }
 
 
     public void shutdown() {
    	 pause();
    	 if (mThreadPool != null) {
    		 mThreadPool.shutdown();
    		 LogX.i(TAG, "threadPool has shutdown.");
		}
     }
     
     
     public class MyThread extends Thread {
         private int threadId;
         private int startPos;
         private int endPos;
         private int compeleteSize;
         private String urlstr;
         private String savePath;
 
         
         public MyThread(int threadId, int startPos, int endPos,
                 int compeleteSize, String urlstr, String savePath) {
             this.threadId = threadId;
             this.startPos = startPos;
             this.endPos = endPos;
             this.compeleteSize = compeleteSize;
             this.urlstr = urlstr;
             this.savePath = savePath;
         }
         @Override
         public void run() {
             HttpURLConnection connection = null;
             RandomAccessFile randomAccessFile = null;
             InputStream is = null;
             try {
                 URL url = new URL(urlstr);
                 connection = (HttpURLConnection) url.openConnection();
                 connection.setConnectTimeout(5000);
                 connection.setRequestMethod("GET");
                 // set Range，format:Range：bytes x-y;
                 connection.setRequestProperty("Range", "bytes="+(startPos + compeleteSize) + "-" + endPos);
 
                 randomAccessFile = new RandomAccessFile(savePath, "rwd");
                 randomAccessFile.seek(startPos + compeleteSize);
                 // save file.
                 is = connection.getInputStream();
                 byte[] buffer = new byte[4096];
                 int length = -1;
                 while ((length = is.read(buffer)) != -1) {
                     randomAccessFile.write(buffer, 0, length);
                     compeleteSize += length;
                     // update db info.
                     Dao.getInstance(context).updataInfos(threadId, compeleteSize, urlstr);
                     // update current msg status.
                     Message message =mHandler.obtainMessage(1);
                     message.obj = urlstr;
                     message.arg1 = length;
                     message.sendToTarget();
                     if (state == PAUSE) {
                         return;
                     }
                 }
             } catch (Exception e) {
                 e.printStackTrace();
             }  
         }
     }
     // delete download info
     public void delete(String urlstr) {
    	 Dao.getInstance(context).delete(urlstr);
     }
     
     // set pause
     public void pause() {
         state = PAUSE;
     }
     
     //reset download status.
     public void reset() {
         state = INIT;
     }
 }