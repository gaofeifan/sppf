package com.linkmoretech.auth.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 16:31 2019-06-19
 */
@Slf4j
public class HttpRequestBodyUtil {

    public static Map<String, Object> getHttpBody(HttpServletRequest request) {
        Map returnMap = new HashMap<>();
        try {
            BufferedReader br = request.getReader();
            String str, wholeStr = "";
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            log.info(wholeStr);

            returnMap = JSONObject.parseObject(wholeStr, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }


}
