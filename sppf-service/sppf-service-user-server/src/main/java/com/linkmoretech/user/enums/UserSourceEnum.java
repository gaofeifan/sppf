package com.linkmoretech.user.enums;

import lombok.Getter;

/**
 * 用户来源枚举
 * @Author: alec
 * @Description:
 * @date: 下午3:28 2019/4/10
 */
@Getter
public enum UserSourceEnum {

    APP(0, "APP"),
    WE_CHAT(1, "微信小程序"),
    OTHER(2, "其他来源"),
    ;

    private Integer code;

    private String message;

    UserSourceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
