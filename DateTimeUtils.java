package com.effect.tdb.bs.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtils {

    public static final String pattern = "dd/MM/yyyy HH:mm:ss";

    public static final int GIAY = 1;
    public static final int PHUT = GIAY * 60;
    public static final int GIO = PHUT * 60;
    public static final int NGAY = GIO * 24;
    public static final int TUAN = NGAY * 7;
    public static final int THANG = NGAY * 30;

    public static long timeRangeInDay(Date start, Date end) {
        return (end.getTime() - start.getTime()) / 1000 / NGAY;
    }

    public static Date daysAgo(int days) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.DATE, -days);
        return gc.getTime();
    }

    public static String getTimeFormat(Date date) {
        if (date == null) {
            date = new Date(0);
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getTimeFormat(String format, Date date) {
        if (date == null) {
            date = new Date(0);
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date getDate(String dateStr) throws ParseException {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    public static Date getDate(String format, String dateStr) throws ParseException {
        return new SimpleDateFormat(format).parse(dateStr);
    }

    public static Date datePlus(Date date, long numOfDay) {
        return new Date(date.getTime() + numOfDay * 24L * 60L * 60L * 1000L);
    }

    public static Date getEndDayTime(Date date) {
        String pattens = "dd/MM/yyyy";
        String endDayTime = new SimpleDateFormat(pattens).format(date) + " 23:59:59";
        try {
            return getDate(endDayTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
