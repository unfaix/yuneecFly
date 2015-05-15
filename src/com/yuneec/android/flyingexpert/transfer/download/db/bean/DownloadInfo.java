package com.yuneec.android.flyingexpert.transfer.download.db.bean;
 

/**
 * ****************************************************************
 * Download entity
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
 public class DownloadInfo {
	 
     private int threadId;// threadId
     private int startPos;// startPos
     private int endPos;// endPos
     private int compeleteSize;//complete
     private String url;// downloader tag
     private String savePath;
     
     public DownloadInfo(int threadId, int startPos, int endPos,
             int compeleteSize,String url, String savePath) {
         this.threadId = threadId;
         this.startPos = startPos;
         this.endPos = endPos;
         this.compeleteSize = compeleteSize;
         this.url=url;
         this.savePath = savePath;
     }
     
     public DownloadInfo() {
     }
     
     public String getUrl() {
         return url;
     }
     public void setUrl(String url) {
         this.url = url;
     }
     public int getThreadId() {
         return threadId;
     }
     public void setThreadId(int threadId) {
         this.threadId = threadId;
     }
     public int getStartPos() {
         return startPos;
     }
     public void setStartPos(int startPos) {
         this.startPos = startPos;
     }
     public int getEndPos() {
         return endPos;
     }
     public void setEndPos(int endPos) {
         this.endPos = endPos;
     }
     public int getCompeleteSize() {
         return compeleteSize;
     }
     public void setCompeleteSize(int compeleteSize) {
         this.compeleteSize = compeleteSize;
     }
     public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	@Override
	public String toString() {
		return "DownloadInfo [threadId=" + threadId + ", startPos=" + startPos
				+ ", endPos=" + endPos + ", compeleteSize=" + compeleteSize
				+ ", url=" + url + ", savePath=" + savePath + "]";
	}

	
 }