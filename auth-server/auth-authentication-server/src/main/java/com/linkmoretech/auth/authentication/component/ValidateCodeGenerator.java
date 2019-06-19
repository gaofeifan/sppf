package com.linkmoretech.auth.authentication.component;

import com.linkmoretech.auth.authentication.authentication.sms.mobile.ValidateCode;

/**
 * @Author: alec
 * Description:
 * @date: 17:24 2019-06-19
 */
public interface ValidateCodeGenerator {

    /**
     * 生成随机验证码
     * @return 验证码
     * */
    ValidateCode createValidateCode();
}
