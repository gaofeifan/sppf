package com.linkmoretech.versatile.sms.client;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.linkmoretech.versatile.sms.template.AliSmsTemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description: 阿里云客户端发送短信
 * @date: 18:08 2019-06-21
 */
@Component(value = "aliClient")
@Slf4j
public class AliClient extends SmsSendClient {

    @Autowired
    IAcsClient iAcsClient;

    @Autowired
    SendSmsRequest sendSmsRequest;

    @Override
    public void sendValidateMessage(String mobile, String code, String value) {

        AliSmsTemp aliSmsTemp = new AliSmsTemp(code, value);
        sendSmsRequest.setPhoneNumbers(mobile);
        sendSmsRequest.setTemplateCode(aliSmsTemp.getCode());
        sendSmsRequest.setTemplateParam(aliSmsTemp.getValidateMessage());
        execute();
    }

    @Override
    public void sendNotifyMessage(String mobile, String code) {
        AliSmsTemp aliSmsTemp = new AliSmsTemp(code);
        sendSmsRequest.setPhoneNumbers(mobile);
        sendSmsRequest.setTemplateCode(aliSmsTemp.getCode());
        sendSmsRequest.setTemplateParam(aliSmsTemp.getNotifyMessage());
        execute();
    }

    private void execute() {
        try {
            log.info("发送参数 {}", sendSmsRequest);
            SendSmsResponse acsResponse = iAcsClient.getAcsResponse(sendSmsRequest);
            log.info("消息发送成功 {} - {}" , acsResponse.getCode(), acsResponse.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
