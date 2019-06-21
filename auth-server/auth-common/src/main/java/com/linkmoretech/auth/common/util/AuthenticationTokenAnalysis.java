package com.linkmoretech.auth.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.auth.common.bean.AccountUserDetail;
import com.linkmoretech.auth.common.configuration.SmsAuthenticationToken;
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
    private Set dataAuthentications;


    public AuthenticationTokenAnalysis (Authentication authentication) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication oauthAuthentication =  oAuth2Authentication.getUserAuthentication();
        SmsAuthenticationToken token = null;
        if (oauthAuthentication instanceof UsernamePasswordAuthenticationToken) {
            analyseTokenJson(oauthAuthentication);
        } else {
            token = (SmsAuthenticationToken) oAuth2Authentication.getUserAuthentication();
            if (token != null) {
                AccountUserDetail userDetail = (AccountUserDetail) token.getPrincipal();
                this.clientId = token.getClientId();
                this.username = userDetail.getUsername();
                this.dataAuthentications = userDetail.getDataAuthorities();
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
        List dataList = JSONObject.parseArray(JSONObject.toJSONString(value.get("dataAuthorities")));
        this.dataAuthentications = new HashSet(dataList);
        log.info("value {}", value);
    }
}
