package org.office.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateUtil {

	private static final Pattern MILLIS = Pattern.compile("[0-9][0-9]*");

	public static String toText(Date date, String format) {
		DateFormat dateFormt = new SimpleDateFormat(format, Locale.ENGLISH);
		return dateFormt.format(date);
	}

	public static String toText(Number date, String format) {
		long time = date.longValue();
        if (time <= 0) {
            return null;
        }
        Date  value = new Date(time);
		DateFormat dateFormt = new SimpleDateFormat(format, Locale.ENGLISH);
		return dateFormt.format(value);
	}
	
	
	
	public static Date toDate(long date, String format) throws ParseException {
		if (date <= 0) {
            return null;
        }
        return new Date(date);

	}
	
	
	public static Date toDate(double date, String format) throws ParseException {
		long value=Double.valueOf(date).longValue();
		return toDate(value,format);

	}
	
	public static Date toDate(String date, String format) throws ParseException {

		if (MILLIS.matcher(date).matches()) {
			long time = Long.parseLong(date);
			if (time <= 0) {
				return null;
			}
			return new Date(time);
		}else{

			DateFormat dateFormt = new SimpleDateFormat(format, Locale.ENGLISH);
			return dateFormt.parse(date);
		}

	}

}
