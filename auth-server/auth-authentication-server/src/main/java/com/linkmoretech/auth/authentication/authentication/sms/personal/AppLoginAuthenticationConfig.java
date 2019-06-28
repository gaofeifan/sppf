package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.common.service.AppUserDetailAbstract;
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
public class AppLoginAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    AuthenticationSuccessHandler validateSuccessHandler;

    @Autowired
    AuthenticationFailureHandler validateFailureHandler;

    @Resource
    AppUserDetailAbstract appUserDetailAbstract;

    @Override
    public void configure(HttpSecurity http){

        log.info("个人版手机登录");
        AppLoginAuthenticationFilter appLoginAuthenticationFilter = new AppLoginAuthenticationFilter();
        appLoginAuthenticationFilter.setAuthenticationSuccessHandler(validateSuccessHandler);
        appLoginAuthenticationFilter.setAuthenticationFailureHandler(validateFailureHandler);
        appLoginAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        AppLoginAuthenticationProvider appLoginAuthenticationProvider = new AppLoginAuthenticationProvider();
        appLoginAuthenticationProvider.setAppUserDetailAbstract(appUserDetailAbstract);
        http.authenticationProvider(appLoginAuthenticationProvider)
                .addFilterAfter(appLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
