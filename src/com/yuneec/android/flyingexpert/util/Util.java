package com.yuneec.android.flyingexpert.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import junit.framework.Assert;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Util {

	public static void resizeView(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if (listAdapter == null) {

			// pre-condition

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItem = listAdapter.getView(i, null, listView);

			listItem.measure(0, 0);

			totalHeight += (listItem.getMeasuredHeight() + 15);

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);

	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle, int quality) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.JPEG, quality, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static String getIp(Context context) {
		WifiManager wifimanage = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (!wifimanage.isWifiEnabled()) {
			wifimanage.setWifiEnabled(true);
		}
		WifiInfo wifiinfo = wifimanage.getConnectionInfo();
		String ip = intToIp(wifiinfo.getIpAddress());
		return ip;

	}

	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}

	public static Date stringToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;

	}

	public static Date stringToDate2(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;

	}

	public static Date stringToDate3(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static Date stringToDate4(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String dateToString1(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String getStringDate2() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static String getStringDate3() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	
	public static String getStringDate4() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime)+new Random().nextInt(999999);
		return dateString;
	}
	

	public static String getStringDate5() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	
	public static String getStringDate6() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	
	public static Date getCurrentDate() {
		Date currentTime = new Date(System.currentTimeMillis());
		return currentTime;
	}

	static final char SBC_CHAR_START = 65281; // 全角！
	static final char SBC_CHAR_END = 65374; // 全角～
	static final int CONVERT_STEP = 65248; // 全角半角转换间隔
	static final char SBC_SPACE = 12288; // 全角空格 12288
	static final char DBC_SPACE = ' '; // 半角空格

	
	/**
	 * 全角转半角
	 * @param src
	 * @return
	 */
	public static String quanJiaoToBanjiao(String src) {
		if (src == null) {
			return src;
		}

		StringBuilder buf = new StringBuilder(1);
		char[] ca = src.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
				buf.append((char) (ca[i] - CONVERT_STEP));
			} else if (ca[i] == SBC_SPACE) { // 如果是全角空格
				buf.append(DBC_SPACE);
			} else { // 不处理全角空格，全角！到全角～区间外的字符
				buf.append(ca[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * float大数据转String
	 * */
	public static String getStringFromBigNumber(double data) {
		String val = null;
		try {
			BigDecimal bd = new BigDecimal(Double.toString(data));
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			val = bd.toString();
			bd = null;
		} catch (Exception e) {
			// TODO: handle exception
			val = "-1";
		}
		return val;
	}

	/**
	 * String大数据转String
	 * */
	public static String getStringFromBigString(String data) {
		String val = null;
		try {
			BigDecimal bd = new BigDecimal(data);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
			val = bd.toString();
			bd = null;
		} catch (Exception e) {
			// TODO: handle exception
			val = "-1";
		}
		return val;
	}

	/**
	 * 精度保留
	 * 
	 * @param value
	 *            :double
	 * */
	public static String round(double value, int scale) {
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		String d = bd.toString();
		bd = null;
		return d;
	}

	/**
	 * 精度保留
	 * 
	 * @param value
	 *            :String
	 * */
	public static String round(String value, int scale) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		String d = bd.toString();
		bd = null;
		return d;
	}

	/**
	 * 金额安全相加
	 * */
	public static String addBigDecimal(String data1, String data2) {
		BigDecimal bd1 = new BigDecimal(data1);
		BigDecimal bd2 = new BigDecimal(data2);
		BigDecimal resultDecimal = bd1.add(bd2);
		resultDecimal = resultDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		String addResult = resultDecimal.toString();
		resultDecimal = null;
		bd1 = null;
		bd2 = null;
		return addResult;
	}

	/**
	 * 金额安全相乘
	 * */
	public static String multiplyBigDecimal(String data1, String data2) {
		BigDecimal bd1 = new BigDecimal(data1);
		BigDecimal bd2 = new BigDecimal(data2);
		BigDecimal resultDecimal = bd1.multiply(bd2);
		resultDecimal = resultDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		String addResult = resultDecimal.toString();
		resultDecimal = null;
		bd1 = null;
		bd2 = null;
		return addResult;
	}

	/**
	 * 金额安全相除
	 * */
	public static String divideBigDecimal(String data1, String data2) {
		BigDecimal bd1 = new BigDecimal(data1);
		BigDecimal bd2 = new BigDecimal(data2);
		try {
			BigDecimal resultDecimal = bd1.divide(bd2, 4,
					BigDecimal.ROUND_HALF_UP);
			resultDecimal = resultDecimal.setScale(4, BigDecimal.ROUND_HALF_UP);
			String addResult = resultDecimal.toString();
			resultDecimal = null;
			bd1 = null;
			bd2 = null;
			return addResult;
		} catch (Exception e) {
			// TODO: handle exception
			return "0.00";
		}

	}

	// public static boolean isTelePhone(String phone) { // 判断电话号码格式
	// Pattern p = Pattern
	// .compile("^((1[0-9]))\\d{9}|(0\\d{3}\\d{8})|(0\\d{2}\\d{8})|(0\\d{3}\\d{7})|(\\(0\\d{3}\\)\\d{8})|(\\(0\\d{2}\\)\\d{8})|(\\(0\\d{3}\\)\\d{7})|(0\\d{3}-\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
	// Matcher m = p.matcher(phone);
	// if (m.matches()) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	/**
	 * 删除文件
	 * 
	 * */
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File[] delFiles = file.listFiles();
				for (int i = 0; i < delFiles.length; i++) {
					deleteFile(delFiles[i]);
				}
			}
		}
	}

	public static String mdNumber(int num) {
		String res = null;
		if (num < 10) {
			res = "0" + String.valueOf(num);
		} else {
			res = String.valueOf(num);
		}
		return res;
	}

private static final String TAG = "SDK_Sample.Util";
	
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static byte[] getHtmlByteArray(final String url) {
		 URL htmlUrl = null;     
		 InputStream inStream = null;     
		 try {         
			 htmlUrl = new URL(url);         
			 URLConnection connection = htmlUrl.openConnection();         
			 HttpURLConnection httpConnection = (HttpURLConnection)connection;         
			 int responseCode = httpConnection.getResponseCode();         
			 if(responseCode == HttpURLConnection.HTTP_OK){             
				 inStream = httpConnection.getInputStream();         
			  }     
			 } catch (MalformedURLException e) {               
				 e.printStackTrace();     
			 } catch (IOException e) {              
				e.printStackTrace();    
		  } 
		byte[] data = inputStreamToByte(inStream);

		return data;
	}
	
	public static byte[] inputStreamToByte(InputStream is) {
		try{
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			int ch;
			while ((ch = is.read()) != -1) {
				bytestream.write(ch);
			}
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static byte[] readFromFile(String fileName, int offset, int len) {
		if (fileName == null) {
			return null;
		}

		File file = new File(fileName);
		if (!file.exists()) {
			Log.i(TAG, "readFromFile: file not found");
			return null;
		}

		if (len == -1) {
			len = (int) file.length();
		}

		Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

		if(offset <0){
			Log.e(TAG, "readFromFile invalid offset:" + offset);
			return null;
		}
		if(len <=0 ){
			Log.e(TAG, "readFromFile invalid len:" + len);
			return null;
		}
		if(offset + len > (int) file.length()){
			Log.e(TAG, "readFromFile invalid file len:" + file.length());
			return null;
		}

		byte[] b = null;
		try {
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			b = new byte[len]; // 创建合适文件大小的数组
			in.seek(offset);
			in.readFully(b);
			in.close();

		} catch (Exception e) {
			Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
			e.printStackTrace();
		}
		return b;
	}
	
	private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
	public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
		Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);

		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

			Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}

			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}

			options.inJustDecodeBounds = false;

			Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				Log.e(TAG, "bitmap decode failed");
				return null;
			}

			Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
			if (scale != null) {
				bm.recycle();
				bm = scale;
			}

			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}

				bm.recycle();
				bm = cropped;
				Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
			}
			return bm;

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
			options = null;
		}

		return null;
	}
	
}
