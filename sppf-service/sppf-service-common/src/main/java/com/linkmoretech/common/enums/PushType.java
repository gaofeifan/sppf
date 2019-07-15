package com.linkmoretech.common.enums;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/11
 */
public enum PushType {

    LOCK_DOWN(0,"降锁操作"),
    LOCK_UP(1,"升锁操作"),
    ;

    public int code;
    public String name;

    PushType(int code ,String name){
        this.code = code;
        this.name = name;
    }
}

