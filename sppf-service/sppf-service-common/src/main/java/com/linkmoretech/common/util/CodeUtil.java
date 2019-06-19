package com.linkmoretech.common.util;

import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @Author: alec
 * Description: 编码生成工具
 * @date: 20:34 2019-05-21
 */
public class CodeUtil {

    /**
     * 随机生成n位数
     * */
    public static String getRandomValue(int bound) {
        Random random = new Random();
        int ends = random.nextInt(bound);
        return String.format("%02d",ends);
    }

    /**
     * 获取系统时间戳后八位
     * */
    public static String timeStampAfter(int bound) {
        long timeStamp = System.currentTimeMillis();
        String timeValue = String.valueOf(timeStamp);
        return timeValue.substring(timeValue.length() - bound);
    }

    /**
     * 获取系统时间 MM SS
     * */
    public static String getOrderDate() {
        String format = "MMdd";
        return DateTimeUtil.getLocalDateTime(format);
    }

    /**
     * 字符串倒叙
     * */
    public static String getReverseValue(String userIds) {
        if (StringUtils.isEmpty(userIds)) {
            return null;
        }
        return new StringBuffer(userIds).reverse().toString();
    }
}
