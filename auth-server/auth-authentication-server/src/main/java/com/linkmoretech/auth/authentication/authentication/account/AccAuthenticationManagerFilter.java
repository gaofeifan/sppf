package com.linkmoretech.auth.authentication.authentication.account;

import com.linkmoretech.auth.authentication.authentication.ValidateAuthenticationFilter;
import com.linkmoretech.auth.authentication.construct.ParamsConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: alec
 * Description: 管理版短信登录过滤器
 * @date: 15:28 2019-06-17
 */
@Slf4j
public class AccAuthenticationManagerFilter extends ValidateAuthenticationFilter {

    private String usernameParams = "username";

    private String passwordParams = "password";

    protected AccAuthenticationManagerFilter() {
        super(new AntPathRequestMatcher(ParamsConstruct.LOGIN_CUSTOMER, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {

        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        Map<String, String> loginParams = getLoginParams(request);
        String clientId = getTokenValue(loginParams, this.CLIENT_ID);
        String username = getTokenValue(loginParams, usernameParams);
        String password = getTokenValue(loginParams, passwordParams);
        AccAuthenticationManagerToken authRequest = new AccAuthenticationManagerToken(username, password, clientId);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
