package com.linkmoretech.auth.authentication.component;

import com.linkmoretech.auth.authentication.authentication.sms.mobile.ValidateCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: alec
 * Description:
 * @date: 17:29 2019-06-19
 */
@Component
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {
    private final Integer LEN = 4;
    private final Integer SECOND = 120;

    @Override
    public ValidateCode createValidateCode() {
        String code = RandomStringUtils.randomNumeric(LEN);
        return new ValidateCode(code, SECOND);
    }
}
