package com.linkmoretech.user.enums;

import lombok.Getter;

/**
 * 用户类型枚举
 * @Author: alec
 * @Description:
 * @date: 下午3:29 2019/4/10
 */
@Getter
public enum UserTypeEnum {

    LONG_RENTAL_USER(1, "长租用户"),
    TEMPORARY_USER(0, "非长租用户"),
    ;

    private Integer code;

    private String message;

    UserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
