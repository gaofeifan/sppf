package com.linkmoretech.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: alec
 * Description: md5加密工具
 * @date: 11:38 2019-05-21
 */
public class MdUtil {

    public static String encode(String value) {
        MessageDigest md5 = null;
        StringBuffer buf = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes("UTF-8"));
            byte bytes[] = md5.digest();
            int i;
            for(int offset = 0; offset < bytes.length; offset ++ ){
                i = bytes[offset];
                if(i < 0){
                    i += 256;
                }
                if(i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
