package com.linkmoretech.versatile.sms.template;

import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 17:11 2019-07-02
 */
public class JiuSmsTemp extends SmsTemp {



    public JiuSmsTemp(String code, Map<String, String> templateMap) {
        super(code);
    }


    @Override
    public String getNotifyMessage() {
        return null;
    }

    @Override
    public String getValidateMessage() {
        return null;
    }
}
