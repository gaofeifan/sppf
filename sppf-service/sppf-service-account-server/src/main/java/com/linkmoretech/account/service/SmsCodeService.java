package com.linkmoretech.account.service;

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
     * */
    void createSmsCode(String mobile, String clientId) throws CommonException;

    /**
     * 生成验证码 - 个人版业务
     * @param mobile 用户手机号
     * @param smsType 验证码类型 1 用户注册 2 修改密码
     * */
    void createSmsCode(String mobile, Integer smsType) throws CommonException;
}
