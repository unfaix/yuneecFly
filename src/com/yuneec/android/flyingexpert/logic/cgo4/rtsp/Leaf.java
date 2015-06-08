package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

import org.xmlpull.v1.XmlSerializer;

import com.yuneec.android.flyingexpert.util.StringUtils;


/**
 * *******************************************************************************
 * Leaf
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 8, 2015 9:11:45 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class Leaf {

	
	private String tagName;
	private 	String tagValue;
	
	
	
	public Leaf(String tagName, String tagValue) {
		super();
		this.tagName = tagName;
		this.tagValue = tagValue;
	}



	public Leaf(String tagName) {
		super();
		this.tagName = tagName;
	}



	public String getTagValue() {
		return tagValue;
	}


	public void setTagValue(String tagValue) {
		this.tagValue = tagValue;
	}


	public String getTagName() {
		return tagName;
	}
	
	
	
	public void serializer(XmlSerializer serializer) {
		
		try {
			serializer.startTag(null, tagName);
			if (StringUtils.isBlank(tagValue)) {
				tagValue = "";
			}
			serializer.text(tagValue);
			serializer.endTag(null, tagName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
}
