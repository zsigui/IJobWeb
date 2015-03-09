package com.ijob.server.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 
 * @description 日期时间操作类
 * 
 * @author Jackie Zhuang
 * @date 2014年12月12日
 */
public class DateUtils {

	public static String getVal(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
		return sdf.format(getTime());
	}
	
	/**
	 * 获取起始到现在的时间戳(ms)
	 * @return
	 */
	public static long getTimeInMillis() {
		return Calendar.getInstance(Locale.CHINA).getTimeInMillis();
	}
	
	/**
	 * 获取Unix时间戳(ms)
	 * @return
	 */
	public static long getGMTUnixTimeInMillis() {
		return getTimeInMillis() - TimeZone.getDefault().getRawOffset();
	}
	
	/**
	 * 获取起始到现在的日期
	 * @return
	 */
	public static Date getTime() {
		return Calendar.getInstance(Locale.CHINA).getTime();
	}
}
