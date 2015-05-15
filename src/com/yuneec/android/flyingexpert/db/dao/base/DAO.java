package com.yuneec.android.flyingexpert.db.dao.base;

import java.io.Serializable;
import java.util.List;

/**
 * abstract All database operate
 * @author Administrator
 *
 * @param <T>
 */
public interface DAO<T> {
	public long insert(T t);
	
	public int delete(Serializable id);
	
	public int update(T t);
	
	public List<T> queryAll();
	/**
	 * test case
	 * @return
	 */
	public T getInstence();
	
}
