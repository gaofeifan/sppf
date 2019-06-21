package com.linkmoretech.account.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description:
 * @date: 17:44 2019-06-20
 */
@Getter
public enum LoginTypeEnum {

    ACCOUNT(1, "帐号"),
    MOBILE(0, "手机号"),
    ;

    private Integer code;

    private String message;

    LoginTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static LoginTypeEnum getStatus(Integer code) {

        if (code.equals(ACCOUNT.getCode())) {
            return ACCOUNT;
        }
        if (code.equals(MOBILE.getCode())) {
            return MOBILE;
        }
        return null;
    }
}
