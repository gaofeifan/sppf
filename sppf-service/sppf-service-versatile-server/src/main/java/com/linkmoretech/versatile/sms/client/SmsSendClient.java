package com.linkmoretech.versatile.sms.client;


import com.linkmoretech.versatile.sms.enums.SmsTempEnum;
import com.linkmoretech.versatile.sms.template.SmsTemp;

/**
 * @Author: alec
 * Description: 发送短信客户端
 * @date: 16:30 2019-07-02
 */
public abstract class SmsSendClient {

    /**
     * 定义发送消息方法
     * @param mobile 发送目标手机号
     * @param smsTemp 模版编号
     * */
    public abstract void sendMessage(SmsTemp smsTemp, String mobile);

    /**
     * 定义发送消息方法
     * @param mobile 发送目标手机号
     * @param smsTempEnum 模版编号
     * */
    public abstract void sendMessage(SmsTempEnum smsTempEnum, String mobile);
}
