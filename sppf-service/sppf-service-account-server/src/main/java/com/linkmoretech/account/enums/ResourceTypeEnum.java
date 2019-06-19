package com.linkmoretech.account.enums;

import lombok.Getter;

/**
 * @Author: alec
 * Description:
 * @date: 19:55 2019-05-29
 */
@Getter
public enum ResourceTypeEnum {

    BUTTON(1, "功能"),
    MENU(0, "菜单"),
    ;

    private Integer code;

    private String message;

    ResourceTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResourceTypeEnum getType(Integer code) {

        if (code.equals(BUTTON.getCode())) {
            return BUTTON;
        }
        if (code.equals(MENU.getCode())) {
            return MENU;
        }
        return null;
    }
}
