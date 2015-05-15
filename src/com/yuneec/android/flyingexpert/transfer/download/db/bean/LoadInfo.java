package com.yuneec.android.flyingexpert.transfer.download.db.bean;

/**
 * ****************************************************************
 * Loadinfo entity
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
 public class LoadInfo {
	 
     public int fileSize;// file size
     private String urlstring;// download tag
     private int complete;// complete
     private String resourceName; // resourceName
     
     
     public LoadInfo(int fileSize, int complete, String urlstring, String resourceName) {
         this.fileSize = fileSize;
         this.complete = complete;
         this.urlstring = urlstring;
         this.resourceName = resourceName;
     }
     
     
     public LoadInfo() {
     }
     
     public int getFileSize() {
         return fileSize;
     }
     
     public void setFileSize(int fileSize) {
         this.fileSize = fileSize;
     }
     public int getComplete() {
         return complete;
     }
     public void setComplete(int complete) {
         this.complete = complete;
     }
     public String getUrlstring() {
         return urlstring;
     }
     public void setUrlstring(String urlstring) {
         this.urlstring = urlstring;
     }

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public String toString() {
		return "LoadInfo [fileSize=" + fileSize + ", urlstring=" + urlstring
				+ ", complete=" + complete + ", resourceName=" + resourceName
				+ "]";
	}
     
 }