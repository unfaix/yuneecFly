package com.yuneec.android.flyingexpert.base;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * ****************************************************************
 * Adapter Frame
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public abstract class BaseListAdapter<E> extends BaseAdapter {

	protected List<E> mList;
	protected Context mContext;
	protected LayoutInflater mInflater;
	
	public List<E> getList() {
		return mList;
	}
	
	public void setList(List<E> list) {
		this.mList = list;
		notifyDataSetChanged();
	}
	
	
	public void add(E e) {
		this.mList.add(e);
		notifyDataSetChanged();
	}
	
	
	public void addAll(List<E> list) {
		this.mList.addAll(list);
		notifyDataSetChanged();
	}
	
	
	public void remove(int position) {
		this.mList.remove(position);
		notifyDataSetChanged();
	}
	
	
	public BaseListAdapter(Context context, List<E> list) {
		super();
		this.mContext = context;
		this.mList = list;
		mInflater = LayoutInflater.from(context);
	}
	
	
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = bindView(position, convertView, parent);
		return convertView;
	}
	
	
	protected abstract View bindView(int position, View convertView, ViewGroup parent);
	
	

}
