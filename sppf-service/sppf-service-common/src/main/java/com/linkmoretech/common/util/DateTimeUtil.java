package com.linkmoretech.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间操作封装
 * @Author: alec
 * @Description:
 * @date: 下午2:48 2019/4/15
 */
public class DateTimeUtil {

    public static final String DAY_FORMAT = "yyyy-MM-dd";
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得当前时间的yyyy-MM-dd格式字符串
     * @return String
     */
    public static String getCurrentDate(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DAY_FORMAT);
        LocalDate localDate = LocalDate.now();
        String nowDate = localDate.format(df);
        return nowDate;
    }

    /**
     * 将localDateTime转换成特定格式字符
     * */
    public static String getLocalDateTime(String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.now();
        String nowDateTime = localDateTime.format(df);
        return nowDateTime;
    }
}
