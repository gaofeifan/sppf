package com.linkmoretech.notice.enums;

import lombok.Getter;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/12
 */
@Getter
public enum AgingTypeEnum {

    SEND(0,"发送"),
    SEND_AFFIRM(1,"发送,保证一定发出"),
    SEND_CONFIRM(2,"发送,需要客户端进行确认");

    private int code;

    private String name;

     AgingTypeEnum(int code,String name){
        this.code = code;
        this.name = name;
    }
}
