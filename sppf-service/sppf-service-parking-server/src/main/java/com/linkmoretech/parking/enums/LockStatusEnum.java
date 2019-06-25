package com.linkmoretech.parking.enums;

import lombok.Getter;

/**
 * @Author: alec
 * @Description:
 * @date: 7:38 PM 2019/4/22
 */
@Getter
public enum LockStatusEnum {
    UP(1, "升起"),
    DOWN(2, "降下"),
    HTTCH(3, "故障")
    ;
    private Integer code;

    private String message;

    LockStatusEnum (Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
