package com.linkmoretech.versatile.sms.client;

import com.linkmoretech.versatile.sms.template.SmsTemp;
import lombok.Getter;

/**
 * @Author: alec
 * Description: 发送短信客户端
 * @date: 16:30 2019-07-02
 */
@Getter
public abstract class SmsSendClient {


    private String code;

    private String notifyMessage;

    private String validateMessage;

    public void build (SmsTemp smsTemplate) {

        this.code = smsTemplate.getCode();

        this.notifyMessage = smsTemplate.getNotifyMessage();

        this.validateMessage = smsTemplate.getValidateMessage();
    }

    /**
     * 定义发送消息方法
     * @param mobile 发送目标手机号
     * */
    public abstract void sendNotifyMessage(String mobile, String code);

    /**
     * 定义发送消息方法
     * @param mobile 发送目标手机号
     * */
    public abstract void sendValidateMessage(String mobile, String code, String value);
}
