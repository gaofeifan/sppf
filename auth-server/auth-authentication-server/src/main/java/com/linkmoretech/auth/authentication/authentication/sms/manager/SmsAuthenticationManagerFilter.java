package com.linkmoretech.auth.authentication.authentication.sms.manager;

import com.alibaba.fastjson.JSONObject;
import com.linkmoretech.auth.authentication.authentication.ValidateAuthenticationFilter;
import com.linkmoretech.auth.authentication.construct.ParamsConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alec
 * Description: 管理版短信登录过滤器
 * @date: 15:28 2019-06-17
 */
@Slf4j
public class SmsAuthenticationManagerFilter extends ValidateAuthenticationFilter {

    private String mobileParams = "mobile";


    public SmsAuthenticationManagerFilter() {
        super(new AntPathRequestMatcher(ParamsConstruct.LOGIN_MANAGE_MOBILE, HttpMethod.POST.name()));
        log.info("管理版手机号登录过滤器 拦截 {}", ParamsConstruct.LOGIN_MANAGE_MOBILE);
    }

    @Override
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        /*if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        Map<String, String> loginParams = getLoginParams(request);

        String clientId = loginParams.get(this.CLIENT_ID);

        String mobile = loginParams.get(mobileParams);*/
        String[] params = this.attempt(request);
        SmsAuthenticationManagerToken authRequest = new SmsAuthenticationManagerToken(params[1], params[0]);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
