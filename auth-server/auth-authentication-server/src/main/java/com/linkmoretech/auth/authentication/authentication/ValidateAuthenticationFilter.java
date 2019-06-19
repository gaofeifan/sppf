package com.linkmoretech.auth.authentication.authentication;

import com.linkmoretech.auth.authentication.construct.ParamsConstruct;
import com.linkmoretech.auth.common.util.HttpRequestBodyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 19:48 2019-06-18
 */
@Slf4j
public  abstract class ValidateAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected boolean postOnly = true;

    protected ValidateAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected abstract void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest);

    protected Map<String, String> getLoginParams(HttpServletRequest request) {
        return HttpRequestBodyUtil.getHttpBody(request);
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
        String clientId = loginParams.get(ParamsConstruct.CLIENT_ID);
        String mobile = loginParams.get(ParamsConstruct.MOBILE_PARAMS);
        return new String[]{clientId, mobile};
    }
}
