package com.yuneec.android.flyingexpert.logic.http.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yuneec.android.flyingexpert.core.Key;
import com.yuneec.android.flyingexpert.logic.RequestKey;
import com.yuneec.android.flyingexpert.logic.http.HttpRequest;
import com.yuneec.android.flyingexpert.logic.http.HttpRequestKey;
import com.yuneec.android.flyingexpert.util.AES;
import com.yuneec.android.flyingexpert.util.LogX;
import com.yuneec.android.flyingexpert.util.SystemUtil;


import android.content.Context;


/**
 * ****************************************************************
 * Check New Version
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class CheckUpdateRequest extends HttpRequest {
	
	/** download url */
	private String mUpdateUrl;
	/** update desc */
	private String desc;
	/** is force update */
	private boolean isneedUpdate = false;
	/** is force update(0:yes,1no) */
	private int isUpdate;
	/** new version */
	private String newVersion;
	private Context mContext;
	
	

	@Override
	public void createRequestBody() throws JSONException {
		JSONObject rootObject = new JSONObject();
		rootObject.put(RequestKey.REQUEST_TYPE, "checkAppVesion");
		rootObject.put(HttpRequestKey.CHECKUPDATE_VERSION, SystemUtil.getAppVersionName(mContext));
		rootObject.put("platform", "Android");
		rootObject.put("appType", "1");
		try {
			requestParas = AES.encrypt(rootObject.toString(),new Key().getPrivateKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogX.i("test_send", rootObject.toString());
	}

	
	
	@Override
	public void parseResponse() throws JSONException {
		JSONObject rootObject = new JSONObject(responseContent);
		resultCode = Integer.valueOf(rootObject.getString(RequestKey.RESULT_CODE));
		if (resultCode == 0) {
			isneedUpdate = rootObject.getBoolean("Status");
			JSONArray jsonArray = rootObject.getJSONArray("ReturnValue");
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					try {
						isUpdate = Integer.valueOf(object.getString("isUpdate"));
					} catch (Exception e) {
						// TODO: handle exception
						isUpdate = 1;
					}
					mUpdateUrl = object.getString("url");
					desc = object.getString("updateContent");
					newVersion = object.getString("version");
				}
			}
		}
	}

	
	@Override
	public String getUrl() {
		return BASE_URL;
	}

	/** download url */
	public String getUpdateUrl() {
		return mUpdateUrl;
	}

	/** update desc */
	public String getDesc() {
		return desc;
	}

	/** is need update */
	public boolean isNeedUpdate() {
		return isneedUpdate;
	}

	/** is force update */
	public boolean isForceUpdate() {
		if (isUpdate == 0) {
			return true;
		} else {
			return false;
		}
	}

	
	/** get new version */
	public String newVersion() {
		return newVersion;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
	
	
}
