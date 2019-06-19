package com.linkmoretech.auth.authentication.authentication;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 19:48 2019-06-18
 */
@Slf4j
public  abstract class ValidateAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected final String CLIENT_ID = "clientId";

    private String mobileParams = "mobile";

    protected boolean postOnly = true;

    protected ValidateAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected abstract void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest);

    protected Map<String, String> getLoginParams(HttpServletRequest request) {
        Map returnMap = new HashMap<>();
        try {
            BufferedReader br = request.getReader();
            String str, wholeStr = "";
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            log.info(wholeStr);

            returnMap = JSONObject.parseObject(wholeStr, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    protected String getTokenValue(Map<String, String> paramsMap, String clientKey) {
        String params =  paramsMap.get(clientKey);
        if (params == null) {
            params = "";
        }
        /**
         * 认证流程验证码是否正确
         * */
        params = params.trim();
        return params;
    }

    public String[] attempt (HttpServletRequest request){
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        Map<String, String> loginParams = getLoginParams(request);
        String clientId = loginParams.get(this.CLIENT_ID);
        String mobile = loginParams.get(mobileParams);
        return new String[]{clientId, mobile};
    }
}
