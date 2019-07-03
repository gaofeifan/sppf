package com.linkmoretech.account.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description: 登录认证验证码类型
 * @date: 10:50 2019-07-03
 */
@Getter
public enum  SmsTypeEnum {

    LOGIN(0, "登录"),
    REGISTER(1, "注册"),
    FIND_PASSWORD(2, "找回密码"),
    RESET_PASSWORD(3, "重置密码"),
    ;

    private Integer code;

    private String message;

    SmsTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static SmsTypeEnum getStatus(Integer code) {

        if (code.equals(LOGIN.getCode())) {
            return LOGIN;
        }
        if (code.equals(REGISTER.getCode())) {
            return REGISTER;
        }
        if (code.equals(FIND_PASSWORD.getCode())) {
            return FIND_PASSWORD;
        }
        if (code.equals(RESET_PASSWORD.getCode())) {
            return RESET_PASSWORD;
        }
        return null;
    }
}
