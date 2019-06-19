package com.linkmoretech.parking.enums;

import lombok.Getter;

/**
 * @Author: alec
 * @Description:
 * @date: 7:38 PM 2019/4/22
 */
@Getter
public enum CarPlaceTypeEnum {

    TEMP_PLACE(1, "临停车位"),
    FIXED_PLACE(2, "固定车位"),
    ;
    private Integer code;

    private String message;

    CarPlaceTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CarPlaceTypeEnum getType(Integer code) {

        if (code.equals(TEMP_PLACE.getCode())) {
            return TEMP_PLACE;
        }
        if (code.equals(FIXED_PLACE.getCode())) {
            return FIXED_PLACE;
        }
        return null;
    }
}
