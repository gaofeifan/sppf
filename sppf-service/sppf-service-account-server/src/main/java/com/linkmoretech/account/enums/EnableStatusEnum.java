package com.linkmoretech.account.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description: 可用枚举
 * @date: 16:27 2019-05-29
 */
@Getter
public enum EnableStatusEnum {

    ENABLED(1, "启用"),
    DISABLED(0, "禁用"),
    ;

    private Integer code;

    private String message;

    EnableStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static EnableStatusEnum getStatus(Integer code) {

        if (code.equals(ENABLED.getCode())) {
            return ENABLED;
        }
        if (code.equals(DISABLED.getCode())) {
            return DISABLED;
        }
        return null;
    }
}