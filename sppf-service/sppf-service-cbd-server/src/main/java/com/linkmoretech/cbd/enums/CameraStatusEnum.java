package com.linkmoretech.cbd.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description:
 * @date: 19:09 2019-05-08
 */
@Getter
public enum CameraStatusEnum {

    ENABLED(1, "可用"),
    DISABLED(0, "不可用"),
    ;
    private Integer code;

    private String message;

    CameraStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static CameraStatusEnum getStatus(Integer code) {

        if (code.equals(ENABLED.getCode())) {
            return ENABLED;
        }
        if (code.equals(DISABLED.getCode())) {
            return DISABLED;
        }
        return null;
    }
}
