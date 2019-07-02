package com.ty.yjx.love.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/10
 */
public class DateUtil {

    /**
     * 年-月-日
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 年-月-日
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 年-月-日
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

    /**
     * 时间转字符串
     *
     * @param date 时间
     * @return dateStr 时间字符串
     */
    public static String dateToStr(Date date) {
        return dateToStr(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 时间转字符串
     *
     * @param date 时间
     * @return dateStr 时间字符串
     */
    public static String dateToStr(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串转时间
     *
     * @param dateStr 时间字符串
     * @return 时间
     * @throws ParseException 转化异常
     */
    public static Date strToDate(String dateStr) throws ParseException {
        return strToDate(dateStr, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 字符串转时间
     *
     * @param dateStr 时间字符串
     * @param pattern 格式
     * @return 时间
     * @throws ParseException 转化异常
     */
    public static Date strToDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 日期天数加减
     *
     * @param date 日期
     * @param day 天数 负数减 正数加
     * @return 新的日期
     */
    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
}
