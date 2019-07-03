package com.linkmoretech.versatile.sms.template;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: alec
 * Description: 通知类短信模版
 * 定义模版编码，和对应的模版内容。
 * 拼装短信模版，解析模版内容
 * @date: 13:34 2019-06-21
 */
@Data
public abstract class SmsTemp implements Serializable {


    private String code;


    public SmsTemp (String code) {
        this.code = code;
    }


    /**
     * 解析通知类短信模版
     * */

    public abstract String getNotifyMessage();

    public abstract String getValidateMessage();

}
