package com.fch.buffetorder.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类
 *
 * @author zed
 * @date 2023/03/15
 */
public class DateUtil {
    /**
     * 比较日期是否为同一天
     */
    public static boolean eqDate(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d1).equals(sdf.format(d2));
    }

    /**
     * 计算2个日期相差的天数
     */
    public static int diffDays(Date start, Date end) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(start);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) //闰年
                {
                    timeDistance += 366;
                } else //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else //不同年
        {
            return day2 - day1;
        }
    }

    /**
     * 获取今日剩余的秒数
     */
    public static long lastSeconds() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = sdf.parse(sdf1.format(date) + " 23:59:59");
            return (date1.getTime() - new Date().getTime()) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取指定时间与当前时间相差的秒数
     */
    public static long lastSeconds(Date date) {
        return (date.getTime() - new Date().getTime()) / 1000;
    }


}
