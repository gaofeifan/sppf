package com.linkmoretech.auth.authentication.authentication.sms.mobile;

import com.linkmoretech.auth.authentication.authentication.ValidateFailureHandler;
import com.linkmoretech.auth.authentication.component.ValidateCodeManage;
import com.linkmoretech.auth.common.construct.ParamsConstruct;
import org.springframework.security.access.method.P;

/**
 * @Author: alec
 * Description:
 * @date: 13:44 2019-07-03
 */
public class SmsLoginFilter extends SmsCodeFilter {

    public SmsLoginFilter(ValidateCodeManage validateCodeManage, ValidateFailureHandler validateFailureHandler) {
        super(validateCodeManage, validateFailureHandler, ParamsConstruct.SMS_TYPE_LOGIN);
    }

    @Override
    public void afterPropertiesSet()  {
        urls.add(ParamsConstruct.LOGIN_MANAGE_MOBILE);
        urls.add(ParamsConstruct.LOGIN_MOBILE_PERSONAL);
    }
}
