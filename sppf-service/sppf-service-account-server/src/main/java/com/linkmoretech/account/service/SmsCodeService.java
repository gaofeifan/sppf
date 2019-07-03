package com.linkmoretech.account.service;

import com.linkmoretech.account.enums.SmsTypeEnum;
import com.linkmoretech.common.exception.CommonException;

/**
 * @Author: alec
 * Description:
 * @date: 17:14 2019-06-19
 */
public interface SmsCodeService {

    /**
     * 生成验证码
     * @param clientId 客户端ID
     * @param mobile 手机号
     * @param smsTypeEnum 验证码类型
     * */
    void createSmsCode(String mobile, String clientId, SmsTypeEnum smsTypeEnum) throws CommonException;


    /**
     * 生成验证码
     * @param mobile 手机号
     * @param smsTypeEnum 验证码类型
     * */
    void createSmsCode(String mobile, SmsTypeEnum smsTypeEnum) throws CommonException;

}
