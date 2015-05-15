package com.yuneec.android.flyingexpert.library;



import com.yuneec.android.flyingexpert.R;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


/**
 * AlertDialog
 * @author yongdaimi
 * @tip 
 * @date 2014-8-26 下午03:48:23
 */
public class AlertDialog {

	private  Context context;
	private android.app.AlertDialog ad;
	private TextView titleView;
	private TextView contentView ;
	private Button common_alert_dialog_cancel;
	private Button common_alert_dialog_confirm;
	
	

	public AlertDialog(Context context) {
		super();
		this.context = context;
		ad = new android.app.AlertDialog.Builder(context).create();
		ad.show();
		Window window = ad.getWindow();
		window.setContentView(R.layout.widget_alert_dialog);
		titleView = (TextView) window.findViewById(R.id.common_alert_dialog_title);
		contentView = (TextView) window.findViewById(R.id.common_alert_dialog_content);
		common_alert_dialog_cancel = (Button) window.findViewById(R.id.common_alert_dialog_cancel);
		common_alert_dialog_confirm = (Button) window.findViewById(R.id.common_alert_dialog_confirm);
	}
	
	
	public AlertDialog(Context context,int flag) {
		super();
		this.context = context;
		ad = new android.app.AlertDialog.Builder(context).create();
		ad.show();
		Window window = ad.getWindow();
		window.setContentView(R.layout.widget_alert_dialog_single);
		titleView = (TextView) window.findViewById(R.id.common_alert_dialog_title);
		contentView = (TextView) window.findViewById(R.id.common_alert_dialog_content);
		common_alert_dialog_confirm = (Button) window.findViewById(R.id.common_alert_dialog_confirm);
	}
	
	
	
	public void setTitle(int resId) {
		titleView.setText(resId);
	}
	
	public void setTitle(String title) {
		titleView.setText(title);
	}
	
	public void setMessage(int resId) {
		contentView.setText(resId);
	}
	
	public void setMessage(String message) {
		contentView.setText(message);
	}
	
	public void setPositiveButton(String text, View.OnClickListener listener) {
		common_alert_dialog_confirm.setText(text);
		common_alert_dialog_confirm.setOnClickListener(listener);
	}
	
	public void setNegativeButton(String text, View.OnClickListener listener) {
		common_alert_dialog_cancel.setText(text);
		common_alert_dialog_cancel.setOnClickListener(listener);
	}
	
	public void setCancelable(boolean flag) {
		ad.setCancelable(flag);
	}
	
	public void dismiss() {
		ad.dismiss();
	}
}
