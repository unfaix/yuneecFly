package com.yuneec.android.flyingexpert.adapter;

import java.util.List;

import com.yuneec.android.flyingexpert.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * ****************************************************************
 * Settings ResolutionAdatper 
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class ResolutionAdapter extends BaseExpandableListAdapter {


	private List<String> mGroupList;
	private List<List<String>> mChildList;
	private Context mContext;
	
	private int selectedGroupId = 0;
	private int selectedChildId = 0;
	
	
	
	public ResolutionAdapter(List<String> mGroupList,
			List<List<String>> mChildList, Context mContext) {
		super();
		this.mGroupList = mGroupList;
		this.mChildList = mChildList;
		this.mContext = mContext;
	}

	
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mChildList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChildList.get(groupPosition).size();
	}

	
	@Override
	public Object getGroup(int groupPosition) {
		return mGroupList.get(groupPosition);
	}

	
	@Override
	public int getGroupCount() {
		return mGroupList.size();
	}

	
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	
	
	public void setSelectedGroupId(int selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}


	public void setSelectedChildId(int selectedChildId) {
		this.selectedChildId = selectedChildId;
	}


	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		
		String string = mChildList.get(groupPosition).get(childPosition);
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_settings_option, null);
		TextView tv_resolution_option = (TextView) view.findViewById(R.id.tv_settings_option);
		ImageView iv_settings_selected = (ImageView) view.findViewById(R.id.iv_settings_selected);
		tv_resolution_option.setText(string);
		if (selectedGroupId == groupPosition && selectedChildId == childPosition) {
			iv_settings_selected.setVisibility(View.VISIBLE);
		} else {
			iv_settings_selected.setVisibility(View.GONE);
		}
		return view;
	}
	
	
	
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		return getGroupView(mGroupList.get(groupPosition));
	}
	
	
	private View getGenericView(String str) {
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_settings_option, null);
		TextView tv_resolution_option = (TextView) view.findViewById(R.id.tv_settings_option);
		tv_resolution_option.setText(str);
		return view;
		
	}
	
	private View getGroupView(String str) {
		
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_settings_group, null);
		TextView tv_settings_group = (TextView) view.findViewById(R.id.tv_settings_group);
		tv_settings_group.setText(str);
		return view;
		
	}
	
}
