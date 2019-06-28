package com.linkmoretech.auth.authentication.authentication;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.auth.common.util.AuthenticationTokenAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 10:31 2019-06-13
 */
@Component
@Slf4j
public class ValidateSuccessHandler implements AuthenticationSuccessHandler {

    private final String HEADER_KEY = "accessToken";

    private final String BASIC = "Basic ";

    @Autowired
    ClientDetailsService clientDetailsService;

    @Qualifier("defaultAuthorizationServerTokenServices")
    @Autowired
    AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String message = "系统登录成功";
        String header = httpServletRequest.getHeader(HEADER_KEY);
        log.info("message {} , header {} ",message, header);
        if (StringUtils.isEmpty(header) || !header.startsWith(BASIC)) {
            throw new UnapprovedClientAuthenticationException("Client id 不正确");
        }
        String[] tokens = this.extractAndDecodeHeader(header, httpServletRequest);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        log.info("client {}, secret {}", clientId, clientSecret);

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("ClientId 对应配置信息不存在， clientId" + clientId );
        }
        if (!clientDetails.getClientSecret().equals(clientSecret)) {
            throw new UnapprovedClientAuthenticationException("ClientId 对应配置信息不正确， clientId" + clientId );
        }

        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setCode(HttpStatus.OK.value());
        loginResponse.setMessage(message);

        Map<String, Object> data = new HashMap<>();
        data.put("access_token", oAuth2AccessToken.getValue());
        data.put("refresh_token", oAuth2AccessToken.getRefreshToken());
        data.put("expiration", oAuth2AccessToken.getExpiration());
        AuthenticationTokenAnalysis authenticationTokenAnalysis = new AuthenticationTokenAnalysis(oAuth2Authentication);
        data.put("info", authenticationTokenAnalysis);
        loginResponse.setData(data);
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSONObject.toJSONString(loginResponse));
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;

        try {
            decoded = Base64.getDecoder().decode(base64Token);
            log.info("decoded {}" , decoded);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded, "utf-8");
        log.info("token {}", token);
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }

}
