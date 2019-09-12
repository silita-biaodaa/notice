package com.silita.notice.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhushuai on 2019/9/6.
 */
public class DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 获取当前日期
     *
     * @param patten 日期格式(默认：yyyy-MM-dd)
     * @return
     */
    public static String dateToStr(Date date, String patten) {
        if (StringUtils.isEmpty(patten)) {
            patten = "yyyy-MM-dd";
        }
        SimpleDateFormat sbf = new SimpleDateFormat(patten);
        if (null == date) {
            date = new Date();
        }
        return sbf.format(date);
    }

    /**
     * 字符串日期转日期格式
     *
     * @param strDate 字符串日期（默认格式:yyyy-MM-dd）
     * @param patten  格式
     * @return
     */
    public static Date strToDate(String strDate, String patten) {
        if (StringUtils.isEmpty(patten)) {
            patten = "yyyy-MM-dd";
        }
        SimpleDateFormat sbf = new SimpleDateFormat(patten);
        try {
            return sbf.parse(strDate);
        } catch (ParseException e) {
            logger.error("日期转换失败！", e);
            return null;
        }
    }

    /**
     * 字符串日期转日期格式
     *
     * @param dateL 字符串日期（默认格式:yyyy-MM-dd）
     * @param patten  格式
     * @return
     */
    public static String longToStr(long dateL, String patten) {
        if (StringUtils.isEmpty(patten)) {
            patten = "yyyy-MM-dd";
        }
        SimpleDateFormat sbf = new SimpleDateFormat(patten);
        try {
            Date date = new Date(dateL);
            return sbf.format(date);
        } catch (Exception e) {
            logger.error("日期转换失败！", e);
            return null;
        }
    }

    /**
     * 获取指定日期的前？天
     *
     * @param date 日期(格式:yyyy-MM-dd)
     * @param days 天数
     * @return
     */
    public static String beforeDate(String date, int days) {
        SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateN = strToDate(date, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateN);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - days);
        return sbf.format(calendar.getTime());
    }

    /**
     * 获取指定日期的前？时
     *
     * @param date 日期(格式:yyyy-MM-dd HH:mm:dd)
     * @param hour 天数
     * @return
     */
    public static Date beforeDateHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(longToStr(new Date().getTime(), "yyyy-MM-dd HH:mm:ss"));
    }
}
