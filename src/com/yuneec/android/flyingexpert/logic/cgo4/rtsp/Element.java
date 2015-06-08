package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

import org.xmlpull.v1.XmlSerializer;

/**
 * *******************************************************************************
 * Element
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 8, 2015 9:34:33 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public abstract class Element {

	
	public abstract void serializer(XmlSerializer serializer);
	
	public abstract String getTransactiontype();
	
	
}
