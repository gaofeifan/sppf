package com.linkmoretech.account.controller;

/**
 * @Author: alec
 * Description:
 * @date: 11:14 2019-06-04
 */

import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.common.annation.IgnoreResponseAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping(value = "principal")
    @IgnoreResponseAdvice
    public Principal user(Principal user) {
        log.info("user {}", user.getClass().getName());
        return user;
    }

    @GetMapping(value = "revoke")
    public void revokeToken(@RequestParam(value = "accessToken") String accessToken) {
        consumerTokenServices.revokeToken(accessToken);
    }

    @GetMapping(value = "authentication/user")
    public UserDetails userDetails (@AuthenticationPrincipal AccountUserDetail userDetails) {
        return userDetails;
    }
}
