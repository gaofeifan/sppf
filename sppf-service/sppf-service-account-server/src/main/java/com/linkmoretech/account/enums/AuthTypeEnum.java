package com.linkmoretech.account.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description: 数据权限类型
 * @date: 16:32 2019-06-24
 */
@Getter
public enum AuthTypeEnum {

    ALL(1, "开启数据权限"),
    NO_ALL(0, "关闭数据权限"),
    ;

    private Integer code;

    private String message;

    AuthTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AuthTypeEnum getStatus(Integer code) {

        if (code.equals(ALL.getCode())) {
            return ALL;
        }
        if (code.equals(NO_ALL.getCode())) {
            return NO_ALL;
        }
        return null;
    }
}
