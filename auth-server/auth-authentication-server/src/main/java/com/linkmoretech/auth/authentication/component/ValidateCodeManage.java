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
    void saveValidateCode(ValidateCode validateCode, String clientId, String mobile);

    /**
     * 获取验证码
     * @param mobile 手机号
     * @param clientId 客户端
     * */
    String findValidateCode(String clientId, String mobile);


    /**
     * 删除验证码
     * @param mobile 手机号
     * @param clientId 客户端
     * */
    void deleteValidateCode(String clientId, String mobile);
}
