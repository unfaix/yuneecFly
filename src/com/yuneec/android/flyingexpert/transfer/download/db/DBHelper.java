package com.yuneec.android.flyingexpert.transfer.download.db;
 
 import com.yuneec.android.flyingexpert.util.SystemUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
     /**
      * create dbhelper
      */
 public class DBHelper extends SQLiteOpenHelper {
     //download.db-->dbName
     public DBHelper(Context context) {
         super(context, SystemUtil.getConnectionWIFIName(context)+"_"+DBConfig.DBNAME, null, DBConfig.DBVERSION);
     }
     
     /**
      * table name : download_info
      */
     @Override
     public void onCreate(SQLiteDatabase db) {
         db.execSQL("create table download_info(_id integer PRIMARY KEY AUTOINCREMENT, thread_id integer, "
                 + "start_pos integer, end_pos integer, compelete_size integer,url char,save_path char)");
     }
     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL("DROP TABLE IF EXISTS download_info");
 		onCreate(db);
     }
 
 }