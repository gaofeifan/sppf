package com.linkmoretech.auth.authentication.authentication.sms.personal;

import com.linkmoretech.auth.authentication.authentication.ValidateAuthenticationFilter;
import com.linkmoretech.auth.common.construct.ParamsConstruct;
import com.linkmoretech.auth.common.token.AppCodeAuthenticationToken;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: alec
 * Description: 小程序code登录
 * @date: 11:20 2019-07-11
 */
@Slf4j
public class AppCodeAuthenticationFilter extends ValidateAuthenticationFilter {


    protected AppCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(ParamsConstruct.LOGIN_PERSON_CODE, HttpMethod.POST.name()));
        log.info("个人版小程序登录过滤器 拦截 {}", ParamsConstruct.LOGIN_PERSON_CODE);
    }

    @Override
    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = attemptCode(request);
        AppCodeAuthenticationToken authRequest = new AppCodeAuthenticationToken(code, "wechar");
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
