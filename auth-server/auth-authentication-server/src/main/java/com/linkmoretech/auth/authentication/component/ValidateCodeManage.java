package com.linkmoretech.auth.authentication.component;

import com.linkmoretech.auth.authentication.authentication.sms.mobile.ValidateCode;

/**
 * @Author: alec
 * Description: 验证码管理
 * @date: 17:35 2019-06-19
 */
public interface ValidateCodeManage {

    /**
     * 保存验证码
     * @param validateCode 验证码
     * @param mobile 手机号
     * @param clientId 客户端
     * */
    void saveValidateCode(ValidateCode validateCode, String clientId, Integer type, String mobile);

    /**
     * 保存验证码
     * @param validateCode 验证码
     * @param mobile 手机号
     * */
    void saveValidateCode(ValidateCode validateCode, Integer type, String mobile);

    /**
     * 获取验证码
     * @param mobile 手机号
     * @param clientId 客户端
     * */
    String findValidateCode(String clientId, Integer type, String mobile);

    /**
     * 获取验证码
     * @param mobile 手机号
     * @param type 客户端
     * */
    String findValidateCode(Integer type, String mobile);

    /**
     * 删除验证码
     * @param mobile 手机号
     * @param clientId 客户端
     * */
    void deleteValidateCode(Integer type,String clientId, String mobile);
}
