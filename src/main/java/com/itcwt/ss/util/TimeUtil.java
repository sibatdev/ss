package com.itcwt.ss.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 15:20
 */
public class TimeUtil {

    public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
    public static final String PATTERN_yyyyMMddHHmm = "yyyyMMddHHmm";
    public static final String PATTERN_MMddHHmm = "MMddHHmm";

    public static String parseDateToString(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date parseStringToDate(String date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date _date = null;
        try {
            _date = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return _date;
    }



}
