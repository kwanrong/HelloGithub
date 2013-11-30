package com.loveplusplus.demo.print.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static String formatToString(Date time) {
		return formatToString(time, "yyyy-MM-hh HH:mm:ss");
	}

	public static String formatToString(Date time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern,Locale.getDefault());
		return format.format(new Date());
	}

}
