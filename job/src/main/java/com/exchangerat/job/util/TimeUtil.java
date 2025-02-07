package com.exchangerat.job.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String convertToFullFormat(String dateStr) throws ParseException {
        Date date = inputDateFormat.parse(dateStr);
        return outputDateFormat.format(date);
    }

    public static String convertToShortFormat(String dateStr) throws ParseException {
        Date date = outputDateFormat.parse(dateStr);
        return inputDateFormat.format(date);
    }
}