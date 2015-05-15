package com.yuneec.android.flyingexpert.manager;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.settings.AppConfig;

import android.content.Context;
import android.widget.Toast;



/**
 * Manage show content
 * @author yongdaimi
 * @remark 
 * @date 2014-10-14 上午08:47:29
 * @company Copyright ©PaoPao.Inc. All Rights Reserved.
 */
public class ViewManager {

	

	
	public static void showSimpleProgressDialog(ProgressDialog progressDialog, String msg) {
		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog.show();
			progressDialog.setMessage(msg);
		}
	}
	
	public static void showSimpleProgressDialog(ProgressDialog progressDialog, int resId) {
		if (progressDialog != null && !progressDialog.isShowing()) {
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.show();
			progressDialog.setMessage(resId);
		}
	}
	
	
	
	

	public static void closeProgressDialog(ProgressDialog progressDialog) {
		if (progressDialog !=null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
	
	

	public static void showNoNetWorkToast(Context context) {
		showToast(context,R.string.is_error_network);
	}
	
	

	public static void showServerErrorToast(Context context) {
		showToast(context,R.string.is_error_500);
	}
	
	
	
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 * test
	 * @param context
	 * @param msg
	 */
	public static void showTestToast(Context context, String msg) {
		if (AppConfig.developerMode) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}
	
	
	
}
