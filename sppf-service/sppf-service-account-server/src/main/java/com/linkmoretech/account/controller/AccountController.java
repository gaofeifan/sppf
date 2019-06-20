package com.linkmoretech.account.controller;

/**
 * @Author: alec
 * Description:
 * @date: 11:14 2019-06-04
 */

import com.linkmoretech.account.service.SmsCodeService;
import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import com.linkmoretech.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
* 账户登入，登出
* */
@RestController
@Slf4j
public class AccountController {

    @Autowired
    ConsumerTokenServices consumerTokenServices;

    @Autowired
    SmsCodeService smsCodeService;

    @GetMapping(value = "principal")
    @IgnoreResponseAdvice
    public Principal user(Principal user) {
        log.info("user {}", user.getClass().getName());
        return user;
    }

    /***/
    @GetMapping(value = "revoke")
    public void revokeToken(@RequestParam(value = "accessToken") String accessToken) {
        consumerTokenServices.revokeToken(accessToken);
    }

    @GetMapping(value = "authentication/user")
    public UserDetails userDetails (AccountUserDetail userDetails) {
        return userDetails;
    }


    /**
     * 生成短信验证码
     * */
    @GetMapping(value = "sms/code")
    public void createSmsCode(@RequestParam (value = "clientId") String clientId,
                              @RequestParam (value = "mobile") String mobile) throws CommonException {
        smsCodeService.createSmsCode(mobile, clientId);
    }
}
