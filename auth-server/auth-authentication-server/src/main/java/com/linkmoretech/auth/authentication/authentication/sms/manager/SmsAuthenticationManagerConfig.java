package com.linkmoretech.auth.authentication.authentication.sms.manager;

import com.linkmoretech.auth.common.service.UserDetailAccountAbstract;
import com.linkmoretech.auth.common.service.UserDetailMobileAbstract;
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
 * @date: 15:29 2019-06-17
 */
@Component
public class SmsAuthenticationManagerConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    AuthenticationSuccessHandler validateSuccessHandler;

    @Autowired
    AuthenticationFailureHandler validateFailureHandler;

    @Resource
    UserDetailAccountAbstract userDetailAccountService;


    @Override
    public void configure(HttpSecurity http) {

        SmsAuthenticationManagerFilter smsAuthenticationFilter = new SmsAuthenticationManagerFilter();
        smsAuthenticationFilter.setAuthenticationSuccessHandler(validateSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(validateFailureHandler);
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        SmsAuthenticationManagerProvider smsAuthenticationProvider = new SmsAuthenticationManagerProvider();
        smsAuthenticationProvider.setUserDetailService(userDetailAccountService);
        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
