package com.yuneec.android.flyingexpert.transfer.download.db;

/**
 * ****************************************************************
 * DBconfig
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class DBConfig {

	public static final String DBNAME = "yuneec_dl.db";
	public static final int DBVERSION= 1;
	
	public String getDbName() {
		return DBNAME;
	}
	
	public int getDbVersion() {
		return DBVERSION;
	}
	

}
