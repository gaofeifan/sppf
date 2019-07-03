package com.linkmoretech.versatile.sms.client;

import org.springframework.stereotype.Component;


/**
 * @Author: alec
 * Description: 封装久佳发送验证码客户端
 * @date: 17:51 2019-06-21
 */
@Component(value = "jiuClient")
public class JiuClient extends SmsSendClient {


    @Override
    public void sendNotifyMessage(String mobile, String code) {

    }

    @Override
    public void sendValidateMessage(String mobile, String code, String value) {

    }
}
