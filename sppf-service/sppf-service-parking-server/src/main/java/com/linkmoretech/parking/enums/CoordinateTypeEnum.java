package com.linkmoretech.parking.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: alec
 * Description:
 * @date: 18:08 2019-05-07
 */
@Getter
@NoArgsConstructor
public enum CoordinateTypeEnum {

    GAO_DE_MAP(1, "高得"),
    BAI_DU_MAP(2, "百度")
    ;

    private Integer code;

    private String message;

    CoordinateTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
