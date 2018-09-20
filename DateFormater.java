/*
 * Copyright (C) 2016 Viettel IT2 . All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.effect.tdb.bs.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static Date fromString(String input) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = sdf.parse(input);
        return date;
    }

    public static String toString(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.format(date);
        } catch (Exception e) {
            BaseUtils.except(null, e);
        }
        return null;
    }
}
