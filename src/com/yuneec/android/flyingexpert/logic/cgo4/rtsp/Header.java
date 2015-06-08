package com.yuneec.android.flyingexpert.logic.cgo4.rtsp;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.xmlpull.v1.XmlSerializer;

import com.yuneec.android.flyingexpert.ConstantValue;

/**
 * *******************************************************************************
 * Header
 * @Author yongdaimi
 * @Remark 
 * @Date Jun 8, 2015 9:21:30 AM
 * @Company Copyright (C) 2014-2015 Yuneec.Inc. All Rights Reserved.
 ********************************************************************************
 */
public class Header {

	
	private Leaf agenterid = new Leaf("agenterid", ConstantValue.AGENTERID);// TAG	id
	private Leaf source = new Leaf("source", ConstantValue.SOURCE);// TAG name
	private Leaf compress = new Leaf("compress", ConstantValue.COMPRESS);// TAG compress
	
	
	private Leaf messengerid = new Leaf("messengerid");// Message id
	private Leaf timestamp = new Leaf("timestamp");// Message timestamp
	private Leaf digest = new Leaf("digest");// MD5

	
	private Leaf transactiontype = new Leaf("transactiontype");// Request tag
	private Leaf username = new Leaf("username");// UserName
	
	
	public Leaf getTransactiontype() {
		return transactiontype;
	}

	public Leaf getUsername() {
		return username;
	}
	
	
	public Leaf getTimestamp() {
		return timestamp;
	}

	public Leaf getDigest() {
		return digest;
	}
	
	
	public void serializer(XmlSerializer serializer) {
		
		// format date
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = dateFormat.format(new Date());
		
		// Random
		Random random = new Random();
		int num = random.nextInt(999999) + 1;// 0~999998 1~999999 000001
		DecimalFormat decimalFormat = new DecimalFormat("000000");

		String numFormat = decimalFormat.format(num);
		
		// set id
		messengerid.setTagValue(time + numFormat);
		// setTimeStamp
		timestamp.setTagValue(time);
		
		/*String md5Info = time + ConstantValue.PASSWORD + body;
		String md5Hex = DigestUtils.md5Hex(md5Info);
		digest.setTagValue(md5Hex);*/
		
		try {
			serializer.startTag(null, "header");
			/*agenterid.serializer(serializer);
			source.serializer(serializer);
			compress.serializer(serializer);

			messengerid.serializer(serializer);
			timestamp.serializer(serializer);
			digest.serializer(serializer);

			transactiontype.serializer(serializer);
			username.serializer(serializer);*/
			serializer.endTag(null, "header");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	
}
