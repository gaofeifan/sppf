package com.linkmoretech.auth.authentication.authentication;

import com.linkmoretech.auth.common.construct.ParamsConstruct;
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

    protected Map<String, Object> getLoginParams(HttpServletRequest request) {
        return HttpRequestBodyUtil.getHttpBody(request);
    }

    protected String getTokenValue(Map<String, Object> paramsMap, String clientKey) {
        String params =  (String) paramsMap.get(clientKey);
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
        Map<String, Object> loginParams = getLoginParamsMap(request);
        String clientId = (String)loginParams.get(ParamsConstruct.CLIENT_ID);
        String mobile = (String)loginParams.get(ParamsConstruct.MOBILE_PARAMS);
        return new String[]{clientId, mobile};
    }

    public Object[] attemptApp (HttpServletRequest request){
        Map<String, Object> loginParams = getLoginParamsMap(request);
        String mobile = (String)loginParams.get(ParamsConstruct.MOBILE_PARAMS);
        Integer type = (Integer) loginParams.get(ParamsConstruct.SOURCE);
        return new Object[]{type, mobile};
    }

    public Map<String, Object> getLoginParamsMap (HttpServletRequest request) {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        Map<String, Object> loginParams = getLoginParams(request);
        return loginParams;
    }
}
