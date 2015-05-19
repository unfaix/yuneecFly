package com.yuneec.android.flyingexpert.adapter;

import com.yuneec.android.flyingexpert.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * *******************************************************************************
 * Share Adapter
 * @Author yongdaimi
 * @Remark 
 * @Date May 18, 2015 2:17:32 PM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class ShareAppAdapter extends BaseAdapter {

	private Context context;
	
	public ShareAppAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_share, parent, false);
			holder = new ViewHolder();
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		switch (position) {
		case 0:
			holder.iv_icon.setImageResource(R.drawable.share_qq);
			holder.tv_name.setText(R.string.share_qq);
			break;
		case 1:
			holder.iv_icon.setImageResource(R.drawable.share_qzone);
			holder.tv_name.setText(R.string.share_qzone);
			break;
		case 2:
			holder.iv_icon.setImageResource(R.drawable.share_wexin);
			holder.tv_name.setText(R.string.share_weixin);
			break;
		case 3:
			holder.iv_icon.setImageResource(R.drawable.share_weixin_friend);
			holder.tv_name.setText(R.string.share_moments);
			break;
		case 4:
			holder.iv_icon.setImageResource(R.drawable.share_sina);
			holder.tv_name.setText(R.string.share_sina);
			break;
		case 5:
			holder.iv_icon.setImageResource(R.drawable.share_message);
			holder.tv_name.setText(R.string.share_message);
			break;
		default:
			break;
		}
		return convertView;
	}

	
	static class ViewHolder {
		ImageView iv_icon;
		TextView tv_name;
	}
	
	
	

}
