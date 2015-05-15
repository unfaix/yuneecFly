package com.yuneec.android.flyingexpert.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseListAdapter;


public class PhotoFormatAdaper extends BaseListAdapter<String> {

	private int selectedId = 0;
	
	public PhotoFormatAdaper(Context context, List<String> list) {
		super(context, list);
	}

	
	
	
	@Override
	protected View bindView(int position, View convertView, ViewGroup parent) {
		String string = mList.get(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_settings_option, null);
		TextView tv_resolution_option = (TextView) view.findViewById(R.id.tv_settings_option);
		ImageView iv_settings_selected = (ImageView) view.findViewById(R.id.iv_settings_selected);
		tv_resolution_option.setText(string);
		if (selectedId == position) {
			iv_settings_selected.setVisibility(View.VISIBLE);
		} else {
			iv_settings_selected.setVisibility(View.GONE);
		}
		return view;
	}

	
	public void setSelectedId(int selectedId) {
		this.selectedId = selectedId;
	}
	

}
