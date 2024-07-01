package com.group2.bookstoreproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    // Định dạng ngày giờ mặc định
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String HH_MM_SS_FORMAT = "HH:mm:ss";
    public static final String HH_MM_A_FORMAT = "HH:mm a";
    public static final String HH_MM_FORMAT = "HH:mm";
    public static final String YYYY_MM_DD_FORMAT = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_FORMAT = "yyyy-MM-dd HH:mm";
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();


    public static Date fromMilliseconds(long milliseconds) {
        return new Date(milliseconds);
    }


    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }


    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, DEFAULT_LOCALE);
        return sdf.format(date);
    }
    public static String formatDate(long timestamps, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, DEFAULT_LOCALE);
        return sdf.format(new Date(timestamps));
    }


    public static Date parseDate(String dateString) throws ParseException {
        return parseDate(dateString, DEFAULT_DATE_FORMAT);
    }


    public static Date parseDate(String dateString, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, DEFAULT_LOCALE);
        return sdf.parse(dateString);
    }


    public static long toMilliseconds(Date date) {
        return date.getTime();
    }
}
