package com.alibaba.repeater.console.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期相关工具类
 */
public class DateUtil {
    /**
     * 将字符串转日期
     *
     * @param strDate
     * @return
     */
    public static Date strYMDToDate(String strDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 计算两个时间差（天）
     * @param start
     * @param end
     * @return
     */
    public static long diffDate(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        return diff / (1000 * 3600 * 24);
    }
    /**
     * 计算两个时间差（天）
     * @param start
     * @param end
     * @return
     */
    public static long diffStrDate(String start, String end) {
        return diffDate(strYMDToDate(start),strYMDToDate(end));
    }

    /**
     * 获取当前时间所在自然周的起止日期
     *
     * @return
     */
    public static Map<String, String> weekBeginningAndEnding() {
        Map<String, String> map = new HashMap<>();
        //获取当前自然周中每天的日期集合
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);		//这里设置一周开始时间是星期一
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        String beginTime = format.format(c.getTime());      //获取当前自然周的起始时间
        map.put("begin", beginTime+" 00:00:00");
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        String endTime = format.format(c.getTime());        //当前自然周的截止时间
        map.put("end", endTime+" 23:59:59");
        return map;
    }
    /**
     * 获取开始和结束之间的每一天
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param type  返回列表中的时间格式
     * @return 返回日期字符串列表
     */
    public static List<String> weekDays(Date beginTime, Date endTime,String type) {
        DateFormat format=new SimpleDateFormat(type);
        //设置开始时间
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(beginTime);

        //设置结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endTime);

        //返回的日期集合
        List<String> dateList = new ArrayList<String>();
        //每次循环给calStart日期加一天，直到calBegin.getTime()时间等于dEnd
        dateList.add(format.format(calStart.getTime()));
        while (endTime.after(calStart.getTime())) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(format.format(calStart.getTime()));
        }
        return dateList;
    }
}
