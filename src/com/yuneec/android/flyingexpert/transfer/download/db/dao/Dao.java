package com.yuneec.android.flyingexpert.transfer.download.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.yuneec.android.flyingexpert.transfer.download.db.DBHelper;
import com.yuneec.android.flyingexpert.transfer.download.db.bean.DownloadInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * ****************************************************************
 * Download Data  Access Object
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class Dao {  
	
	private static Dao dao=null;
	private Context context; 
	private ReentrantReadWriteLock readWriteLock;
	
	private Dao(Context context) { 
		this.context=context;
		this.readWriteLock = new ReentrantReadWriteLock();
	}
	
	
	public static Dao getInstance(Context context){
		if(dao==null){
			dao=new Dao(context); 
		}
		return dao;
	}
	
	
	public  SQLiteDatabase getConnection() {
		SQLiteDatabase sqliteDatabase = null;
		try { 
			sqliteDatabase= new DBHelper(context).getReadableDatabase();
		} catch (Exception e) {  
		}
		return sqliteDatabase;
	}
	
	
	/**
	 * check is have.
	 */
	public boolean isHasInfors(String urlstr) {
		
		readWriteLock.readLock().lock();
		SQLiteDatabase database = getConnection();
		int count = -1;
		Cursor cursor = null;
		try {
			String sql = "select count(*)  from download_info where url=?";
			cursor = database.rawQuery(sql, new String[] { urlstr });
			if (cursor.moveToFirst()) {
				count = cursor.getInt(0);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
			if (null != database) {
				database.close();
			}
			readWriteLock.readLock().unlock();
		}
		return count == 0;
	}

	/**
	 * save download info
	 */
	public void saveInfos(List<DownloadInfo> infos) {
		readWriteLock.writeLock().lock();
		SQLiteDatabase database = getConnection();
		try {
			for (DownloadInfo info : infos) {
				String sql = "insert into download_info(thread_id,start_pos, end_pos,compelete_size,url,save_path) values (?,?,?,?,?,?)";
				Object[] bindArgs = { info.getThreadId(), info.getStartPos(),
						info.getEndPos(), info.getCompeleteSize(),
						info.getUrl(),info.getSavePath() };
				database.execSQL(sql, bindArgs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			readWriteLock.writeLock().unlock();
		}
	}

	/**
	 * get download info.
	 */
	public List<DownloadInfo> getInfos(String urlstr) {
		readWriteLock.readLock().lock();
		List<DownloadInfo> list = new ArrayList<DownloadInfo>();
		SQLiteDatabase database = getConnection();
		Cursor cursor = null;
		try {
			String sql = "select thread_id, start_pos, end_pos,compelete_size,url,save_path from download_info where url=?";
			cursor = database.rawQuery(sql, new String[] { urlstr });
			while (cursor.moveToNext()) {
				DownloadInfo info = new DownloadInfo(cursor.getInt(0),
						cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
						cursor.getString(4),cursor.getString(5));
				list.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
			if (null != database) {
				database.close();
			}
			readWriteLock.readLock().unlock();
		}
		return list;
	}
	
	
	/**
	 * get exists urls.
	 * @return
	 */
	public List<String> getExistUrls() {
		readWriteLock.readLock().lock();
		List<String> urls = new ArrayList<String>();
		SQLiteDatabase database = getConnection();
		Cursor cursor = null;
		try {
			String sql = "select url from download_info group by url";
			cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				urls.add(cursor.getString(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor && !cursor.isClosed()) {
				cursor.close();
			}
			if (null != database) {
				database.close();
			}
			readWriteLock.readLock().unlock();
		}
		return urls;
	}
	

	/**
	 * upate download info
	 */
	public void updataInfos(int threadId, int compeleteSize, String urlstr) {
		readWriteLock.writeLock().lock();
		SQLiteDatabase database = getConnection();
		try {
			String sql = "update download_info set compelete_size=? where thread_id=? and url=?";
			Object[] bindArgs = { compeleteSize, threadId, urlstr };
			database.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			readWriteLock.writeLock().unlock();
		}
	}

	/**
	 * delete download data.
	 */
	public void delete(String url) {
		readWriteLock.writeLock().lock();
		SQLiteDatabase database = getConnection();
		try {
			database.delete("download_info", "url=?", new String[] { url });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != database) {
				database.close();
			}
			readWriteLock.writeLock().unlock();
		}
	}
}