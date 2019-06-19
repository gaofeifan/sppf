package com.linkmoretech.parking.enums;

import lombok.Getter;

/**
 * @Author: alec
 * @Description:
 * @date: 7:38 PM 2019/4/22
 */
@Getter
public enum CarPlaceStatusEnum {
    FREE(0, "空闲"),
    USING(1, "使用"),
            ;
    private Integer code;

    private String message;

    CarPlaceStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
