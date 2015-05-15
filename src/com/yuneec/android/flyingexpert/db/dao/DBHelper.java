package com.yuneec.android.flyingexpert.db.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "itcast.db";
	public static final int DBVERSION = 1;

	public DBHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	/**
	 * 
	 */
	public static final String TABLE_ID_COL = "_ID";// ID

	/**
	 * table
	 */
	public static final String TABLE_NEW = "news";
	public static final String TABLE_NEW_TITLE_COL = "TITLE";// title
	public static final String TABLE_NEW_SUMMARY_COL = "SUMMARY";// summary
	/**
	 * book
	 */
	public static final String TABLE_BOOK = "book";
	public static final String TABLE_BOOK_NAME_COL = "name";// book_name

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NEW + " (" + //
				TABLE_ID_COL + " integer primary key autoincrement, " + //
				TABLE_NEW_TITLE_COL + " varchar(50), " + //
				TABLE_NEW_SUMMARY_COL + " VARCHAR(200))"//
		);
		db.execSQL("CREATE TABLE " + TABLE_BOOK + " (" + //
				TABLE_ID_COL + " integer primary key autoincrement, " + //
				TABLE_BOOK_NAME_COL + " VARCHAR(200))"//
		);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
