package com.linkmoretech.notice.enums;

import lombok.Getter;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/12
 */
@Getter
public enum MqMesEnum {
    YES(true),
    NO(false);

    private boolean flag;
    MqMesEnum(boolean flag){
        this.flag = flag;
    }
}
