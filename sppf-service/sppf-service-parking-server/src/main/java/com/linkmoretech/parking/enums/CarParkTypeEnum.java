package com.linkmoretech.parking.enums;

import lombok.Getter;

/**
 * @Author: alec
 * @Description:
 * @date: 下午2:18 2019/4/17
 */
@Getter
public enum CarParkTypeEnum {
    TEMPORARY(1, "临停车场"),
    RENTAL(2, "长租车场"),
    COMBINATION(3, "组合车场")
    ;
    private Integer code;

    private String message;

    CarParkTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
