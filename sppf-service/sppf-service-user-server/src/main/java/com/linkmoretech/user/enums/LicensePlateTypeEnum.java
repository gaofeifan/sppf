package com.linkmoretech.user.enums;

import lombok.Getter;

/**
 * 车牌类型枚举
 * @Author: alec
 * @Description:
 * @date: 下午3:26 2019/4/10
 */
@Getter
public enum LicensePlateTypeEnum {
    NEW_ENERGY(0, "系能源"),
    FUEL_TRUCK(1, "燃油车"),
    ;

    private Integer code;

    private String message;

    LicensePlateTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
