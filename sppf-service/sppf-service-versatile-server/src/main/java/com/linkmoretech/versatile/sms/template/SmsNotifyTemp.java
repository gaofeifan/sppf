package com.linkmoretech.versatile.sms.template;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: alec
 * Description: 通知类短信模版
 * 拼装短信模版，解析模版内容
 * @date: 13:34 2019-06-21
 */
@Data
public class SmsNotifyTemp implements Serializable {

    private String code;

    private String id;

    private String description;

    private String message;


}
