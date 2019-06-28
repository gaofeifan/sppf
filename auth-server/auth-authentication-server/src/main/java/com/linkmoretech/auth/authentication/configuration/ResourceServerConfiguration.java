package com.linkmoretech.auth.authentication.configuration;

import com.linkmoretech.auth.authentication.authentication.ValidateFailureHandler;
import com.linkmoretech.auth.authentication.authentication.ValidateSuccessHandler;
import com.linkmoretech.auth.authentication.authentication.account.AccAuthenticationManagerConfig;
import com.linkmoretech.auth.authentication.authentication.sms.mobile.SmsCodeFilter;
import com.linkmoretech.auth.authentication.authentication.sms.personal.AppRegisterAuthenticationConfig;
import com.linkmoretech.auth.authentication.authentication.sms.manager.SmsAuthenticationManagerConfig;
import com.linkmoretech.auth.authentication.component.ValidateCodeManage;
import com.linkmoretech.auth.common.construct.ParamsConstruct;
import com.linkmoretech.auth.common.configuration.OauthResourceConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: alec
 * Description:
 * @date: 15:17 2019-06-13
 */
@Configuration
@EnableResourceServer
@Slf4j
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {



    @Autowired
    ValidateSuccessHandler validateSuccessHandler;

    @Autowired
    ValidateFailureHandler validateFailureHandler;

    @Autowired
    SmsAuthenticationManagerConfig smsAuthenticationManagerConfig;

    @Autowired
    AccAuthenticationManagerConfig accAuthenticationManagerConfig;

    /**
     * 用户注册过滤器
     * */
    @Autowired
    AppRegisterAuthenticationConfig appRegisterAuthenticationConfig;

    @Autowired
    ValidateCodeManage validateCodeManage;

    @Autowired
    OauthResourceConfig oauthResourceConfig;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(validateCodeManage, validateFailureHandler);
        smsCodeFilter.afterPropertiesSet();

        http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class);

        http.formLogin() //采用表单认证方式 -- 身份认证
            .successHandler(validateSuccessHandler)
            .failureHandler(validateFailureHandler);

        http
            .apply(accAuthenticationManagerConfig)
            .and()
            .apply(smsAuthenticationManagerConfig)
            .and()
            .apply(appRegisterAuthenticationConfig)
            .and()
            .authorizeRequests() // 授权设定
            .antMatchers(
                ParamsConstruct.LOGIN_CUSTOMER,
                ParamsConstruct.LOGIN_MANAGE_MOBILE,
                ParamsConstruct.SEND_SMS,
                ParamsConstruct.NO_LOGIN_TIP_INFO,
                ParamsConstruct.REGISTER_APP,
                ParamsConstruct.CSS,
                ParamsConstruct.JS,
                ParamsConstruct.DOC,
                ParamsConstruct.SWAGGER_URL
            ).permitAll()    //对此链接不拦截
            .anyRequest() // 所有请求
            .authenticated() //需要身份认证
            .and()
            .csrf().disable();//关闭csrf
    }
}
