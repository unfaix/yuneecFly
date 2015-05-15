package com.yuneec.android.flyingexpert.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.yuneec.android.flyingexpert.base64.BASE64Decoder;
import com.yuneec.android.flyingexpert.base64.BASE64Encoder;



public class AES {
	
	public static String encrypt(String src, String key) {
		try {
			if (key == null) {
				System.out.print("Key is null");
				return null;
			}
			if (key.length() != 16) {
				System.out.print("Key length  not 16");
				return null;
			}
			byte[] raw = key.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(src.getBytes("utf-8"));

			return new BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			return null;
		}

	}

	public static String decrypt(String src, String key) {
		try {
			if (key == null) {
				System.out.print("Key is null");
				return null;
			}
			if (key.length() != 16) {
				System.out.print("Key length  not 16");
				return null;
			}
			byte[] raw = key.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(src);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
}