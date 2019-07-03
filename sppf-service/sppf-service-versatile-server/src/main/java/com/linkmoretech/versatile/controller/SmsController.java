package com.linkmoretech.versatile.controller;

import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.versatile.sms.client.SmsSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 17:48 2019-07-02
 */
@RestController
@RequestMapping(value = "sms")
public class SmsController {

    @Autowired
    Map<String, SmsSendClient> sendClientMap;

    private static final String client = "aliClient";


   /* *//**
     * 发送短信登录验证码
     * *//*
    @GetMapping(value = "send/message")
    public void sendMessage(@RequestParam (value = "mobile") String mobile, @RequestParam(value = "code") String code) {
        String templateCode = "SMS_60145031";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        sendClientMap.get(client).sendMessage(mobile, templateCode  ,jsonObject.toJSONString());
    }*/


    /**
     * 发送通知类验证码
     * */
    @GetMapping(value = "send/notify")
    public void sendNotifyMessage(@RequestParam (value = "mobile") String mobile,
                                  @RequestParam (value = "code") String code) {
        sendClientMap.get(client).sendNotifyMessage(mobile, code);

    }
    /**
     * 发送验证码
     * */
    @IgnoreResponseAdvice
    @GetMapping(value = "send/validate")
    public void sendValidateMessage(@RequestParam (value = "mobile") String mobile,
                                    @RequestParam (value = "code") String code,
                                    @RequestParam (value = "value") String value) {

        sendClientMap.get(client).sendValidateMessage(mobile, code, value);
    }


}
