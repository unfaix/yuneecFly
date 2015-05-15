package com.yuneec.android.flyingexpert.db.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.yuneec.android.flyingexpert.db.dao.DBHelper;
import com.yuneec.android.flyingexpert.db.dao.annotation.Column;
import com.yuneec.android.flyingexpert.db.dao.annotation.ID;
import com.yuneec.android.flyingexpert.db.dao.annotation.Table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public abstract class DAOImpl<T> implements DAO<T> {
	private static final String TAG = "DAOImpl";
	protected DBHelper dbHelper;
	protected SQLiteDatabase db;

	public DAOImpl(Context context) {
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	@Override
	public int delete(Serializable id) {
		// TABLE_ID_COL
		db.delete(getTableName(), DBHelper.TABLE_ID_COL + "=?", new String[] { id.toString() });
		return 0;
	}

	@Override
	public long insert(T t) {

		ContentValues values = new ContentValues();
		fillContentValues(t, values);

		long insert = db.insert(getTableName(), null, values);
		return insert;
	}

	/**
	 * @param t
	 * @param values
	 */
	private void fillContentValues(T t, ContentValues values) {
		// values.put(DBHelper.TABLE_NEW_TITLE_COL, news.getTitle());

		Field[] declaredFields = t.getClass().getDeclaredFields();

		for (Field item : declaredFields) {
			item.setAccessible(true);
			Column column = item.getAnnotation(Column.class);
			if (column != null) {
				String key = column.value();
				// This reproduces the effect of object.fieldName
				String value = "";
				try {
					value = item.get(t).toString();// result：news.title
				} catch (Exception e) {
					e.printStackTrace();
				}

				ID id = item.getAnnotation(ID.class);
				if (id != null) {
					// id
					if (id.autoIncreament()) {
						// ID Increment
					} else {
						values.put(key, value);
					}

				} else {
					// not main key
					values.put(key, value);

				}
			}
		}

	}

	@Override
	public List<T> queryAll() {
		Cursor query = null;
		List<T> list = new ArrayList<T>();
		try {
			query = db.query(getTableName(), null, null, null, null, null, null);
			
			while (query.moveToNext()) {
				
				//
				/**
				 * int columnIndex = query.getColumnIndex(DBHelper.TABLE_NEW_TITLE_COL);
				 * String title = query.getString(columnIndex);
				 */
				T t=getInstence();
				fillInstance(query,t);
				list.add(t);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (query != null)
				query.close();
		}
		return list;
	}
	/**
	 * get Instance Info
	 * @param query
	 * @param t
	 */
	private void fillInstance(Cursor query, T t) {
		/**
		 * int columnIndex = query.getColumnIndex(DBHelper.TABLE_NEW_TITLE_COL);
		 * String title = query.getString(columnIndex);
		 */
		Field[] fields = t.getClass().getDeclaredFields();
		
		for(Field item:fields)
		{
			String key="";
			
			Column column = item.getAnnotation(Column.class);
			if(column!=null)
			{
				key=column.value();
				int columnIndex=query.getColumnIndex(key);
				String value=query.getString(columnIndex);
				try {
					item.set(t,value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		
		
	}

	@Override
	public int update(T t) {
		ContentValues values=new ContentValues();
		fillContentValues(t, values);
		
		db.update(getTableName(),values,DBHelper.TABLE_ID_COL + "=?",new String[] { getId(t) });
		
		return 0;
	}
	
	/**
	 * getId
	 * @param t
	 * @return
	 */
	private String getId(T t) {
		Field[] fields = t.getClass().getDeclaredFields();
		
		for(Field item :fields)
		{
			ID id = item.getAnnotation(ID.class);
			if(id!=null)
			{
				try {
					item.setAccessible(true);
					String value=item.get(t)+"";
					return value;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * get table name
	 * 
	 * @return
	 */
	private String getTableName() {
		T t = getInstence();
		Table table = t.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.value();
		}
		return null;
	}


	/**
	 * cretae T instance
	 * 
	 * @return
	 */
	public T getInstence() {
		
		Type superclass = this.getClass().getGenericSuperclass();
		
		ParameterizedType pt=(ParameterizedType)superclass;
		Type[] actualTypeArguments = pt.getActualTypeArguments();
		Type type=actualTypeArguments[0];
		
		try {
			return (T) ((Class)type).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		Log.i(TAG, super.getClass().getSimpleName());
		return null;
	}

}
