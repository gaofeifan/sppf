package com.linkmoretech.versatile.sms.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description: 短信发送码 枚举类型
 * @date: 20:17 2019-07-02
 */
@Getter
public enum  SmsTempEnum {

    REGISTER_SUCCESS ("SMS_149391090"),
    REGISTER_VALIDATE("SMS_60145031"),
    FIND_PASSWORD("SMS_59965017"),
    RESET_PASSWORD("SMS_60075062"),
    LOGIN_VALIDATE("SMS_52350243"),

    ;

    private String code;

    SmsTempEnum (String code) {
        this.code = code;
    }
}
