package com.linkmoretech.common.util;

/**
 * @Author: alec
 * Description: 字符转化工具
 * @date: 11:14 2019-06-26
 */
public class CharUtil {

    private static final long MIN_CHAR = 65280;
    private static final long MAX_CHAR = 65375;
    private static final long CHAR_LEN = 65248;
    private static final long LEN = 12288;
    private static final long SPACE = 32;
    /**
     * 将符号转化成英文
     * */
    public static String change(String sourceStr) {

        char[] chars = sourceStr.toCharArray();
        for (int i = 0; i< chars.length; i++) {

            if (chars[i] == LEN) {
                chars[i]=(char)SPACE;
                continue;
            }
            if (chars[i] > MIN_CHAR && chars[i] < MAX_CHAR)  {
                chars[i] = (char)(chars[i] - CHAR_LEN);
            }
        }
        return new String(chars);
    }
}
