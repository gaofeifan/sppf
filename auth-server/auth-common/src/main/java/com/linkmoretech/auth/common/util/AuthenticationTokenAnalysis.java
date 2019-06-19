package com.linkmoretech.auth.common.util;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.auth.common.configuration.SmsAuthenticationToken;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.*;

/**
 * @Author: alec
 * Description: Token 解析
 * @date: 20:52 2019-06-14
 */
@Slf4j
public class AuthenticationTokenAnalysis {

    @Getter
    private String username;

    @Getter
    private String clientId;

    @Getter
    private Set<Long> dataAuthentications;


    public AuthenticationTokenAnalysis (Authentication authentication) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        this.username = authentication.getPrincipal().toString();
        SmsAuthenticationToken token = (SmsAuthenticationToken) oAuth2Authentication.getUserAuthentication();
        log.info("token detail is {}" , JSONObject.toJSONString(token));

        if (token != null) {
            this.clientId = token.getClientId();
            this.username = (String) token.getPrincipal();
            this.dataAuthentications = token.getDataResources();
        }
    }

}
