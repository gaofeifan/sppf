package com.linkmoretech.versatile.sms.client;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description: 阿里云客户端发送短信
 * @date: 18:08 2019-06-21
 */
@Component
public class AliClient {

    @Autowired
    IAcsClient iAcsClient;

    @Autowired
    SendSmsRequest sendSmsRequest;

    public void sendMessage(String mobile, String message) {
        sendSmsRequest.setPhoneNumbers(mobile);
        sendSmsRequest.setTemplateCode("001");
        sendSmsRequest.setTemplateParam("");
        try {
            iAcsClient.getAcsResponse(sendSmsRequest);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


}
