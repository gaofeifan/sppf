package com.linkmoretech.auth.common.util;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.token.AppAuthenticationToken;
import com.linkmoretech.auth.common.token.SmsAuthenticationToken;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private Boolean registerUser;

    @Getter
    private Long userId;


    public AuthenticationTokenAnalysis (Authentication authentication) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication oauthAuthentication =  oAuth2Authentication.getUserAuthentication();
        SmsAuthenticationToken token;
        if (oauthAuthentication instanceof UsernamePasswordAuthenticationToken) {
            analyseTokenJson(oauthAuthentication);
        } else if (oauthAuthentication instanceof AppAuthenticationToken) {
            AppAuthenticationToken authenticationToken = (AppAuthenticationToken) oauthAuthentication;
            this.userId = authenticationToken.getUserId();
            this.username = (String) authenticationToken.getPrincipal();
            this.registerUser = authenticationToken.getRegister();
        } else {
            token = (SmsAuthenticationToken) oAuth2Authentication.getUserAuthentication();
            if (token != null) {
                log.info("token {}", token);
                AccountUserDetail userDetail = (AccountUserDetail) token.getPrincipal();
                this.clientId = token.getClientId();
                this.username = userDetail.getUsername();
                this.userId = userDetail.getUserId();
            }
        }


    }

    private void analyseTokenJson(Authentication oauthAuthentication) {
        this.username = (String) oauthAuthentication.getPrincipal();
        String mapKey = "principal";
        String tokeValue = JSONObject.toJSONString(oauthAuthentication.getDetails());
        log.info(tokeValue);
        Map tokenMap = JSONObject.parseObject(tokeValue, Map.class);
        Map value = (Map) tokenMap.get(mapKey);
        this.clientId = (String) value.get("clientId");
        this.userId =  Long.valueOf(String.valueOf(value.get("userId")));
        log.info("value {}", value);
    }
}
