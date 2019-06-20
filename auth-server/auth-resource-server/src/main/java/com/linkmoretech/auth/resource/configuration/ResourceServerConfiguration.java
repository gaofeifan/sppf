package com.linkmoretech.auth.resource.configuration;

import com.sun.deploy.util.StringUtils;
import io.netty.util.internal.StringUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.Arrays;


/**
 * @Author: alec
 * Description:
 * @date: 15:17 2019-06-13
 */
@Setter
@Slf4j
@EnableResourceServer
@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

   @Autowired
   OauthResourceConfig oauthResourceConfig;

    private final String OAUTH_URL = "/oauth/**";

    @Override
    public void configure(HttpSecurity http) throws Exception {

       /* String matchers = StringUtils.join(Arrays.asList(oauthResourceConfig.getIgnores().toArray()), ",");
        if (StringUtil.isNullOrEmpty(matchers)) {
            matchers = OAUTH_URL;
        }
        log.info("过滤URL {}", matchers);*/
        http
              .authorizeRequests()
              .antMatchers(OAUTH_URL).permitAll()
              /*  .authorizeRequests() // 授权设定
                .antMatchers("/oauth/**", ParamsConstruct.LOGIN_CUSTOMER,
                        ParamsConstruct.LOGIN_MANAGE_PASSWORD,
                        ParamsConstruct.LOGIN_MANAGE_MOBILE,
                        ParamsConstruct.NO_LOGIN_TIP_INFO,
                        ParamsConstruct.LOGIN_PLATFORM).permitAll()    //对此链接不拦截*/
                .anyRequest() // 所有请求
                .authenticated() //需要身份认证
                .and()
                .csrf().disable();//关闭csrf
    }
}
