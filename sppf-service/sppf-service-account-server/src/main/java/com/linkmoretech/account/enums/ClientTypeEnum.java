package com.linkmoretech.account.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description: 客户端类型
 * @date: 15:49 2019-06-28
 */
@Getter
public enum  ClientTypeEnum {

    SYSTEM ("system"),
    PLATFORM ("platform"),
    MANAGE ("manage"),
    PERSONAL ("personal"),
    ;

    private String code;

    ClientTypeEnum (String code) {
        this.code = code;
    }
}
