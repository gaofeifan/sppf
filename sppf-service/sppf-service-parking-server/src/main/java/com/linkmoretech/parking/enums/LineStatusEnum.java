package com.linkmoretech.parking.enums;

import lombok.Getter;

/**
 * 上线状态
 * @Author: alec
 * @Description:
 * @date: 6:15 PM 2019/4/18
 */
@Getter
public enum LineStatusEnum {
    ONLINE(1, "上线"),
    OFFLINE(0, "下线"),
    ;
    private Integer code;

    private String message;

    LineStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static LineStatusEnum getStatus(Integer code) {

        if (code.equals(ONLINE.getCode())) {
            return ONLINE;
        }
        if (code.equals(OFFLINE.getCode())) {
            return OFFLINE;
        }
        return null;
    }
}
