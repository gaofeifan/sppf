package com.linkmoretech.versatile.sms.client;

import com.linkmoretech.versatile.sms.enums.SmsTempEnum;
import com.linkmoretech.versatile.sms.template.SmsTemp;
import org.springframework.stereotype.Component;


/**
 * @Author: alec
 * Description: 封装久佳发送验证码客户端
 * @date: 17:51 2019-06-21
 */
@Component(value = "jiuClient")
public class JiuClient extends SmsSendClient {


    @Override
    public void sendMessage(SmsTemp smsTemp, String mobile) {

    }

    @Override
    public void sendMessage(SmsTempEnum smsTempEnum, String mobile) {

    }
}
