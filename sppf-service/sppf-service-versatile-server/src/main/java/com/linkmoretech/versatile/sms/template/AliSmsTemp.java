package com.linkmoretech.versatile.sms.template;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: alec
 * Description:
 * @date: 20:25 2019-07-02
 */
public class AliSmsTemp extends SmsTemp {

    private String validate;

    public AliSmsTemp(String code, String validate) {
        super(code);
        this.validate = validate;
    }

    public AliSmsTemp(String code) {
        super(code);
    }

    @Override
    public String getNotifyMessage() {
        /**
         * 通知类短信不需要发送额外数据，直接返回null
         * */
        return null;
    }

    @Override
    public String getValidateMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", validate);
        return jsonObject.toJSONString();
    }
}
