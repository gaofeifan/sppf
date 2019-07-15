package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: alec
 * Description:
 * @date: 11:08 2019-06-28
 */
@Component
@Slf4j
public class AppCodeAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    AuthenticationSuccessHandler validateSuccessHandler;

    @Autowired
    AuthenticationFailureHandler validateFailureHandler;

    @Resource
    UserDetailAccountAbstract userDetailAccountService;

    @Override
    public void configure(HttpSecurity http){

        log.info("小程序code 登录");
        AppCodeAuthenticationFilter appCodeAuthenticationFilter = new AppCodeAuthenticationFilter();
        appCodeAuthenticationFilter.setAuthenticationSuccessHandler(validateSuccessHandler);
        appCodeAuthenticationFilter.setAuthenticationFailureHandler(validateFailureHandler);
        appCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        AppCodeAuthenticationProvider appCodeAuthenticationProvider = new AppCodeAuthenticationProvider();
        appCodeAuthenticationProvider.setUserDetailService(userDetailAccountService);
        http.authenticationProvider(appCodeAuthenticationProvider)
                .addFilterAfter(appCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
