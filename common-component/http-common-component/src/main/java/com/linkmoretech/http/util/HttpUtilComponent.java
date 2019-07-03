package com.linkmoretech.http.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 14:45 2019-05-20
 */
@Component
@Slf4j
public class HttpUtilComponent {

    @Autowired
    OkHttpClient okHttpClient;

    /**
     * 发送http get请求
     * @param url 请求地址
     * @param params 请求参数
     * @return  返回请求响应内容
     * */

    public String sendHttpGetRequest(String url, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        String responseBody = "";
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    stringBuilder.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        log.info("发送请求url {} ", stringBuilder.toString());
        Request request = new Request.Builder().url(stringBuilder.toString()).build();
        return  execute(request);
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */

    public String sendHttpPostRequest(String url, Map<String, String> params) {
        FormBody.Builder   builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        log.info("发送请求url {} , 参数 {}", url , params);
        return execute(request);
    }

    public String sendHttpPostRequest(String url, JSONObject jsonObject) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toJSONString());
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        log.info("发送请求url {} , 参数 {}", url , jsonObject);
        return execute(request);
    }


    private String execute(Request request) {
        String responseString = "";
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            log.info("请求响应码 {}", response.code());
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            log.error("ok http3 get error >> ex =  { } ", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseString;
    }

}
