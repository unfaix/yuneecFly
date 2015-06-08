package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * *******************************************************************************
 * Body
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 8, 2015 9:36:05 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class Body {

	
	private List<Element> elements = new ArrayList<Element>();
	private Oelement oelement = new Oelement();
	
	private String desBody; // DESbody
	private String bodyInfo; // body
	

	public String getBodyInfo() {
		return bodyInfo;
	}

	public void setBodyInfo(String bodyInfo) {
		this.bodyInfo = bodyInfo;
	}

	public String getDesBody() {
		return desBody;
	}

	public void setDesBody(String desBody) {
		this.desBody = desBody;
	}
	
	public List<Element> getElements() {
		return elements;
	}
	
	public Oelement getOelement() {
		return oelement;
	}
	
	public void setOelement(Oelement oelement) {
		this.oelement = oelement;
	}
	
	public String getBody() {
		XmlSerializer temp = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			temp.setOutput(writer);
			
			this.serializer(temp);
			// Document End
			temp.flush();
			return writer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	
	
	/*public String getDESBody() {
		String bodyInfo=getBody();
		
		String elemengsInfo = StringUtils.substringBetween(bodyInfo, "<body>", "</body>");
		DES des=new DES();
		
		String authcode = des.authcode(elemengsInfo,"DECODE",ConstantValue.DES_PASSWORD);
		
		return authcode;
	}*/
	
	
	public void serializer(XmlSerializer serializer) {
		
		try {
			serializer.startTag(null, "body");
			serializer.startTag(null, "elements");
			for (Element element : elements) {
				element.serializer(serializer);
			}
			serializer.endTag(null, "elements");
			serializer.endTag(null, "body");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
}
