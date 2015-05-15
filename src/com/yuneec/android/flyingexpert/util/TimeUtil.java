package com.yuneec.android.flyingexpert.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;

/**
 * Time format util
 * @author yongdaimi
 * @remark
 * @date 2014-10-21 下午03:50:50
 * @company Copyright ©PaoPao.Inc. All Rights Reserved.
 */
public class TimeUtil {

	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "hh:mm";
	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd hh:mm";
	public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日 hh:mm";
	public final static String FORMAT_MONTH_DAY = "MM月dd日";
	private static SimpleDateFormat sdf = new SimpleDateFormat();
	private static final int YEAR = 365 * 24 * 60 * 60;// 年
	private static final int MONTH = 30 * 24 * 60 * 60;// 月
	private static final int DAY = 24 * 60 * 60;// 天
	private static final int HOUR = 60 * 60;// 小时
	private static final int MINUTE = 60;// 分钟

	/**
	 * 根据时间戳获取描述性时间，如3分钟前，1天前
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @return 时间字符串
	 */
	public static String getDescriptionTimeFromTimestamp(long timestamp) {
		//long currentTime = System.currentTimeMillis();
		//long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		//System.out.println("timeGap: " + timeGap);
		String timeStr = null;
		if (timestamp > YEAR) {
			timeStr = timestamp / YEAR + "年前";
		} else if (timestamp > MONTH) {
			timeStr = timestamp / MONTH + "个月前";
		} else if (timestamp > DAY) {// 1天以上
			timeStr = timestamp / DAY + "天前";
		} else if (timestamp > HOUR) {// 1小时-24小时
			timeStr = timestamp / HOUR + "小时前";
		} else if (timestamp > MINUTE) {// 1分钟-59分钟
			timeStr = timestamp / MINUTE + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	/**
	 * 根据时间戳获取指定格式的时间，如2011-11-30 08:40
	 * 
	 * @param timestamp
	 *            时间戳 单位为毫秒
	 * @param format
	 *            指定格式 如果为null或空串则使用默认格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getFormatTimeFromTimestamp(long timestamp,
			String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE);
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			int year = Integer.valueOf(sdf.format(new Date(timestamp))
					.substring(0, 4));
			System.out.println("currentYear: " + currentYear);
			System.out.println("year: " + year);
			if (currentYear == year) {// 如果为今年则不显示年份
				sdf.applyPattern(FORMAT_MONTH_DAY_TIME);
			} else {
				sdf.applyPattern(FORMAT_DATE_TIME);
			}
		} else {
			sdf.applyPattern(format);
		}
		Date date = new Date(timestamp);
		return sdf.format(date);
	}

	/**
	 * 根据时间戳获取时间字符串，并根据指定的时间分割数partionSeconds来自动判断返回描述性时间还是指定格式的时间
	 * 
	 * @param timestamp
	 *            时间戳 单位是毫秒
	 * @param partionSeconds
	 *            时间分割线，当现在时间与指定的时间戳的秒数差大于这个分割线时则返回指定格式时间，否则返回描述性时间
	 * @param format
	 * @return
	 */
	public static String getMixTimeFromTimestamp(long timestamp,
			long partionSeconds, String format) {
		long currentTime = System.currentTimeMillis();
		long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
		if (timeGap <= partionSeconds) {
			return getDescriptionTimeFromTimestamp(timestamp);
		} else {
			return getFormatTimeFromTimestamp(timestamp, format);
		}
	}

	
	
	/**
	 * 获取指定的日期时间格式
	 * @return xx月xx日
	 */
	public static String getMonthAndDayFormat(String dateStr) {
		if (!dateStr.equals("")) {
			DateFormat format  = new SimpleDateFormat(FORMAT_MONTH_DAY);
			try {
				Date date = format.parse(dateStr);
				return date.toString();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
	
	/**
	 * 获取当前日期的指定格式的字符串
	 * 
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getCurrentTime(String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		return sdf.format(new Date());
	}

	/**
	 * 将日期字符串以指定格式转换为Date
	 * 
	 * @param time
	 *            日期字符串
	 * @param format
	 *            指定的日期格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static Date getTimeFromString(String timeStr, String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		try {
			return sdf.parse(timeStr);
		} catch (ParseException e) {
			return new Date();
		}
	}

	/**
	 * 将Date以指定格式转换为日期时间字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
	 * @return
	 */
	public static String getStringFromTime(Date time, String format) {
		if (format == null || format.trim().equals("")) {
			sdf.applyPattern(FORMAT_DATE_TIME);
		} else {
			sdf.applyPattern(format);
		}
		return sdf.format(time);
	}

	public static void main(String[] args) {
		System.out.println(getDescriptionTimeFromTimestamp(170983));
	}
	
	
	/**
	 * 时间戳处理
	 * 
	 * */
	public static String getDateTime(String dataString) {
		Date date = Util.stringToDate2(dataString);
		Date current = Util.getCurrentDate();
		String time = "";
		if (date.getYear() == current.getYear()
				&& date.getMonth() == current.getMonth()) {
			int d = date.getDate() - current.getDate();
			switch (d) {
			case 0:
				time += "今天";
				break;
			case -1:
				time += "昨天";
				break;
			default:
				time += (date.getMonth() + 1)
						+ "月" + date.getDate()
						+ "日";
				break;
			}
		} else if (date.getYear() == current.getYear()
				&& date.getMonth() != current.getMonth()) {
			time += date.getMonth() + 1 + "月"
					+ date.getDate() +  "日";
		} else if (date.getYear() != current.getYear()) {
			time += date.getYear() + 1900 + "年"
					+ (date.getMonth() + 1)
					+ "月" + date.getDate()
					+ "日";
		}
		return time;
	}
	
	public static String getDateTime2(String dataString) {
		if (dataString != null && !TextUtils.isEmpty(dataString)) {
			Date date = Util.stringToDate2(dataString);
			Date current = Util.getCurrentDate();
			String time = "";
			if (date.getYear() == current.getYear()
					&& date.getMonth() == current.getMonth()) {
				int d = date.getDate() - current.getDate();
				switch (d) {
				case 0:
					time += "今天"
							+ mdNumber(date.getHours()) + ":"
							+ mdNumber(date.getMinutes());
					break;
				case -1:
					time += "昨天"
							+ mdNumber(date.getHours()) + ":"
							+ mdNumber(date.getMinutes());
					break;
				default:
					time += (date.getMonth() + 1)
							+ "月"
							+ date.getDate() + "日"
							+ mdNumber(date.getHours()) + ":"
							+ mdNumber(date.getMinutes());
					break;
				}
			} else if (date.getYear() == current.getYear()
					&& date.getMonth() != current.getMonth()) {
				time += date.getMonth() + 1
						+ "月" + date.getDate()
						+ "日"
						+ mdNumber(date.getHours()) + ":"
						+ mdNumber(date.getMinutes());
			} else if (date.getYear() != current.getYear()) {
				time += date.getYear() + 1900
						+ "年"
						+ (date.getMonth() + 1)
						+ "月" + date.getDate()
						+ "日"
						+ mdNumber(date.getHours()) + ":"
						+ mdNumber(date.getMinutes());
			}
			return time;
		} else {
			return "";
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

	
	public static String dateToString1(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		String dateString = formatter.format(date);
		return dateString;
	}
	
	
	/** yyyy/MM/dd HH:mm:ss */
	public static Date stringToDate2(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
}