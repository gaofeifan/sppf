package com.linkmoretech.account.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description:
 * @date: 15:12 2019-06-27
 */
@Getter
public enum  UserSourceEnum {

    IOS(1, "IOS"),
    ANDROID(2, "andorod"),
    WE_CHAR(3, "微信")
    ;

    private Integer code;

    private String message;

    UserSourceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static UserSourceEnum getStatus(Integer code) {

        if (code.equals(IOS.getCode())) {
            return IOS;
        }
        if (code.equals(ANDROID.getCode())) {
            return ANDROID;
        }
        if (code.equals(WE_CHAR.getCode())) {
            return WE_CHAR;
        }
        return null;
    }
}
