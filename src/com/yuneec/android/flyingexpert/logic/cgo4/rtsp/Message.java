package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * *******************************************************************************
 * Message
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 8, 2015 9:45:38 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class Message {

	private Header header = new Header();
	private Body body = new Body();
	
	
	public Header getHeader() {
		return header;
	}
	public Body getBody() {
		return body;
	}
	
	
	public void serializer(XmlSerializer serializer) {
		
		try {
			serializer.startTag(null, "message");
			serializer.attribute(null, "version", "1.0");
			Element element = body.getElements().get(0);
			// set Request TAG
			String transactiontype = element.getTransactiontype();
			header.getTransactiontype().setTagValue(transactiontype);
			header.serializer(serializer);
			serializer.startTag(null, "body");
			serializer.text(body.getBody());
			serializer.endTag(null, "body");
			serializer.endTag(null, "message");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	public String getXml(Element element) {
		
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("utf-8", null);
			body.getElements().add(element);
			this.serializer(serializer);
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "";
	}
	
	
	
	
	
	
	
}
