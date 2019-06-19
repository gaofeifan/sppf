package com.linkmoretech.auth.authentication.configuration;

import com.linkmoretech.auth.authentication.authentication.ValidateFailureHandler;
import com.linkmoretech.auth.authentication.authentication.ValidateSuccessHandler;
import com.linkmoretech.auth.authentication.authentication.account.AccAuthenticationManagerConfig;
import com.linkmoretech.auth.authentication.authentication.sms.personal.SmsAuthenticationPersonalConfig;
import com.linkmoretech.auth.authentication.authentication.sms.manager.SmsAuthenticationManagerConfig;
import com.linkmoretech.auth.authentication.construct.ParamsConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @Author: alec
 * Description:
 * @date: 15:17 2019-06-13
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {



    @Autowired
    ValidateSuccessHandler validateSuccessHandler;

    @Autowired
    ValidateFailureHandler validateFailureHandler;

    @Autowired
    SmsAuthenticationManagerConfig smsAuthenticationManagerConfig;

    @Autowired
    SmsAuthenticationPersonalConfig msAuthenticationPersonalConfig;

    @Autowired
    AccAuthenticationManagerConfig accAuthenticationManagerConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin() //采用表单认证方式 -- 身份认证
            .successHandler(validateSuccessHandler)
            .failureHandler(validateFailureHandler);

        http
            .apply(accAuthenticationManagerConfig)
            .and()
            .apply(msAuthenticationPersonalConfig)
            .and()
            .apply(smsAuthenticationManagerConfig)
            .and()

            .authorizeRequests() // 授权设定
            .antMatchers(
                    ParamsConstruct.LOGIN_CUSTOMER,
                    ParamsConstruct.LOGIN_MANAGE_PASSWORD,
                    ParamsConstruct.LOGIN_MANAGE_MOBILE,
                    ParamsConstruct.NO_LOGIN_TIP_INFO,
                    ParamsConstruct.LOGIN_MOBILE_PERSONAL,
                    ParamsConstruct.LOGIN_PLATFORM).permitAll()    //对此链接不拦截
            .anyRequest() // 所有请求
            .authenticated() //需要身份认证
            .and()
            .csrf().disable();//关闭csrf
    }
}
