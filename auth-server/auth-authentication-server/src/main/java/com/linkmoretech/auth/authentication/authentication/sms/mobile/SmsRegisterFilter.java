package com.linkmoretech.auth.authentication.authentication.sms.mobile;

import com.linkmoretech.auth.authentication.authentication.ValidateFailureHandler;
import com.linkmoretech.auth.authentication.component.ValidateCodeManage;
import com.linkmoretech.auth.common.construct.ParamsConstruct;

/**
 * @Author: alec
 * Description:
 * @date: 17:00 2019-07-11
 */
public class SmsRegisterFilter extends SmsCodeFilter {

    public SmsRegisterFilter(ValidateCodeManage validateCodeManage, ValidateFailureHandler validateFailureHandler) {
        super(validateCodeManage, validateFailureHandler, ParamsConstruct.SMS_TYPE_REGISTER);
    }
    @Override
    public void afterPropertiesSet()  {
        urls.add(ParamsConstruct.REGISTER_APP);
        urls.add(ParamsConstruct.APP_REGISTER);
    }

}
